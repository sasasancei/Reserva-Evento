package org.example.reservaevento.saga;

import org.example.reservaevento.adapters.outbound.ReservaRepository;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await; // <-- RESOLVE 'Cannot resolve method await'
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EmbeddedKafka(partitions = 1,
        topics = {"reservas.criadas","pagamentos.solicitados","pagamentos.confirmados","notificacoes.enviar"})
@DirtiesContext
@ExtendWith(SpringExtension.class)
class ReservaSagaIntegrationTest {

    // CAMPOS DECLARADOS DENTRO DO CORPO DA CLASSE
    private final KafkaTemplate<String, Object> kafka;
    private final ReservaRepository repository;
    private final EmbeddedKafkaBroker embeddedKafka;

    // Construtor para Injeção (Com @Autowired para Spring/JUnit)
    @Autowired
    public ReservaSagaIntegrationTest(
            KafkaTemplate<String, Object> kafka,
            ReservaRepository repository,
            EmbeddedKafkaBroker embeddedKafka) {
        this.kafka = kafka;
        this.repository = repository;
        this.embeddedKafka = embeddedKafka;
    }

    @Test
    void fluxo_sagaCompleta() {
        // 1. Publica ReservaCriadaEvent
        ReservaCriadaEvent reservaEvt = new ReservaCriadaEvent("id-test", "sarah@teste.com", 1);
        kafka.send("reservas.criadas", reservaEvt);

        // 2. Aguarda PagamentoSolicitadoEvent
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafka);
            DefaultKafkaConsumerFactory<String, Object> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
            try (var consumer = cf.createConsumer()) {
                embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "pagamentos.solicitados");
                var rec = KafkaTestUtils.getSingleRecord(consumer, "pagamentos.solicitados");
                assertNotNull(rec);
            }
        });

        // 3. Aguarda PagamentoConfirmadoEvent
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup2", "false", embeddedKafka);
            DefaultKafkaConsumerFactory<String, Object> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
            try (var consumer = cf.createConsumer()) {
                embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "pagamentos.confirmados");
                KafkaTestUtils.getSingleRecord(consumer, "pagamentos.confirmados");
            }
        });

        // 4. Aguarda NotificacaoEvent
        await().atMost(5, SECONDS).untilAsserted(() -> {
            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup3", "false", embeddedKafka);
            DefaultKafkaConsumerFactory<String, Object> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
            try (var consumer = cf.createConsumer()) {
                embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "notificacoes.enviar");
                KafkaTestUtils.getSingleRecord(consumer, "notificacoes.enviar");
            }
        });
    }
}
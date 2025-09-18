package org.example.reservaevento.saga;

import org.example.reservaevento.adapters.outbound.ReservaRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

public class ReservaSagaIntegrationTestBuilder {
    private KafkaTemplate<String, Object> kafka;
    private ReservaRepository repository;
    private EmbeddedKafkaBroker embeddedKafka;

    public ReservaSagaIntegrationTestBuilder setKafka(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
        return this;
    }

    public ReservaSagaIntegrationTestBuilder setRepository(ReservaRepository repository) {
        this.repository = repository;
        return this;
    }

    public ReservaSagaIntegrationTestBuilder setEmbeddedKafka(EmbeddedKafkaBroker embeddedKafka) {
        this.embeddedKafka = embeddedKafka;
        return this;
    }

    public ReservaSagaIntegrationTest createReservaSagaIntegrationTest() {
        return new ReservaSagaIntegrationTest(kafka, repository, embeddedKafka);
    }
}
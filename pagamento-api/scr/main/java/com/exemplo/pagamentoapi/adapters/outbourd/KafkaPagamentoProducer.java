package org.exemplo.pagamentoapi.adapters.outbound;

import org.exemplo.pagamentoapi.domain.ports.PagamentoEventPublisher;
import org.example.common.events.PagamentoAprovadoEvent;
import org.example.common.events.PagamentoRejeitadoEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Adaptador de Saída (Outbound Adapter) para publicação de eventos de pagamento no Kafka.
 * Implementa a porta PagamentoEventPublisher definida na camada de domínio.
 */
@Component
public class KafkaPagamentoProducer implements PagamentoEventPublisher {

    private static final String PAGAMENTOS_APROVADOS_TOPIC = "pagamentos.aprovados";
    private static final String PAGAMENTOS_REJEITADOS_TOPIC = "pagamentos.rejeitados";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaPagamentoProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishPagamentoAprovado(PagamentoAprovadoEvent event) {
        kafkaTemplate.send(PAGAMENTOS_APROVADOS_TOPIC, event);
        System.out.println("Enviado evento de pagamento aprovado para o Kafka: " + event.reservaId());
    }

    @Override
    public void publishPagamentoRejeitado(PagamentoRejeitadoEvent event) {
        kafkaTemplate.send(PAGAMENTOS_REJEITADOS_TOPIC, event);
        System.out.println("Enviado evento de pagamento rejeitado para o Kafka: " + event.reservaId());
    }
}
package org.exemplo.pagamentoapi.adapters.inbound;

import org.exemplo.pagamentoapi.application.PagamentoService;
import org.example.common.events.PagamentoSolicitadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener do Kafka para o evento PagamentoSolicitadoEvent.
 * Este componente atua como um Adaptador de Entrada (Inbound Adapter),
 * consumindo a mensagem da fila e chamando a lógica de negócio.
 */
@Component
public class PagamentoSolicitadoListener {

    private final PagamentoService pagamentoService;

    public PagamentoSolicitadoListener(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @KafkaListener(topics = "pagamentos.solicitados", groupId = "pagamento-api-group", containerFactory = "kafkaListenerContainerFactory")
    public void handlePagamentoSolicitado(@Payload PagamentoSolicitadoEvent event) {
        pagamentoService.processarPagamento(event);
    }
}
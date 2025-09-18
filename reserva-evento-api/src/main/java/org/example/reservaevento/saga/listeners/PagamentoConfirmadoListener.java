package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.application.ReservaUseCase;
import org.example.reservaevento.saga.events.PagamentoConfirmadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener do Kafka que ouve o evento de confirmação de pagamento.
 * Este é um "Adapter" (adaptador de entrada) na arquitetura hexagonal.
 */
@Component
public class PagamentoConfirmadoListener {

    private final ReservaUseCase reservaService;

    public PagamentoConfirmadoListener(ReservaUseCase reservaService) {
        this.reservaService = reservaService;
    }

    @KafkaListener(topics = "pagamentos.confirmados", groupId = "reserva-api-group-confirma", containerFactory = "kafkaListenerContainerFactory")
    public void handlePagamentoConfirmado(@Payload PagamentoConfirmadoEvent event) {
        // Adaptador Inbound: Chama a lógica de Aplicação
        reservaService.confirmarReserva(event.reservaId());
    }
}

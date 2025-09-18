package org.example.reservaevento.adapters.inbound;

import org.example.reservaevento.application.ReservaUseCase;
import org.example.reservaevento.saga.events.PagamentoConfirmadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener do Kafka que ouve o evento de pagamento confirmado.
 * Este é um "Adapter" (adaptador de entrada) na arquitetura hexagonal.
 */
@Component
public class PagamentoConfirmadoListener {

    private final ReservaUseCase reservaUseCase;

    @Autowired
    public PagamentoConfirmadoListener(ReservaUseCase reservaUseCase) {
        this.reservaUseCase = reservaUseCase;
    }

    @KafkaListener(topics = "pagamentos.confirmados", groupId = "reserva-api-group-confirma")
    public void handlePagamentoConfirmado(@Payload PagamentoConfirmadoEvent event) {
        System.out.println("Recebido evento de pagamento confirmado para a reserva: " + event.reservaId());
        // Adaptador Inbound: Chama a lógica de Aplicação
        reservaUseCase.confirmarReserva(event.reservaId());
    }
}

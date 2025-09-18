package org.example.reservaevento.adapters.inbound;

import org.example.reservaevento.application.ReservaUseCase;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener do Kafka que ouve o evento de criação de reserva.
 * Este é um "Adapter" (adaptador de entrada) na arquitetura hexagonal.
 */
@Component
public class ReservaCriadoListener {

    private final ReservaUseCase reservaUseCase;

    public ReservaCriadoListener(ReservaUseCase reservaUseCase) {
        this.reservaUseCase = reservaUseCase;
    }

    @KafkaListener(topics = "reservas.criadas", groupId = "reserva-group")
    public void onReservaCriada(ReservaCriadaEvent event) {
        System.out.println("Recebido evento de reserva criada para a pessoa: " + event.getNomePessoa());
        // Lógica de negócio para processar o evento, se necessário.
    }
}

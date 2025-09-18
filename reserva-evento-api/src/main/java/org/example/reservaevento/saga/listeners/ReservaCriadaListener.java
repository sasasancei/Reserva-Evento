package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.application.ReservaService;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Este é o Adaptador INBOUND (Kafka Consumer).
 * Ele ouve o evento ReservaCriadaEvent vindo do tópico
 * 'reservas.criadas' e aciona o serviço de domínio.
 */
@Component
public class ReservaCriadaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservaCriadaListener.class);
    private final ReservaService reservaService;

    public ReservaCriadaListener(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /**
     * Ouve o tópico de reservas criadas e processa o evento.
     * @param event O evento de reserva criada recebido do Kafka.
     */
    @KafkaListener(topics = "reservas.criadas", groupId = "reserva-evento-group")
    public void handleReservaCriada(ReservaCriadaEvent event) {
        LOGGER.info("Evento de Reserva Criada recebido: {}", event);
        try {
            reservaService.processarReserva(event);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Falha ao processar a reserva com ID {}. Motivo: {}", event.getId(), e.getMessage());
            // TODO: Tratar o erro de forma mais robusta, como enviar um evento de falha.
        }
    }
}

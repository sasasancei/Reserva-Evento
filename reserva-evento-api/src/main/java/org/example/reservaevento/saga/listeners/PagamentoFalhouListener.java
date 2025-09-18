package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.application.ReservaService;
import org.example.reservaevento.saga.events.PagamentoFalhadoEvent;
import org.example.reservaevento.saga.events.PagamentoRejeitadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoFalhouListener {

    private final ReservaService reservaService;

    public PagamentoFalhouListener(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @KafkaListener(topics = "pagamentos.falhou", groupId = "reserva-api-group-cancela", containerFactory = "kafkaListenerContainerFactory")
    public void handlePagamentoFalhou(@Payload PagamentoFalhadoEvent event) {
        // Adaptador Inbound: Chama a lógica de Compensação
        System.out.println("Recebido evento de pagamento falho para reserva: " + event.reservaId());

        // Cria um novo evento com o tipo correto para a chamada do serviço
        PagamentoRejeitadoEvent eventoRejeitado = new PagamentoRejeitadoEvent(event.reservaId(), event.motivo());
        reservaService.cancelarReserva(eventoRejeitado);
    }
}

package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PagamentoSagaListener {

    private final KafkaTemplate<String, Object> kafka;
    // Corrija a instanciação do ConcurrentHashMap
    private final ConcurrentHashMap<String, Boolean> seen = new ConcurrentHashMap<>();

    public PagamentoSagaListener(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    @KafkaListener(topics = "reservas.criadas", groupId = "sagas")
    public void onReservaCriada(ReservaCriadaEvent e) {
        if (seen.putIfAbsent(e.getId(), true) != null) {
            return;
        }

        BigDecimal valor = BigDecimal.valueOf(e.getNumeroDeAssentos() * 100L);

        kafka.send("pagamentos.solicitados",
                new PagamentoSolicitadoEvent(e.getId(), valor));
    }
}
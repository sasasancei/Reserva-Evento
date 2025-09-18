package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.saga.events.PagamentoConfirmadoEvent;
import org.example.reservaevento.saga.events.PagamentoFalhadoEvent;
import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    private final KafkaTemplate<String, Object> kafka;

    public PagamentoListener(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    @KafkaListener(topics = "pagamentos.solicitados", groupId = "sagas")
    public void onPagamento(PagamentoSolicitadoEvent e) {
        boolean ok = true; // simulação
        if (ok) {
            kafka.send("pagamentos.confirmados", new PagamentoConfirmadoEvent(e.reservaId()));
        } else {
            kafka.send("pagamentos.falhados",
                    new PagamentoFalhadoEvent(e.reservaId(), "Erro no pagamento"));
        }
    }
}

package org.example.reservaevento.saga.listeners;

import org.example.reservaevento.saga.events.NotificacaoEvent;
import org.example.reservaevento.saga.events.PagamentoConfirmadoEvent;
import org.example.reservaevento.saga.events.PagamentoFalhadoEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoListener {

    private final KafkaTemplate<String, Object> kafka;

    public NotificacaoListener(KafkaTemplate<String, Object> kafka) {
        this.kafka = kafka;
    }

    @KafkaListener(topics = {"pagamentos.confirmados","pagamentos.falhados"}, groupId = "sagas")
    public void onResultadoPagamento(Object evt) {
        String id, msg;
        if (evt instanceof PagamentoConfirmadoEvent c) {
            id = c.reservaId();
            msg = "Pagamento aprovado";
        } else {
            var f = (PagamentoFalhadoEvent)evt;
            id = f.reservaId();
            msg = "Pagamento falhou: "+f.motivo();
        }
        kafka.send("notificacoes.enviar", new NotificacaoEvent(id, lookupEmail(id), msg));
    }

    private String lookupEmail(String id) {
        return "cliente@example.com";
    }
}

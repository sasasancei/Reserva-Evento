package org.example.reservaevento.adapters.inbound;

import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

// Importe as outras classes que você precisa, como PedidoEvent, etc.
// import org.example.reservaevento.domain.PedidoEvent;
// import org.example.reservaevento.application.PagamentoUseCase;

@Component
public class PedidoCriadoListener {

    @KafkaListener(topics = "pedidos.criados", groupId = "pagamento-group")
    public void handlePedidoCriado(String message) {
    }
}

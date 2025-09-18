package org.example.reservaevento.adapters.outbound;


import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Adapter de saída responsável por enviar eventos de pedido para o Kafka.
 * Este é um "Adapter" (adaptador de saída) na arquitetura hexagonal.
 */
@Component
public class KafkaPedidoProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public KafkaPedidoProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviarReservaCriada(ReservaCriadaEvent event) {
        kafkaTemplate.send("reservas.criadas", event);
        System.out.println("Enviado evento de reserva criada para o Kafka: " + event.getId());
    }
}

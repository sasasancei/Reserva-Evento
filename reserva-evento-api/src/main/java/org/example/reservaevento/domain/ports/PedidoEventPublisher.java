package org.example.reservaevento.domain.ports;

import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;

/**
 * Interface que define o contrato para a publicação de eventos de pedido.
 * Esta interface é um "port" (porta de saída) na arquitetura hexagonal,
 * isolando a lógica de negócio do detalhe de infraestrutura (neste caso, o Kafka).
 */
public interface PedidoEventPublisher {

    void publishReservaCriada(ReservaCriadaEvent event);

    void publishPagamentoSolicitado(PagamentoSolicitadoEvent event);
}

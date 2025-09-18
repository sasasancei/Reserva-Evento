package org.exemplo.pagamentoapi.domain.ports;

import org.example.common.events.PagamentoAprovadoEvent;
import org.example.common.events.PagamentoRejeitadoEvent;
import org.example.common.events.PagamentoSolicitadoEvent;

/**
 * Interface que define os contratos para publicação de eventos de pagamento.
 * Esta interface representa uma porta de saída (driving port) na arquitetura hexagonal.
 * O PagamentoService (core da aplicação) usará esta interface para notificar o mundo exterior.
 * A implementação (KafkaPublisher, por exemplo) será um "Adapter" (adaptador de saída).
 */
public interface PagamentoEventPublisher {

    /**
     * Publica um evento de pagamento aprovado.
     * @param event O evento de pagamento aprovado a ser publicado.
     */
    void publishPagamentoAprovado(PagamentoAprovadoEvent event);

    /**
     * Publica um evento de pagamento rejeitado.
     * @param event O evento de pagamento rejeitado a ser publicado.
     */
    void publishPagamentoRejeitado(PagamentoRejeitadoEvent event);
}
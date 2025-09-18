package org.example.reservaevento.saga.events;

/**
 * Evento de domínio que representa a aprovação de um pagamento.
 * Este evento é emitido pelo serviço de pagamento após o sucesso da transação.
 */
public record PagamentoAprovadoEvent(
        String reservaId
) {

}

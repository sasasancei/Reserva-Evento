package org.exemplo.pagamentoapi.saga.events;

import java.math.BigDecimal;

/**
 * Evento de domínio que representa uma solicitação de pagamento.
 * Este evento é emitido pelo microsserviço de reservas e consumido pelo de pagamento.
 */
public record PagamentoSolicitadoEvent(String reservaId, BigDecimal valorTotal) {}

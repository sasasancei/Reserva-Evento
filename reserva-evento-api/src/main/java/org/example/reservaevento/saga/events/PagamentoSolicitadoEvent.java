package org.example.reservaevento.saga.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Evento de domínio que representa a solicitação de pagamento.
 * Este record é imutável e seguro para ser transportado pelo Kafka.
 */
public record PagamentoSolicitadoEvent(
        String reservaId,
        BigDecimal valor
) {}

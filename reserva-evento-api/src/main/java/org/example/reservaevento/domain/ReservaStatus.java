package org.example.reservaevento.domain;

/**
 * Enumeração que representa os possíveis status de uma reserva.
 * Utilizada para gerenciar o estado da reserva ao longo do fluxo da Saga.
 */
public enum ReservaStatus {
    RESERVA_CRIADA,
    PAGAMENTO_PENDENTE,
    PAGAMENTO_CONFIRMADO,
    CANCELADA,
    FINALIZADA,
    PAGAMENTO_SOLICITADO
}

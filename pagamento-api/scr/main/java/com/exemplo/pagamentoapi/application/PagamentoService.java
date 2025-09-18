package org.exemplo.pagamentoapi.application;

import org.exemplo.pagamentoapi.domain.ports.PagamentoEventPublisher;
import org.example.common.events.PagamentoAprovadoEvent;
import org.example.common.events.PagamentoFalhadoEvent;
import org.example.common.events.PagamentoRejeitadoEvent;
import org.example.common.events.PagamentoSolicitadoEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Lógica de negócio para processamento de pagamentos.
 * Esta classe é o "core" da aplicação de pagamento na arquitetura hexagonal.
 */
@Service
public class PagamentoService {

    private final PagamentoEventPublisher pagamentoEventPublisher;

    public PagamentoService(PagamentoEventPublisher pagamentoEventPublisher) {
        this.pagamentoEventPublisher = pagamentoEventPublisher;
    }

    public void processarPagamento(PagamentoSolicitadoEvent event) {
        System.out.println("Iniciando processamento de pagamento para a reserva: " + event.reservaId());

        // Simula a lógica de pagamento. Se o valor total for par, o pagamento falha.
        if (event.valorTotal().intValue() % 2 == 0) {
            System.out.println("Pagamento falhou para a reserva: " + event.reservaId());
            // Lógica de compensação: publica um evento de falha.
            PagamentoRejeitadoEvent eventoRejeitado = new PagamentoRejeitadoEvent(event.reservaId(), "Valor par");
            pagamentoEventPublisher.publishPagamentoRejeitado(eventoRejeitado);
        } else {
            System.out.println("Pagamento aprovado para a reserva: " + event.reservaId());
            // Publica o evento de sucesso.
            PagamentoAprovadoEvent eventoAprovado = new PagamentoAprovadoEvent(event.reservaId());
            pagamentoEventPublisher.publishPagamentoAprovado(eventoAprovado);
        }
    }
}
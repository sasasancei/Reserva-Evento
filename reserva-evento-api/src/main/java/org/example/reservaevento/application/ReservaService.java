package org.example.reservaevento.application;

import org.example.reservaevento.adapters.outbound.ReservaRepository;
import org.example.reservaevento.adapters.outbound.ReservaEntity;
import org.example.reservaevento.domain.ReservaStatus;
import org.example.reservaevento.saga.events.PagamentoAprovadoEvent;
import org.example.reservaevento.saga.events.PagamentoRejeitadoEvent;
import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.example.reservaevento.saga.events.ReservaCriadaEvent;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private static final BigDecimal CUSTO_POR_ASSENTO = new BigDecimal("100.00");
    private static final String TOPICO_PAGAMENTOS = "pagamentos.solicitados";

    private final ReservaRepository repository;
    private final KafkaTemplate<String, Object> kafka;

    public ReservaService(ReservaRepository repository,
                          KafkaTemplate<String, Object> kafka) {
        this.repository = repository;
        this.kafka = kafka;
    }

    private static ReservaEntity toEntity(ReservaCriadaEvent event, BigDecimal valor) {
        ReservaEntity entity = new ReservaEntity();
        entity.setId(event.getId());
        entity.setEmail(event.getEmail());
        entity.setNumeroDeAssentos(event.getNumeroDeAssentos());
        entity.setDataReserva(LocalDateTime.now());
        entity.setValorTotal(valor);
        entity.setStatus(ReservaStatus.PAGAMENTO_SOLICITADO.name());
        return entity;
    }

    // --------------------------------------------------------------------------

    /**
     * Processa o evento de Reserva Criada e inicia o fluxo de pagamento.
     */
    @Transactional
    public void processarReserva(ReservaCriadaEvent event) {
        LOGGER.info("Processando evento de Reserva Criada para ID: {}", event.getId());
        if (event.getNumeroDeAssentos() <= 0) {
            throw new IllegalArgumentException("Assentos deve ser > 0");
        }

        Optional<ReservaEntity> existing = repository.findById(event.getId());
        if (existing.isPresent()) {
            LOGGER.warn("Evento de Reserva Criada já processado para ID: {}", event.getId());
            return;
        }

        BigDecimal numeroAssentos = BigDecimal.valueOf(event.getNumeroDeAssentos());
        BigDecimal valorTotal = numeroAssentos.multiply(CUSTO_POR_ASSENTO);

        ReservaEntity entityParaSalvar = toEntity(event, valorTotal);
        repository.save(entityParaSalvar);

        PagamentoSolicitadoEvent pagamentoEvent = new PagamentoSolicitadoEvent(
                event.getId(),
                valorTotal
        );

        kafka.send(TOPICO_PAGAMENTOS, event.getId(), pagamentoEvent);
        LOGGER.info("Evento de Pagamento Solicitado enviado para o Kafka para ID: {}", event.getId());
    }

    // Métodos confirmarReserva e cancelarReserva
    @Transactional
    public void confirmarReserva(PagamentoAprovadoEvent event) {
        LOGGER.info("Confirmando reserva para ID: {}", event.reservaId());
        Optional<ReservaEntity> optionalReserva = repository.findById(event.reservaId());

        if (optionalReserva.isPresent()) {
            ReservaEntity reserva = optionalReserva.get();
            reserva.setStatus(ReservaStatus.PAGAMENTO_CONFIRMADO.name());
            repository.save(reserva);
            LOGGER.info("Reserva {} confirmada com sucesso.", event.reservaId());
        } else {
            LOGGER.error("Reserva não encontrada para o ID: {}", event.reservaId());
        }
    }

    @Transactional
    public void cancelarReserva(PagamentoRejeitadoEvent event) {
        LOGGER.warn("Cancelando reserva para ID: {}", event.reservaId());
        Optional<ReservaEntity> optionalReserva = repository.findById(event.reservaId());

        if (optionalReserva.isPresent()) {
            ReservaEntity reserva = optionalReserva.get();
            reserva.setStatus(ReservaStatus.CANCELADA.name());
            repository.save(reserva);
            LOGGER.warn("Reserva {} cancelada devido a falha no pagamento.", event.reservaId());
        } else {
            LOGGER.error("Reserva não encontrada para o ID: {}", event.reservaId());
        }
    }
}

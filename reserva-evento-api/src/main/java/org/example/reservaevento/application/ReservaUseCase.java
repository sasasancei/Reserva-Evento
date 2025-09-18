package org.example.reservaevento.application;

import org.example.reservaevento.adapters.outbound.ReservaEntity;
import org.example.reservaevento.adapters.outbound.ReservaRepository;
import org.example.reservaevento.domain.Reserva;
import org.example.reservaevento.domain.ports.PedidoEventPublisher;
import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Lógica de negócio para a criação de reservas.
 * Esta classe é o "core" da aplicação na arquitetura hexagonal.
 */
@Service
public class ReservaUseCase {

    private final ReservaRepository reservaRepository;
    private final PedidoEventPublisher pedidoEventPublisher;

    public ReservaUseCase(ReservaRepository reservaRepository, PedidoEventPublisher pedidoEventPublisher) {
        this.reservaRepository = reservaRepository;
        this.pedidoEventPublisher = pedidoEventPublisher;
    }

    @Transactional
    public Reserva criar(Reserva reserva) {
        // Gera um ID e data de criacao antes de salvar
        reserva.setId(UUID.randomUUID().toString());
        reserva.setDataCriacao(LocalDateTime.now());
        reserva.setStatus("RESERVA_CRIADA");

        // Salva a reserva no banco de dados, convertendo-a para a entidade de persistência.
        ReservaEntity reservaEntity = ReservaEntity.fromDomain(reserva);
        ReservaEntity reservaSalvaEntity = reservaRepository.save(reservaEntity);

        // Publica o evento de PagamentoSolicitado para o Kafka
        PagamentoSolicitadoEvent pagamentoEvent = new PagamentoSolicitadoEvent(
                reservaSalvaEntity.getId(),
                reservaSalvaEntity.getValorTotal()
        );
        pedidoEventPublisher.publishPagamentoSolicitado(pagamentoEvent);

        return reservaSalvaEntity.toDomain();
    }

    @Transactional
    public void confirmarReserva(String reservaId) {
        Optional<ReservaEntity> optionalReserva = reservaRepository.findById(reservaId);
        if (optionalReserva.isPresent()) {
            ReservaEntity reserva = optionalReserva.get();
            reserva.setStatus("PAGAMENTO_CONFIRMADO");
            reservaRepository.save(reserva);
            System.out.println("Reserva " + reservaId + " confirmada com sucesso.");
            // Publicar evento para o próximo passo da Saga, por exemplo, enviar notificação.
        } else {
            System.out.println("Erro: Reserva " + reservaId + " não encontrada para confirmação.");
        }
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarTodas() {
        return reservaRepository.findAll().stream()
                .map(ReservaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Reserva> buscarPorId(String id) {
        return reservaRepository.findById(id).map(ReservaEntity::toDomain);
    }
}

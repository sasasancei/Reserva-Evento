package org.example.reservaevento.application;

import org.example.reservaevento.adapters.outbound.ReservaRepository;
import org.example.reservaevento.adapters.outbound.ReservaEntity;
import org.example.reservaevento.domain.Reserva;
import org.example.reservaevento.domain.ports.PedidoEventPublisher;
import org.example.reservaevento.saga.events.PagamentoSolicitadoEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservaUseCaseTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private PedidoEventPublisher pedidoEventPublisher; // Mock do publicador de eventos

    @InjectMocks
    private ReservaUseCase useCase;

    // Constantes para dados de teste
    private final String TEST_ID = UUID.randomUUID().toString();
    private final String TEST_EMAIL = "joao@ex.com";
    private final String TEST_NOME = "João";
    private final int TEST_ASSENTOS = 3;
    private String id;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criar_deveSalvarERetornarReservaEEnviaEvento() {
        // Arrange
        // 1. Objeto de ENTRADA (sem ID, pois será gerado na persistência)
        Reserva input = new Reserva(this.id, TEST_EMAIL, TEST_ASSENTOS);
        input.setNomePessoa(TEST_NOME);

        // 2. Objeto de SAÍDA (Com o ID gerado e os mesmos dados do input)
        Reserva saved = new Reserva(this.id, TEST_ID, TEST_ASSENTOS);
        saved.setNomePessoa(TEST_NOME);
        saved.setStatus("RESERVA_CRIADA");

        // 3. MOCKING: Simula o comportamento do repositório.
        // O useCase converte 'Reserva' para 'ReservaEntity' antes de salvar.
        when(repository.save(any(ReservaEntity.class))).thenReturn(ReservaEntity.fromDomain(saved));

        // Act
        Reserva result = useCase.criar(input);

        // Assert
        assertNotNull(result.getId());
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getEmail(), result.getEmail());
        assertEquals(saved.getStatus(), result.getStatus());

        // Verifica se o save foi chamado
        verify(repository, times(1)).save(any(ReservaEntity.class));

        // Verifica o evento de pagamento enviado
        ArgumentCaptor<PagamentoSolicitadoEvent> captor = ArgumentCaptor.forClass(PagamentoSolicitadoEvent.class);
        verify(pedidoEventPublisher).publishPagamentoSolicitado(captor.capture());
        PagamentoSolicitadoEvent event = captor.getValue();
        assertEquals(saved.getId(), event.reservaId());
    }

    // Outros testes para os métodos do use case
    @Test
    void buscarPorId_deveRetornarReservaQuandoEncontrada() {
        // Arrange
        Reserva saved = new Reserva(this.id, TEST_ID, TEST_ASSENTOS);
        when(repository.findById(TEST_ID)).thenReturn(Optional.of(ReservaEntity.fromDomain(saved)));

        // Act
        Optional<Reserva> result = useCase.buscarPorId(TEST_ID);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(saved.getId(), result.get().getId());
    }

    @Test
    void buscarPorId_deveRetornarVazioQuandoNaoEncontrada() {
        // Arrange
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        Optional<Reserva> result = useCase.buscarPorId("qualquer-id");

        // Assert
        assertFalse(result.isPresent());
    }
}
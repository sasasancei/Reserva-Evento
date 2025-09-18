package org.example.reservaevento.adapters.outbound;

import org.example.reservaevento.adapters.outbound.ReservaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// O repositório agora gerencia a ReservaEntity, não a Reserva de domínio.
@Repository
public interface ReservaRepository extends JpaRepository<ReservaEntity, String> {
    // Você pode adicionar métodos de consulta personalizados aqui.
}

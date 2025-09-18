package org.example.reservaevento.adapters.outbound;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.example.reservaevento.domain.Reserva;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de persistência que representa a tabela 'reservas' no banco de dados.
 * Ela é parte do Adaptador (Outbound) e mapeia os dados para armazenamento.
 */
@Entity
@Table(name = "reservas")
public class ReservaEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    private String email;
    private int numeroDeAssentos;
    private LocalDateTime dataReserva;
    private BigDecimal valorTotal;
    private String status; // Ex: PAGAMENTO_SOLICITADO, CONFIRMADA, CANCELADA

    public ReservaEntity() {
    }

    // Métodos para converter entre a entidade de domínio e a de persistência
    public static ReservaEntity fromDomain(Reserva reserva) {
        ReservaEntity entity = new ReservaEntity();
        entity.setId(reserva.getId());
        entity.setEmail(reserva.getEmail());
        entity.setNumeroDeAssentos(reserva.getNumeroDeAssentos());
        entity.setDataReserva(reserva.getDataReserva());
        entity.setValorTotal(reserva.getValorTotal());
        entity.setStatus(reserva.getStatus());
        return entity;
    }

    public Reserva toDomain() {
        // Cria uma nova instância de Reserva usando os valores da própria entidade.
        Reserva domain = new Reserva(this.id, this.email, this.numeroDeAssentos);
        domain.setDataReserva(this.dataReserva);
        domain.setValorTotal(this.valorTotal);
        domain.setStatus(this.status);
        return domain;
    }

    // --- GETTERS E SETTERS ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumeroDeAssentos() {
        return numeroDeAssentos;
    }

    public void setNumeroDeAssentos(int numeroDeAssentos) {
        this.numeroDeAssentos = numeroDeAssentos;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

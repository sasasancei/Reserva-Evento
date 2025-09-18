package org.example.reservaevento.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// A entidade de domínio não deve ter anotações de frameworks.
// Ela representa a regra de negócio pura.
public class Reserva {
    private String id;
    private String nomePessoa;
    private String email;
    private int numeroDeAssentos;
    private LocalDateTime dataReserva;
    private LocalDateTime dataCriacao;
    private BigDecimal valorTotal;
    private String status;

    public Reserva(String id, String TEST_ID, int TEST_ASSENTOS) {
        this.dataCriacao = LocalDateTime.now();
    }

    public Reserva(String id, String nomePessoa, String email, int numeroDeAssentos, LocalDateTime dataReserva, BigDecimal valorTotal, String status) {
        this.id = id;
        this.nomePessoa = nomePessoa;
        this.email = email;
        this.numeroDeAssentos = numeroDeAssentos;
        this.dataReserva = dataReserva;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
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

package org.example.reservaevento.dto;

import java.math.BigDecimal;

public class PagamentoSolicitadoDTO {
    private String idReserva;
    private String email;
    private BigDecimal valorTotal;

    // Construtor, Getters e Setters
    public PagamentoSolicitadoDTO(String idReserva, String email, BigDecimal valorTotal) {
        this.idReserva = idReserva;
        this.email = email;
        this.valorTotal = valorTotal;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "PagamentoSolicitadoDTO{" +
                "idReserva='" + idReserva + '\'' +
                ", email='" + email + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
package com.exemplo.bff.dto;

import java.time.LocalDateTime;

// DTO para o BFF, representa a view model do cliente
public class ReservaViewModel {

    private String id;
    private String nomePessoa;
    private int numeroDeAssentos;
    private LocalDateTime dataReserva;

    public ReservaViewModel() {
    }

    public ReservaViewModel(String id, String nomePessoa, int numeroDeAssentos, LocalDateTime dataReserva) {
        this.id = id;
        this.nomePessoa = nomePessoa;
        this.numeroDeAssentos = numeroDeAssentos;
        this.dataReserva = dataReserva;
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
}

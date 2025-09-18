package org.example.reservaevento.saga.events;

public class ReservaCriadaEvent {
    private final String id;
    private final String email;
    private final int numeroDeAssentos;

    // Construtor (necessário para criar o evento)
    public ReservaCriadaEvent(String id, String email, int numeroDeAssentos) {
        this.id = id;
        this.email = email;
        this.numeroDeAssentos = numeroDeAssentos;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return id;
    }

    public int getNumeroDeAssentos() {
        return numeroDeAssentos;
    }

    public String getNomePessoa(){
        return email;
    }


}
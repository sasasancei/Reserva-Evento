// src/main/java/org/example/reservaevento/adapters/inbound/ReservaController.java
package org.example.reservaevento.adapters.inbound;

import org.example.reservaevento.application.ReservaUseCase;
import org.example.reservaevento.domain.Reserva;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaUseCase reservaUseCase;

    public ReservaController(ReservaUseCase reservaUseCase) {
        this.reservaUseCase = reservaUseCase;
    }


    @PostMapping
    public ResponseEntity<Reserva> criar(@RequestBody Reserva reserva) {
        Reserva criada = reservaUseCase.criar(reserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        List<Reserva> todas = reservaUseCase.listarTodas();
        return ResponseEntity.ok(todas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarReserva(@PathVariable String id) {
        return reservaUseCase.buscarPorId(id)
                .map(res -> ResponseEntity.ok(res))
                .orElse(ResponseEntity.notFound().build());
    }
}

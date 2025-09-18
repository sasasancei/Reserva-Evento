package com.exemplo.bff.controller;

import com.exemplo.common.dto.ReservaViewModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller do BFF para o serviço de reservas.
 * Adapta a API de domínio para as necessidades do front-end.
 */
@RestController
@RequestMapping("/v1/bff/reservas")
@Tag(name = "Reservas BFF", description = "Endpoints para a gestão de reservas adaptados para o front-end")
public class ReservaBFFController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    @Operation(summary = "Obtém todas as reservas", description = "Lista todas as reservas existentes no sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem de reservas realizada com sucesso"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            })
    @CircuitBreaker(name = "reserva-api", fallbackMethod = "fallbackGetReservas")
    @Retry(name = "reserva-api")
    public ResponseEntity<List<ReservaViewModel>> getReservas() {
        // Chamada para o serviço de domínio que agora será protegida
        String apiUrl = "http://localhost:8080/v1/reservas";
        ReservaViewModel[] reservas = restTemplate.getForObject(apiUrl, ReservaViewModel[].class);
        return ResponseEntity.ok(Arrays.asList(reservas));
    }

    /**
     * Método de fallback para lidar com falhas na chamada à API de reservas.
     * Retorna uma lista vazia ou uma mensagem de erro controlada.
     */
    public ResponseEntity<List<ReservaViewModel>> fallbackGetReservas(Throwable t) {
        System.err.println("Falha ao chamar a API de reservas. Retornando fallback. Erro: " + t.getMessage());
        return ResponseEntity.status(503).body(Collections.emptyList());
    }
}

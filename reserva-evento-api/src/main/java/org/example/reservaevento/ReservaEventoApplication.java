package org.example.reservaevento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "org.example.reservaevento.adapters",
        "org.example.reservaevento.application",
        "org.example.reservaevento.config",
        "org.example.reservaevento.domain.ports"
})
// Inclua o pacote de outbound onde a interface ReservaRepository foi criada
@EnableJpaRepositories(basePackages = {
        "org.example.reservaevento.adapters.outbound",
        "org.example.reservaevento.domain.ports"
})
public class ReservaEventoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservaEventoApplication.class, args);
    }
}

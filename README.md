Reserva Evento
Uma plataforma completa de gestão de reservas de eventos, construída com uma arquitetura de microsserviços, seguindo os princípios de Domain-Driven Design (DDD) e Arquitetura Hexagonal. O projeto utiliza Kafka para garantir uma comunicação assíncrona e resiliente entre os serviços.

🚀 Tecnologias Utilizadas
Este projeto é um exemplo de arquitetura moderna e escalável, utilizando as seguintes tecnologias:

Linguagem: Java 21

Framework: Spring Boot

Orquestração de Containers: Docker e Docker Compose

Mensageria: Apache Kafka

Banco de Dados: H2 (para desenvolvimento)

API: REST com validação de contrato

Outros: Maven, Swagger/OpenAPI 3.0

🏛️ Arquitetura do Projeto
A arquitetura do projeto é baseada em microsserviços, com a comunicação desacoplada via Kafka. Adotamos a Arquitetura Hexagonal para isolar a lógica de negócio das tecnologias externas, garantindo um design flexível e fácil de testar.

Microsserviços
reserva-evento-api: O core do sistema. Contém a lógica de domínio para criar e gerenciar reservas. Atua como um produtor e consumidor de eventos do Kafka, participando de uma Saga de Coreografia para confirmar ou cancelar reservas com base no status do pagamento.

pagamento-api: O microsserviço especializado em processar pagamentos. Ele escuta eventos de PagamentoSolicitado e, após o processamento, envia um novo evento de PagamentoAprovado ou PagamentoRejeitado para o Kafka.

bff (Backend for Frontend): Atua como uma camada de agregação para o frontend. Simplifica as chamadas da UI, orquestrando requisições para os microsserviços de backend. O BFF também implementa padrões de resiliência, como Circuit Breaker e Retry, para proteger o sistema contra falhas temporárias.

Diagrama de Fluxo de Mensagens (Saga)
Um novo evento de reserva é criado no reserva-evento-api.

O reserva-evento-api envia um evento PagamentoSolicitado para o tópico do Kafka.

O pagamento-api consome o evento PagamentoSolicitado.

O pagamento-api processa o pagamento e envia um evento PagamentoAprovado ou PagamentoRejeitado de volta para o Kafka.

O reserva-evento-api consome o evento de pagamento e atualiza o status da reserva para CONFIRMADA ou CANCELADA.

⚙️ Como Rodar o Projeto
Pré-requisitos
Docker e Docker Compose

Java JDK 17+

Maven

Passo a Passo
Inicie os serviços de infraestrutura:

No diretório onde está o docker-compose.yml, execute:

docker-compose up -d

Isso iniciará o Kafka, Zookeeper e o Redis.

Construa e execute os microsserviços:

Em cada pasta de serviço (reserva-evento-api, pagamento-api, bff), use o Maven para construir e executar a aplicação:

# Exemplo com o reserva-evento-api
cd reserva-evento-api
mvn clean install
mvn spring-boot:run

Acesse a documentação das APIs:

Uma vez que os serviços estejam rodando, você pode acessar a documentação OpenAPI/Swagger para testar os endpoints.

🤝 Contribuição
Contribuições são bem-vindas! Se quiser melhorar o projeto, sinta-se à vontade para abrir uma issue ou um pull request.
Projeto de Orquestração de Reservas
Este repositório contém uma aplicação modular para gerenciamento de reservas de eventos, construída com uma arquitetura de microsserviços. O projeto utiliza Spring Boot para os serviços, Kafka para comunicação assíncrona, e Docker para orquestração em um ambiente de desenvolvimento.

A arquitetura do projeto é dividida em serviços especializados, garantindo a separação de responsabilidades e a escalabilidade.

Arquitetura do Projeto
O sistema é composto pelos seguintes microsserviços:

reserva-evento-api: O serviço principal responsável por gerenciar a lógica de negócio das reservas. Ele publica eventos no Kafka quando uma reserva é criada e consome eventos de outros serviços para atualizar o seu estado.

pagamento-api: Um serviço especializado em processar pagamentos. Ele consome eventos de solicitação de pagamento e, após o processamento, publica eventos de confirmação ou falha.

bff (Backend for Frontend): Atua como uma camada de agregação para a interface do usuário. Ele simplifica a comunicação do frontend, orquestrando chamadas a outros microsserviços e retornando uma resposta consolidada.

common-lib: Uma biblioteca compartilhada que contém classes e utilitários comuns a todos os serviços, como DTOs e eventos, garantindo a consistência do contrato de comunicação.

A comunicação entre os microsserviços é totalmente assíncrona, utilizando uma Saga de Coreografia onde cada serviço reage a eventos relevantes, mantendo o sistema robusto e desacoplado.

Tecnologias Utilizadas
Java 17+: Linguagem de programação principal.

Spring Boot: Framework para a construção de serviços REST e de processamento de eventos.

Apache Maven: Ferramenta para automação de construção e gerenciamento de dependências.

Apache Kafka: Plataforma de streaming de eventos para a comunicação entre os serviços.

Docker: Utilizado para criar um ambiente de desenvolvimento isolado, com containers para o Kafka e o Zookeeper.

GitHub Actions: Ferramenta de CI/CD para automação de build, testes e deploy (conforme evidenciado pela pasta .github/workflows/).

Como Rodar o Projeto Localmente
Siga estas instruções para colocar o projeto em execução em seu ambiente de desenvolvimento.

Pré-requisitos
Java Development Kit (JDK) 17+

Apache Maven

Docker e Docker Compose

1. Iniciar a Infraestrutura
Navegue até o diretório raiz do projeto e use o Docker Compose para iniciar o Kafka e o Zookeeper.

docker-compose up -d

2. Construir e Executar os Serviços
Para cada microsserviço (ex: reserva-evento-api, pagamento-api, bff), navegue até seu respectivo diretório e execute os comandos:

# Exemplo para o serviço de reserva
cd reserva-evento-api
mvn clean package -DskipTests
java -jar target/reserva-evento-api-*.jar

Você também pode usar o seu ambiente de desenvolvimento (IDE) para executar os serviços.

Fluxo de Eventos (Exemplo da Saga)
Um novo evento de reserva é iniciado através do bff ou diretamente do reserva-evento-api.

O reserva-evento-api persiste a reserva com status "pendente" e publica um evento ReservaCriadaEvent no tópico do Kafka.

O pagamento-api consome o evento ReservaCriadaEvent e inicia o processamento do pagamento.

Após o processamento, o pagamento-api publica um evento PagamentoAprovadoEvent ou PagamentoRejeitadoEvent no Kafka.

O reserva-evento-api consome o evento de pagamento e atualiza o status da reserva no banco de dados para "confirmada" ou "cancelada".

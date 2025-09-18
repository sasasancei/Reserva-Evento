terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "2.23.1"
    }
  }
}

provider "docker" {}

resource "docker_network" "kafka_network" {
  name = "kafka_network"
}

resource "docker_container" "zookeeper" {
  name  = "zookeeper"
  image = "confluentinc/cp-zookeeper:7.4.0"
  ports {
    internal = 2181
    external = 2181
  }
  networks_advanced {
    name = docker_network.kafka_network.name
  }
}

resource "docker_container" "kafka" {
  name  = "kafka"
  image = "confluentinc/cp-kafka:7.4.0"
  ports {
    internal = 9092
    external = 9092
  }
  environment = [
    "KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181",
    "KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092",
    "KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT",
    "KAFKA_INTER_BROKER_LISTENER_NAME=PLAINTEXT",
    "KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1",
    "KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0"
  ]
  networks_advanced {
    name = docker_network.kafka_network.name
  }
  depends_on = [docker_container.zookeeper]
}

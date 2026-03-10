# 📬 Notification Service

API REST para envio de notificações assíncronas com processamento via Apache Kafka.

---

## 📋 Sobre o Projeto

O **Notification Service** é uma API que recebe solicitações de notificações (EMAIL, SMS, PUSH), publica eventos em um tópico Kafka e processa as notificações de forma assíncrona via consumer. O projeto foi desenvolvido para demonstrar na prática o uso de mensageria com Kafka em uma arquitetura limpa e bem estruturada.

### Fluxo principal

```
POST /notificacoes
       │
       ▼
NotificacaoController
       │
       ▼
NotificacaoService ──► KafkaProducer ──► [tópico: notificacao-criada]
                                                    │
                                                    ▼
                                         NotificacaoConsumer
                                                    │
                                                    ▼
                                         Salva no banco (H2)
                                                    │
                                                    ▼
                                         GET /notificacoes
```

---

## 🛠️ Tecnologias

| Tecnologia | Versão / Uso |
|---|---|
| Java | 21 (LTS) — Records, Text Blocks |
| Spring Boot | 3.x |
| Spring Web | Endpoints REST |
| Spring Data JPA | Persistência com repositórios |
| Spring Kafka | Producer e Consumer de eventos |
| H2 Database | Banco em memória para desenvolvimento |
| Lombok | Redução de boilerplate |
| Validation | @NotBlank, @Email, @NotNull nos DTOs |
| Springdoc OpenAPI | Documentação automática via Swagger |
| JUnit 5 + Mockito | Testes unitários |
| Docker Compose | Infraestrutura local do Kafka |

---

## 📁 Estrutura do Projeto

```
src/
└── main/java/com/adalberto/notification/
    ├── controller/          # Endpoints REST
    ├── service/             # Lógica de negócio
    ├── domain/
    │   ├── model/           # Entidade JPA
    │   └── enums/           # TipoNotificacao (EMAIL, SMS, PUSH)
    ├── dto/                 # Records de entrada e saída da API
    ├── repository/          # Interface JpaRepository
    ├── kafka/
    │   ├── producer/        # Publica eventos no tópico
    │   └── consumer/        # Consome e processa eventos
    ├── exception/           # BusinessException + GlobalExceptionHandler
    └── config/              # Configuração dos tópicos Kafka
```

---

## 🔗 Endpoints

### `POST /notificacoes`

Envia uma notificação — publica evento no Kafka para processamento assíncrono.

**Request body:**
```json
{
  "destinatario": "usuario@email.com",
  "mensagem": "Sua solicitação foi processada com sucesso.",
  "tipo": "EMAIL"
}
```

**Tipos disponíveis:** `EMAIL`, `SMS`, `PUSH`

| Status | Descrição |
|---|---|
| `202 Accepted` | Evento publicado no Kafka com sucesso |
| `400 Bad Request` | Campos inválidos ou ausentes |

---

### `GET /notificacoes`

Retorna todas as notificações processadas.

**Resposta:**
```json
[
  {
    "id": 1,
    "destinatario": "usuario@email.com",
    "mensagem": "Sua solicitação foi processada com sucesso.",
    "tipo": "EMAIL",
    "status": "PROCESSADO",
    "criadoEm": "2026-03-10T10:00:00"
  }
]
```

---

### `GET /notificacoes/{id}`

Busca uma notificação pelo ID.

| Status | Descrição |
|---|---|
| `200 OK` | Notificação encontrada |
| `400 Bad Request` | Notificação não encontrada |

---

## 🚀 Como executar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose

### 1. Clone o repositório

```bash
git clone https://github.com/jr-adalberto/notification-service.git
cd notification-service
```

### 2. Suba o Kafka com Docker Compose

```bash
docker-compose up -d
```

### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 📖 Documentação da API

Com a aplicação rodando, acesse:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

---

## 🗄️ Console H2

Para visualizar os dados salvos em memória:

- **URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:notificacoes`

---

## 🧪 Testes

```bash
./mvnw test
```

Cobertura de testes unitários no `NotificacaoService` com JUnit 5 e Mockito, validando os cenários de envio, listagem e busca por ID.

---

## 👨‍💻 Autor

**Adalberto da Silva Junior**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=flat&logo=linkedin&logoColor=white)](https://linkedin.com/in/adalbertodsj)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=flat&logo=github&logoColor=white)](https://github.com/jr-adalberto)

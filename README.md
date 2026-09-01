# 🔗 URL Shortener

API REST para encurtamento de URLs, desenvolvida com Java e Spring Boot.

A aplicação permite transformar URLs longas em links curtos, realizar redirecionamentos para a URL original e contabilizar a quantidade de acessos realizados através do link encurtado.

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- PostgreSQL
- Maven
- Docker
- Docker Compose
- JUnit
- Mockito
- Swagger / OpenAPI

## ⚙️ Funcionalidades

- Encurtamento de URLs
- Geração automática de códigos curtos e únicos
- Redirecionamento para a URL original
- Contagem de cliques
- Consulta de informações de uma URL encurtada
- Validação das URLs recebidas
- Tratamento de erros
- Documentação interativa com Swagger
- Testes automatizados

## 📌 Endpoints

### Encurtar uma URL

```http
POST /api/urls
```

Exemplo de requisição:

```json
{
  "url": "https://github.com/"
}
```

Exemplo de resposta:

```json
{
  "originalUrl": "https://github.com/",
  "shortCode": "aB3xK9",
  "shortUrl": "http://localhost:8080/aB3xK9",
  "clickCount": 0
}
```

Status:

```text
201 Created
```

### Consultar uma URL

```http
GET /api/urls/{shortCode}
```

Retorna as informações da URL encurtada sem incrementar a quantidade de cliques.

### Redirecionar

```http
GET /{shortCode}
```

Redireciona para a URL original e incrementa o contador de cliques.

Exemplo:

```text
http://localhost:8080/aB3xK9
```

Resposta:

```text
302 Found
```

## 📖 Swagger

Com a aplicação em execução, a documentação interativa da API pode ser acessada em:

```text
http://localhost:8080/swagger-ui/index.html
```

## 🐳 Executando com Docker

### Pré-requisitos

- Docker
- Docker Compose

Clone o repositório:

```bash
git clone https://github.com/GuiPloW/url-shortener.git
```

Entre no diretório:

```bash
cd url-shortener
```

Suba a aplicação e o PostgreSQL:

```bash
docker compose up --build
```

A API estará disponível em:

```text
http://localhost:8080
```

Para encerrar:

```bash
docker compose down
```

## 💻 Executando localmente

Para executar sem Docker, é necessário possuir Java 21 e PostgreSQL configurados.

A aplicação utiliza as seguintes variáveis de ambiente:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Exemplo de URL de conexão:

```text
jdbc:postgresql://localhost:5432/url_shortener
```

Depois execute:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw spring-boot:run
```

## 🧪 Testes

Para executar os testes automatizados:

```bash
./mvnw clean test
```

No Windows:

```powershell
.\mvnw clean test
```

O projeto possui testes para a camada de serviço e controllers, incluindo cenários de criação, consulta, redirecionamento, validação e tratamento de erros.

## 📂 Estrutura do projeto

```text
src
├── main
│   ├── java/br/com/guilherme/urlshortener
│   │   ├── controller
│   │   ├── dto
│   │   ├── exception
│   │   ├── model
│   │   ├── repository
│   │   └── service
│   └── resources
│       └── application.properties
│
└── test
    └── java/br/com/guilherme/urlshortener
        ├── controller
        └── service
```

## 🗄️ Modelo de dados

A entidade de URL armazena:

| Campo | Descrição |
|---|---|
| `id` | Identificador da URL |
| `originalUrl` | URL original |
| `shortCode` | Código curto único |
| `clickCount` | Quantidade de acessos |
| `createdAt` | Data e hora da criação |

## 👨‍💻 Autor

Desenvolvido por Guilherme Magalhães.

GitHub: [@GuiPloW](https://github.com/GuiPloW)
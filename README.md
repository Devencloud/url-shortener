# URL Shortener

A REST API built with Spring Boot that shortens long URLs into compact, shareable links.

## Features

- Shorten any valid URL into a short key using Base62 encoding
- Redirect short URLs to their original destination
- Redis caching for fast redirects
- Rate limiting — max 10 requests per minute per IP
- Input validation
- Fully Dockerized — runs on any machine with Docker installed

## Tech Stack

- Java 21
- Spring Boot 3
- MySQL 8
- Redis
- Docker & Docker Compose
- Swagger UI (API docs)

## Getting Started

### Prerequisites

- Docker Desktop installed — nothing else required

### Steps

1. Clone the repository

```bash
git clone https://github.com/Devencloud/url-shortener.git
cd url-shortener
```

2. Create your `application.properties` from the example file

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Fill in your credentials in `application.properties` — make sure they match `docker-compose.yml`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=your_password
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

4. Run the app

```bash
docker compose up --build
```

Docker will automatically set up MySQL, Redis, and the Spring Boot app.

## API Endpoints

### Shorten a URL
```
POST /api/shorten
```
Request body:
```json
{
  "originalUrl": "https://www.example.com"
}
```
Response:
```
http://localhost:8080/r/aB3k
```

### Redirect to original URL
```
GET /r/{shortKey}
```
Redirects to the original URL. Returns `404` if the short key does not exist.

## API Documentation

Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

## Rate Limiting

Each IP address is limited to **10 requests per minute**. Exceeding this returns:
```json
{
  "error": "Too many requests. Max 10 requests per minute."
}
```

## How It Works

1. A long URL is received and saved to MySQL
2. The database ID is encoded using Base62 to generate a unique short key
3. On redirect, the short key is first looked up in Redis (cache)
4. If not found in cache, it falls back to MySQL and caches the result for 10 minutes

## Stopping the App

```bash
docker compose down
```

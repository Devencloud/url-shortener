# 🔗 Scalable URL Shortener

A production-ready URL shortening service built using **Java, Spring Boot, MySQL, and Redis**, designed with scalability, performance, and clean backend architecture in mind.

---

## 🚀 Features

* 🔗 Shorten long URLs into compact Base62 encoded links
* ⚡ Fast redirection using Redis caching (Cache-Aside pattern)
* 🗄️ Persistent storage with MySQL
* 🔁 Idempotent URL generation (same URL → same short link)
* 🛡️ Input validation with clean error responses
* 📄 API documentation with Swagger UI
* 🐳 Fully containerized using Docker Compose

---

## 🧠 System Design Overview

### Flow:

1. User submits a long URL
2. Backend checks if URL already exists
3. If not:

   * Save URL → get DB ID
   * Convert ID → Base62 short key
4. Store mapping in MySQL
5. Cache result in Redis

### Redirection:

* First request → DB lookup → cache in Redis
* Subsequent requests → served from Redis (low latency ⚡)

---

## 🏗️ Tech Stack

* **Backend:** Java, Spring Boot
* **Database:** MySQL
* **Cache:** Redis
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **Containerization:** Docker & Docker Compose
* **API Docs:** Swagger (OpenAPI)

---

## ⚙️ Getting Started

### Prerequisites

* Docker Desktop installed
* No need to install Java, MySQL, or Redis manually

---

### 🧱 Steps to Run

#### 1. Clone the repository

```bash
git clone https://github.com/Devencloud/url-shortener.git
cd url-shortener
```

---

#### 2. Create `.env` file

On Mac/Linux:

```bash
cp .env.example .env
```

On Windows:

```bash
copy .env.example .env
```

👉 Default values are already configured — no changes needed.

---

#### 3. Run the application

```bash
docker compose up --build
```

This will start:

* Spring Boot app
* MySQL database
* Redis cache

---

#### 4. Access the application

* API Base URL:

  ```
  http://localhost:8080
  ```

* Swagger UI:

  ```
  http://localhost:8080/swagger-ui/index.html
  ```

---

#### 5. Stop the application

```bash
docker compose down
```

---

## 📌 API Endpoints

### 🔗 Shorten URL

```http
POST /api/shorten
```

**Request:**

```json
{
  "originalUrl": "https://example.com"
}
```

**Response:**

```
http://localhost:8080/r/{shortKey}
```

---

### 🔁 Redirect

```http
GET /r/{shortKey}
```

Redirects to the original URL.

---

## ⚡ Performance Optimization

* Redis used as a **cache layer**
* Cache-Aside pattern implemented
* Reduces DB load significantly
* Ensures low-latency redirection

---

## 🧪 Validation & Error Handling

* Input validation using `@Valid`, `@NotBlank`, `@Pattern`
* Centralized exception handling using `@ControllerAdvice`
* Clean JSON error responses

---

## 🐳 Docker Architecture

The system runs as three containers:

* `app` → Spring Boot backend
* `mysql` → persistent storage
* `redis` → caching layer

All services are orchestrated using **Docker Compose**.

---

## 📈 Future Improvements

* Rate limiting (API protection)
* Analytics (click tracking, geo stats)
* Custom alias support
* Link expiration
* Distributed scaling

---

## 🧾 Project HighLights

> Developed a scalable URL shortening service using Java, Spring Boot, MySQL, and Redis, implementing Base62 encoding and cache-aside strategy to achieve low-latency redirection. Containerized the application using Docker Compose and integrated validation and API documentation for production readiness.

---

## 👨‍💻 Author

**Deven**

---

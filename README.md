🔗 Scalable URL Shortener
A production-ready URL shortening service built with Java, Spring Boot, MySQL, and Redis — designed with scalability, performance, and clean backend architecture in mind.

🚀 Features

🔗 Shorten long URLs into compact Base62 encoded links
⚡ Fast redirection using Redis caching (Cache-Aside pattern)
🗄️ Persistent storage with MySQL
🔁 Idempotent URL generation (same URL → same short link)
🛡️ Input validation with clean error responses
🚦 Rate limiting to protect against API abuse
📄 API documentation via Swagger UI
🐳 Fully containerized using Docker Compose


🧠 System Design Overview
Shortening Flow:

User submits a long URL
Backend checks if the URL already exists
If not: save URL → get DB ID → convert ID to Base62 short key
Store mapping in MySQL
Cache result in Redis

Redirection Flow:

First request → DB lookup → cache in Redis
Subsequent requests → served directly from Redis ⚡


🏗️ Tech Stack
LayerTechnologyBackendJava, Spring BootDatabaseMySQLCacheRedisORMSpring Data JPA (Hibernate)Build ToolMavenContainerizationDocker & Docker ComposeAPI DocsSwagger (OpenAPI)

⚙️ Getting Started
Prerequisites

Docker Desktop installed
No need to install Java, MySQL, or Redis manually

Steps to Run
1. Clone the repository
bashgit clone https://github.com/Devencloud/url-shortener.git
cd url-shortener
2. Create .env file
On Mac/Linux:
bashcp .env.example .env
On Windows:
bashcopy .env.example .env

Default values are pre-configured — no changes needed.

3. Run the application
bashdocker compose up --build
This starts three containers: the Spring Boot app, MySQL, and Redis.
4. Access the application
ServiceURLAPI Base URLhttp://localhost:8080Swagger UIhttp://localhost:8080/swagger-ui/index.html
5. Stop the application
bashdocker compose down

📌 API Endpoints
🔗 Shorten URL
POST /api/shorten
Request:
json{
  "originalUrl": "https://example.com"
}
Response:
http://localhost:8080/r/{shortKey}
🔁 Redirect
GET /r/{shortKey}
Redirects to the original long URL.

⚡ Performance

Redis Cache-Aside pattern reduces database load significantly
Cache hit on subsequent requests ensures low-latency redirection
Rate limiting prevents abuse and protects backend resources


🛡️ Validation & Error Handling

Input validation using @Valid, @NotBlank, @Pattern
Centralized exception handling via @ControllerAdvice
Clean, structured JSON error responses


🐳 Docker Architecture
ContainerRoleappSpring Boot backendmysqlPersistent storageredisCaching layer
All services are orchestrated via Docker Compose.

🧾 Project Highlights
Developed a scalable URL shortening service using Java, Spring Boot, MySQL, and Redis — implementing Base62 encoding, a cache-aside caching strategy, and rate limiting to achieve low-latency, abuse-resistant redirection. Containerized with Docker Compose and integrated with Swagger for API documentation and full input validation for production readiness.

👨‍💻 Author
Deven
github.com/Devencloud


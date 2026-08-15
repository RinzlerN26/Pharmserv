# Pharmserv

Pharmserv is a pharmaceutical entry management application built with Spring Boot and Angular.

The application supports three development and deployment modes:

1. **Local Development** — Angular and Spring Boot run directly on the host, while MySQL runs in Docker.
2. **Docker Development** — Angular and Spring Boot run inside Docker with hot reload.
3. **Docker Production** — Angular and Spring Boot are built into production Docker images and served through Caddy.

## Built With

- ![Spring](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=FFFFFF)

- ![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=FFFFFF)

- Angular
- MySQL
- Docker
- Caddy

## Prerequisites

For local development:

- Java 21
- Maven Wrapper
- Node.js 22+
- npm
- Docker

For Docker development/production:

- Docker
- Docker Compose

## Repository Structure

Pharmserv requires the backend and frontend repositories to be cloned next to each other.

```text
ParentFolder/
├── Pharmserv/
└── Pharmserv-UI/
```

Clone both repositories:

```sh
git clone https://github.com/RinzlerN26/Pharmserv.git
git clone https://github.com/RinzlerN26/Pharmserv-UI.git
```

The Docker Compose configuration expects the UI repository at:

```text
../Pharmserv-UI
```

relative to the Pharmserv repository.

---

# Configuration

Pharmserv uses the following environment variables:

```text
JWT_SECRET_KEY
ADMIN_BOOTSTRAP_ENABLED
ADMIN_BOOTSTRAP_USER_ID
ADMIN_BOOTSTRAP_USERNAME
ADMIN_BOOTSTRAP_EMAIL
ADMIN_BOOTSTRAP_PASSWORD
```

## Docker

For Docker-based setups, create a `.env` file from the provided example:

```powershell
Copy-Item .env.example .env
```

Then configure the required values in `.env`.

The `.env` file is ignored by Git and should never be committed.

## Local Spring Boot

When running Spring Boot directly on the host, environment variables can be configured in PowerShell.

Example:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/pharmserv-db"
$env:SPRING_DATASOURCE_USERNAME="mysql"
$env:SPRING_DATASOURCE_PASSWORD="mysql"

$env:JWT_SECRET_KEY="your-secret"

$env:ADMIN_BOOTSTRAP_ENABLED="true"
$env:ADMIN_BOOTSTRAP_USER_ID="1"
$env:ADMIN_BOOTSTRAP_USERNAME="admin"
$env:ADMIN_BOOTSTRAP_EMAIL="admin@example.com"
$env:ADMIN_BOOTSTRAP_PASSWORD="your-password"
```

---

# 1. Local Development

In this mode, Angular and Spring Boot run directly on the host.

Only MySQL and phpMyAdmin run inside Docker.

## Start MySQL

From the Pharmserv directory:

```powershell
docker compose -f docker-compose.local.yml up -d
```

This starts:

```text
MySQL       localhost:3306
phpMyAdmin  localhost:8081
```

## Start Spring Boot

Configure the required PowerShell environment variables and run:

```powershell
./mvnw spring-boot:run
```

Spring Boot will be available at:

```text
http://localhost:8080
```

## Start Angular

Open another terminal in `Pharmserv-UI`:

```powershell
npm install
npm start
```

Angular will be available at:

```text
http://localhost:4200
```

This is the recommended setup for day-to-day development because both Angular and Spring Boot run directly on the host and provide fast development feedback.

---

# 2. Docker Development

This mode runs the entire application stack inside Docker while keeping development hot reload enabled.

The stack consists of:

```text
Caddy
├── Angular development server
├── Spring Boot development server
├── MySQL
└── phpMyAdmin
```

Start the environment with:

```powershell
docker compose --env-file .env -f docker-compose.dev.yml up --build
```

Caddy will be available at:

```text
http://localhost
```

Requests are routed as follows:

```text
/       → Angular
/ms/*   → Spring Boot
```

Angular source files are mounted into the container and served through the Angular development server.

Spring Boot source files are also mounted into the container.

This allows application changes to be reflected without rebuilding the Docker images.

To stop the environment:

```powershell
docker compose -f docker-compose.dev.yml down
```

---

# 3. Docker Production

The production configuration builds both the Angular frontend and Spring Boot backend.

The stack consists of:

```text
Caddy
├── Angular production build
└── Spring Boot

MySQL

phpMyAdmin
```

Create and configure `.env` first:

```powershell
Copy-Item .env.example .env
```

Then start the application:

```powershell
docker compose --env-file .env up --build
```

Caddy will be available at:

```text
http://localhost
```

Routing:

```text
/       → Angular production build
/ms/*   → Spring Boot
```

The Angular application is compiled using:

```text
npm run build
```

The Spring Boot application is compiled into a JAR and executed using Eclipse Temurin JRE 21.

Unlike Docker development, production does not mount the application source code and does not use development servers or hot reload.

To stop the environment:

```powershell
docker compose down
```

---

# Application URLs

## Local Development

| Service     | URL                   |
| ----------- | --------------------- |
| Angular     | http://localhost:4200 |
| Spring Boot | http://localhost:8080 |
| MySQL       | localhost:3306        |
| phpMyAdmin  | http://localhost:8081 |

## Docker Development

| Service     | URL                   |
| ----------- | --------------------- |
| Application | http://localhost      |
| phpMyAdmin  | http://localhost:8081 |

## Docker Production

| Service     | URL                   |
| ----------- | --------------------- |
| Application | http://localhost      |
| phpMyAdmin  | http://localhost:8081 |

---

# Docker Commands

## Local

```powershell
docker compose -f docker-compose.local.yml up -d
```

Stop:

```powershell
docker compose -f docker-compose.local.yml down
```

## Docker Development

```powershell
docker compose --env-file .env -f docker-compose.dev.yml up --build
```

Stop:

```powershell
docker compose -f docker-compose.dev.yml down
```

## Docker Production

```powershell
docker compose --env-file .env up --build
```

Stop:

```powershell
docker compose down
```

---

# Notes

- `.env` contains secrets and must not be committed.
- Docker development mounts source code to enable hot reload.
- Docker production does not mount source code.
- Caddy is used as the reverse proxy and static file server.
- The `/ms/*` path is used for Spring Boot API requests.

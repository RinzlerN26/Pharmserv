# Pharmserv

Microservices for pharmaceutical data entry software.<br>The application is dockerized for rapid deployment.

### Built With

- ![Spring](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=FFFFFF)

- ![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=FFFFFF)

### Prerequisites

- Docker

### Installation

1. Clone the repo.

   ```sh
   git clone https://github.com/RinzlerN26/Pharmserv.git
   ```

2. Clone the UI repo in the parent folder where the Pharmserv was cloned.

   The directory structure should be as follows after cloning:

   ParentFolder<br>
   |--Pharmserv<br>
   |--Pharmserv-UI

   ```sh
   git clone https://github.com/RinzlerN26/Pharmserv-UI.git
   ```

3. Change directory to Pharmserv-UI, uncomment production configuration in Dockerfile and comment environment object with production set as false and uncomment environment object with production set as true in src/environments/environments.ts .

4. Change directory to Pharmserv, uncomment all services in docker-compose.yml, production configuration in Dockerfile and caddy production configuration in Caddyfile.

5. Run the application.

   ```sh
   docker compose up
   ```

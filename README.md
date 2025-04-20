# Pharmserv

Microservices for pharmaceutical data entry software.

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

2. Clone the UI repo in the parent folder where the above repo was cloned.
   The directory structure should be as follows after cloning:
   ParentFolder
   |--PharmServ
   |--PharmServ-UI

   ```sh
   git clone https://github.com/RinzlerN26/Pharmserv-UI.git
   ```

3. Open a powershell/terminal in PharmServ folder and run the application.

   ```sh
   docker compose up
   ```

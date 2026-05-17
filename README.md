## Custom Single-Node Hadoop Cluster (Dockerized)

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-22.04-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![Hadoop](https://img.shields.io/badge/Apache_Hadoop-3.5.0-FFDF00?style=for-the-badge&logo=apachehadoop&logoColor=black)
![Java](https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=openjdk&logoColor=white)

## How to Run

To get the system up and running, you only need to have **Docker** and **Docker Compose** installed on your computer. Complex XML configurations and SSH keys are handled automatically during the image build process.

**1. Building and Starting the System**
Open your terminal in the main project directory and run the following command:
```bash
docker compose up -d --build
```
(This command pulls the Ubuntu base, installs Java 17 and Hadoop 3.5.0, imports our custom config files, formats the HDFS, and starts all services.)

2. Accessing the Web Interfaces
Once the system is in the "Up" state, you can monitor the cluster live by visiting the following addresses in your web browser:

    HDFS NameNode (File System Status): http://localhost:9870

    YARN Resource Manager (Resource Management): http://localhost:8088

3. Shutting Down the System
To safely stop the container and clean up the network when you are finished working:
```Bash
docker compose down
```
Architectural Folder Structure

    config/: Contains the custom XML configuration files (core-site.xml, hdfs-site.xml, etc.) and startup scripts (.sh) that form the core of Hadoop.

    Dockerfile: Our main recipe that builds the environment from the base OS, installs all dependencies, and sets up security permissions.

    docker-compose.yml: The orchestration file responsible for spinning up the container and managing port and volume mappings.

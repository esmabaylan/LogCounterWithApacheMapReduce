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


## Running on Hadoop Cluster (Docker) and Retrieving Results

Follow these execution steps to deploy the MapReduce application on the Hadoop cluster and copy the results back to the local environment.

sample data link: https://www.kaggle.com/datasets/eliasdabbas/web-server-access-logs
    Note:During the Docker development process, 3.5 GB of data was used.
    The JAR file was tested with the `sample_data.txt` file located in the `data` folder during the development stage.


**1. Package the Project (Local Terminal)**
Compile the project and build the executable `.jar` file:
```bash
mvn clean package
```
2. Copy Files to Docker Container (Local Terminal)
Transfer the generated .jar file and the raw log data into the Hadoop Master (Namenode) container:
```Bash
docker cp target/mapreduce-app-1.0-SNAPSHOT.jar <container_id>:/mapreduce-app.jar
```
```Bash
docker cp INPUT_DATA_PATH/INPUT_DATA <container_id>:/INPUT_DATA
```
3. Access the Container (Local Terminal)
Open a bash session inside the Docker container:
```Bash
docker exec -it <container_id> bash
```
4. Upload Data to HDFS (Container Terminal)
Create an input directory in the Hadoop Distributed File System (HDFS) and move the log file into it:
```Bash
hdfs dfs -mkdir /input_data
```

```Bash
hdfs dfs -put /access.log /input_data/
```

5. Execute the MapReduce Job (Container Terminal)
Run the application by specifying the main class, input directory, and output directory:
```Bash
hadoop jar /mapreduce-app.jar com.loganalyzer.LogDriver /input_data /output_data
```
6. Extract Results from HDFS to Container (Container Terminal)
Once the job completes successfully, download the output folder from HDFS to the local filesystem of the container, then terminate the session:
```Bash
hdfs dfs -get /output_data /output_data_linux
```
```Bash
exit
```
7. Download Results to Host Machine (Local Terminal)
Copy the final analysis directory from the Docker container back into the local project path:
```Bash
docker cp <container_id>:/output_data_linux ./output_data
```

    Note: The final counts are stored in the output/part-r-00000 file. Replace <container_id> with the actual container ID of the namenode before running the commands.
# Running the microservice

### Docker Support
The Student Support Service provides a `Dockerfile` for building a Docker image and a `docker-compose.yml` for running a Docker container that hosts the service `.jar`.  For the following command to work, the Docker Engine must be installed on the target build machine.  Resources for downloading and installing the Docker Engine on various operating systems can be found [here](https://docs.docker.com/engine/installation/).  For details on what Docker is and how it works, refer to [this page](https://www.docker.com/what-docker).

To build the service and its associated Docker image:

* `mvn clean install docker:build -f /path/to/service/pom.xml`

## Run
### Run in IDE
To run the Student Support Service in the IDE, update the `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password` properties defined in the `service/src/main/resources/application.properties` file and set them to appropriate values.

### Run .JAR
To run the compiled jar built by one of the build commands above, use the following:

```
java -Xms256m -Xmx512m \
    -jar /path/to/target/tds-student-service-0.0.1-SNAPSHOT.jar \
    --server-port="8080" \
    --server.undertow.buffer-size=16384 \
    --server.undertow.buffers-per-region=20 \
    --server.undertow.io-threads=64 \
    --server.undertow.worker-threads=512 \
    --server.undertow.direct-buffers=true \
    --spring.datasource.url="jdbc:mysql://[db server name]:[db port]/session" \
    --spring.datasource.username="[MySQL user name]" \
    --spring.datasource.password="[MySQL user password]" \
    --spring.datasource.type=com.zaxxer.hikari.HikariDataSource
```

### Run Docker Container
The Student Support Service requires several environment variables to be set prior to starting the `.jar`.  The environment variables are managed by an [environment file](https://docs.docker.com/engine/reference/commandline/run/#/set-environment-variables-e-env-env-file), which Docker uses to set the appropriate environment variables.

To create the environment file:

* Navigate to where the `docker-compose.yml` file is located
* Create a new file named `student-service.env`
* Open `session-service.env` in an editor and set the following values:

```
SESSION_DB_HOST=[IP address or FQDN of the MySQL database server that hosts the TDS session database]
SESSION_DB_PORT=[The port on which the MySQL database server listens]
SESSION_DB_NAME=[The name of the TDS session database (typically "session")]
SESSION_DB_USER=[The MySQL user account with sufficient privileges to read from the session databases]
SESSION_DB_PASSWORD=[The password for the MySQL user account]
```

* Example `session-service.env` file:

```
SESSION_DB_HOST=tds-mysql-instance.example.com
SESSION_DB_PORT=3306
SESSION_DB_NAME=session
SESSION_DB_USER=tds_user
SESSION_DB_PASSWORD=protohorsecarbattery
```
**NOTE:**  Any file with a `.env` extension will _not_ be committed to source control; the `.gitignore` is set to exclude files with a `.env` extension.  Therefore, sensitive information stored in this file will not be committed.

After the `student-service.env` file is saved, run the Config Support Service Docker container with the following commands:
 
```
mvn clean install docker:build -f /path/to/service/pom.xml
docker-compose up -d -f /path/to/docker-compose.yml
```

#### Additional Details for Interacting With Docker
The `Dockerfile` included in this repository is intended for use with [Spotify's Docker Maven plugin](https://github.com/spotify/docker-maven-plugin).  As such, the `docker build` command will fail because it cannot find the compiled `.jar`.

The Docker container can be started via `docker-compose` or `docker run`:

* The command for starting the container via `docker-compose`:  `docker-compose up -d -f /path/to/docker-compose.yml`
  * **NOTE:** If `docker-compose` is run in the same directory where the `docker-compose.yml` file is located, `docker-compose up -d` is sufficient to start the container
* Alternately, `docker run` can be used to start up the container:  `docker run -d -p [open port on host]:8080 --env-file /path/to/session-service.env fwsbac/tds-session-service`
  * example:  `docker run -d -p 23572:8080 --env-file session-service.env fwsbac/tds-session-service`

To see the list of running Docker containers, use the following command:

* `docker ps -a`
* Output will appear as follows:
 
```
CONTAINER ID        IMAGE                        COMMAND                CREATED             STATUS              PORTS                     NAMES
de37db84cb30        fwsbac/tds-session-service   "/docker-startup.sh"   2 hours ago         Up 2 hours          0.0.0.0:23572->8080/tcp   docker_session_1
```
To tail the log files for the process(es) running on the Docker container:

* `docker logs -f [container id]`
  * **NOTE:**  To view the logs without tailing them, omit the `-f` from the command above
* example:  `docker logs -f de37db84cb30`
#!/bin/sh
#-----------------------------------------------------------------------------------------------------------------------
# File:  docker-startup.sh
#
# Desc:  Start the tds-student-service.jar with the appropriate properties.
#
#-----------------------------------------------------------------------------------------------------------------------

java \
    -Dspring.datasource.url="jdbc:mysql://${STUDENT_DB_HOST}:${STUDENT_DB_PORT}/${STUDENT_DB_NAME}" \
    -Dspring.datasource.username="${STUDENT_DB_USER}" \
    -Dspring.datasource.password="${STUDENT_DB_PASSWORD}" \
    -Dspring.datasource.type=com.zaxxer.hikari.HikariDataSource \
    -Dspring.datasource.driver-class-name=com.mysql.jdbc.Driver \
    -jar /tds-student-service.jar \
    --server-port="8080" \
    --server.undertow.buffer-size=16384 \
    --server.undertow.buffers-per-region=20 \
    --server.undertow.io-threads=64 \
    --server.undertow.worker-threads=512 \
    --server.undertow.direct-buffers=true \
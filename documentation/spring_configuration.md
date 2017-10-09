# Spring Configuration for Student Microservice
This page covers the Spring boot application options for the Student microservice.

```
# Contains properties for the tds-assessment-service.
# Properties not defined in a profile are global to all profiles.  Profiles sections can override the globabl values.

# Undertow Web Server configuration
server:
  port: ${SERVER_PORT:8080}
  undertow:
    buffer-size: 8192
    max-regions: 10
    io-threads: 4
    worker-threads: 32
    direct-buffers: true

# General Spring Datasource information that doesn't need to change
spring:
  datasource:
    hikari:
      maximum-pool-size: 32
      minimum-idle: 8
      idle-timeout: 120000
      connectionTestQuery: "SELECT 1"

# Flyway should not be run by default in the student service
flyway:
  enabled: false

# Health checks.  Disable redis and rabbit health checks.
management:
  health:
    redis:
      enabled: false
    rabbit:
      enabled: false

---
# AWS QA kubernetes profile
spring:
  profiles: awsqa
  datasource:
    url: jdbc:mysql://<DB SERVER>/session
    username: # DB username
    password: # DB password
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: 'com.mysql.jdbc.Driver'
  redis: 
    sentinel: # Sentinel and master for redis hosted in Kubernetes
      master: # master name
      nodes: # sentinal address
    host: # put external redis server here
  rabbitmq:
    addresses: # rabbit address
    username: # rabbit username
    password: # rabbit password

tds:
  cache:
    implementation: # 'redis' or nothing.  In memory is the default if not specified

logstash-destination: logstash-qa.sbtds.org:4560

```
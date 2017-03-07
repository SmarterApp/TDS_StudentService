#-----------------------------------------------------------------------------------------------------------------------
# File:  Dockerfile
#
# Desc:  Create a Docker container to host the Student Support Service.
#
#-----------------------------------------------------------------------------------------------------------------------
FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp

COPY docker-startup.sh docker-startup.sh
COPY tds-student-service-*.jar tds-student-service.jar

RUN sh -c 'touch /tds-student-service.jar /docker-startup.sh'
RUN apk --no-cache add curl

ENTRYPOINT ["/docker-startup.sh"]
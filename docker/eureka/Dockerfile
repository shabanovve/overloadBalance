FROM openjdk:8-jdk

# to check for open ports in the container: docker run <container id> netstat -tan
#RUN apt update && apt install net-tools

RUN mkdir -p /opt/app

ADD eureka.jar /opt/app/eureka.jar

# A simple shell script to pass JVM arguments
ADD entrypoint.sh /opt/app/entrypoint.sh

# JMX
EXPOSE 9010

# JVM debugging port
EXPOSE 5005

EXPOSE 8080

ENTRYPOINT [ "/opt/app/entrypoint.sh" ]

FROM --platform=linux/amd64 openjdk:11-slim as build
MAINTAINER armanit.net

COPY target/spring-security-demo-0.0.1-SNAPSHOT.jar spring-security-demo-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/spring-security-demo-0.0.1-SNAPSHOT.jar"]
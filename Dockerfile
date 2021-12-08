FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/exchangerate-0.0.1-SNAPSHOT.jar exchangerate-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "exchangerate-0.0.1-SNAPSHOT.jar"]
FROM openjdk:17-oracle
ARG JAR_FILE=target/sender-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} sender-service.jar
ENTRYPOINT ["java","-jar","/sender-service.jar"]
EXPOSE 8100
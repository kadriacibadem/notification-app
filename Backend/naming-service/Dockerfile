FROM openjdk:17-oracle
ARG JAR_FILE=target/naming-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} naming-service.jar
ENTRYPOINT ["java","-jar","/naming-service.jar"]
EXPOSE 8761
FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} rackSemanalMS.jar
ENTRYPOINT ["java", "-jar", "/rackSemanalMS.jar"]
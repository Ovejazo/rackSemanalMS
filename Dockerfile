FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} clientMS.jar
ENTRYPOINT ["java", "-jar", "/clientMS.jar"]
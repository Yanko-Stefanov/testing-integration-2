FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} jwt-token-0.0.1.jar
ENTRYPOINT ["java", "-jar", "jwt-token-0.0.1.jar"]
EXPOSE 9005

FROM openjdk:17-jdk-slim
CMD mvn clean install -Pproduction
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
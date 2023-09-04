FROM maven:3-eclipse-temurin-17 as build
COPY . .
RUN mvn clean install -Pproduction -DskipTests

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
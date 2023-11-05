#FROM maven:3.8.4-openjdk-17 as builder
#WORKDIR /app
#COPY . /app/.
#RUN mvn -f /app/pom.xml clean package
#
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#COPY --from=builder /app/target/*.jar /app/*.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/app/*.jar"]

FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
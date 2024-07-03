# Building stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .env .
RUN mvn clean package -Dmaven.test.skip

# Running stage
FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY --from=build /app/target/gateway-api-0.0.1-SNAPSHOT.jar gateway_api.jar
ENTRYPOINT ["java","-jar","/gateway_api.jar"]
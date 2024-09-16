#creating Jar file
FROM maven:latest AS build

COPY CargoStacks /opt/CargoStacks
WORKDIR /opt/CargoStacks
RUN mvn package -DskipTests 

# Second stage: create a slim image
FROM openjdk:latest

COPY --from=build /opt/CargoStacks/target/CargoStacks-Application.jar /opt/CargoStacks-Application.jar
ENTRYPOINT ["java", "-jar", "/opt/CargoStacks-Application.jar"]
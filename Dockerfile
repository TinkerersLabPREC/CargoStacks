FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /opt/CargoStacks
# COPY CargoStacks/pom.xml .
# RUN mvn dependency:go-offline -B
COPY CargoStacks /opt/CargoStacks
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy

RUN groupadd -g 1234 CargoStacks && \
    useradd -m -u 1234 -g CargoStacks CargoStacks
USER CargoStacks
WORKDIR /home/CargoStacks
COPY --from=build /opt/CargoStacks/target/CargoStacks-Application.jar .
ENTRYPOINT ["java", "-jar", "CargoStacks-Application.jar"]
# building jar file
FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /opt/CargoStacks
COPY CargoStacks /opt/CargoStacks
RUN mvn clean package -DskipTests

# generating custom jre 
FROM eclipse-temurin:17-jdk-alpine AS jre-builder
RUN apk update && \
    apk add binutils;
WORKDIR /opt
COPY --from=build /opt/CargoStacks/target/CargoStacks-Application.jar .
# RUN jdeps --ignore-missing-deps -q \
#     --recursive \
#     --multi-release 17 \
#     --print-module-deps \
#     --class-path "/opt/CargoStacks-Application.jar:BOOT-INF/lib/*" \
#     CargoStacks-Application.jar > modules.txt 
RUN $JAVA_HOME/bin/jlink \
    --verbose \
    # --add-modules $(cat modules.txt) \
    --add-modules ALL-MODULE-PATH \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /optimized-jdk-17

# running jar file in custom jre
FROM alpine:latest
ENV JAVA_HOME=/opt/jdk/jdk-17
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-builder /optimized-jdk-17 $JAVA_HOME
ARG APPLICATION_USER=spring
RUN addgroup --system $APPLICATION_USER &&  \
    adduser --system $APPLICATION_USER --ingroup $APPLICATION_USER
    RUN mkdir /opt/CargoStacks && chown -R $APPLICATION_USER /opt/CargoStacks
USER $APPLICATION_USER
WORKDIR /home/CargoStacks
COPY --from=build /opt/CargoStacks/target/CargoStacks-Application.jar .
ENTRYPOINT [ "java","-jar", "CargoStacks-Application.jar" ]
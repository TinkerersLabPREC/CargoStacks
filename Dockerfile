FROM maven:3.8.7-eclipse-temurin-17 AS build
WORKDIR /opt/CargoStacks
# COPY CargoStacks/pom.xml .
# RUN mvn dependency:go-offline -B
COPY CargoStacks /opt/CargoStacks
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

RUN groupadd -g 1234 CargoStacks && \
    useradd -m -u 1234 -g CargoStacks CargoStacks
USER CargoStacks
WORKDIR /home/CargoStacks
COPY --from=build /opt/CargoStacks/target/CargoStacks-Application.jar .
ENTRYPOINT ["java", "-jar", "CargoStacks-Application.jar"]



# second approach, using jlink

# FROM eclipse-temurin:17-jdk-alpine AS build
# RUN apk update &&  \
#     apk add binutils

# RUN $JAVA_HOME/bin/jlink \
#         --verbose \
#         --add-modules ALL-MODULE-PATH \
#         --strip-debug \
#         --no-man-pages \
#         --no-header-files \
#         --compress=2 \
#         --output /optimized-jdk-17


# FROM eclipse-temurin:17-jdk-alpine AS jre-builder

# RUN mkdir /opt/CargoStacks-application
# COPY ./CargoStacks /opt/CargoStacks-application

# WORKDIR /opt/CargoStacks-application

# ENV MAVEN_VERSION 3.5.4
# ENV MAVEN_HOME /usr/lib/mvn
# ENV PATH $MAVEN_HOME/bin:$PATH

# RUN apk update && \
#     apk add --no-cache tar binutils

# RUN wget http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz && \
#     tar -zxvf apache-maven-$MAVEN_VERSION-bin.tar.gz && \
#     rm apache-maven-$MAVEN_VERSION-bin.tar.gz && \
#     mv apache-maven-$MAVEN_VERSION /usr/lib/mvn

# RUN mvn package -DskipTests

# RUN jar xvf target/spring-error-handling-rfc-9457-0.0.1-SNAPSHOT.jar

# RUN jdeps --ignore-missing-deps -q  \
#     --recursive  \
#     --multi-release 17  \
#     --print-module-deps  \
#     --class-path 'BOOT-INF/lib/*'  \
#     target/spring-error-handling-rfc-9457-0.0.1-SNAPSHOT.jar > modules.txt

# # Build small JRE image
# RUN $JAVA_HOME/bin/jlink \
#          --verbose \
#          --add-modules $(cat modules.txt) \
#          --strip-debug \
#          --no-man-pages \
#          --no-header-files \
#          --compress=2 \
#          --output /optimized-jdk-17
# RUN jdeps --ignore-missing-deps -q  \
#     --recursive  \
#     --multi-release 17  \
#     --print-module-deps  \
#     --class-path 'BOOT-INF/lib/*'  \
#     target/spring-error-handling-rfc-9457-0.0.1-SNAPSHOT.jar > modules.txt

# RUN jlink \
#     --verbose \
#     --add-modules $(cat modules.txt) \
#     --strip-debug \
#     --no-man-pages \
#     --no-header-files \
#     --compress=2 \
#     --output /optimized-jdk-17

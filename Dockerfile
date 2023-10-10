FROM eclipse-temurin:17 as build
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
WORKDIR /opt/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY ./src ./src
RUN ./mvnw clean install -Dmaven.test.skip=true

FROM openjdk:17-slim

WORKDIR /opt/app

COPY --from=build /opt/app/target/itxbackendtools-0.0.1-SNAPSHOT.jar /opt/app

CMD ["java", "-jar", "--enable-preview", "/opt/app/itxbackendtools-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=mongo"]
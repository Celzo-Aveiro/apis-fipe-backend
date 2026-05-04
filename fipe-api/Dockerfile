# syntax=docker/dockerfile:1.7

# ---- build ----
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /build

# Wrapper e POM primeiro: alterar src/ nao invalida o cache de dependencias.
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN --mount=type=cache,target=/root/.m2 \
    chmod +x mvnw && ./mvnw -B -ntp dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 \
    ./mvnw -B -ntp -DskipTests package

# ---- runtime ----
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN groupadd --system --gid 1001 spring \
 && useradd  --system --uid 1001 --gid spring --no-create-home spring
USER spring:spring

COPY --from=builder --chown=spring:spring /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

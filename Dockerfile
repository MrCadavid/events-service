# compilation
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Ejecutar el comando de compilación
RUN mvn clean package -DskipTests

# execution
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=builder /app/target/events-service-0.0.1-SNAPSHOT.jar app.jar

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

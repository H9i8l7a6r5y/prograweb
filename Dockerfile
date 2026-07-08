# ===== ETAPA DE CONSTRUCCIÓN =====
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar archivos
COPY pom.xml .
COPY src ./src

# Compilar
RUN mvn clean package -DskipTests

# ===== ETAPA DE EJECUCIÓN =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar JAR
COPY --from=build /app/target/*.jar app.jar

# Configurar puerto
EXPOSE 8080

# Ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
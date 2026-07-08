# ===== CONSTRUCCIÓN (BUILD STAGE) =====
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
COPY src ./src

# Compilar y empaquetar (saltando pruebas)
RUN mvn clean package -DskipTests

# ===== EJECUCIÓN (RUNTIME STAGE) =====
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Puerto de la aplicación
EXPOSE 8080

# Comando para ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
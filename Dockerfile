# Runtime Java 21
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el JAR empacado por Maven
COPY target/*.jar app.jar

# App Runner y muchas plataformas usan 8080 por defecto
EXPOSE 8080

# Limitar memoria de forma segura
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75"

# Arranque
CMD ["java","-jar","/app/app.jar"]
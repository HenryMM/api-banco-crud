# Usa una imagen oficial de Java para ejecutar la app
FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY build/libs/api-banco-crud-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

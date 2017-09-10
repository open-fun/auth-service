FROM openjdk:9-jre-slim
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar /workspace/app.jar
WORKDIR /workspace
EXPOSE 80
CMD ["java", "--add-modules", "java.xml.bind", "-jar", "app.jar", "--server.port=80"]

HEALTHCHECK CMD curl --fail http://localhost/health.json || exit 1
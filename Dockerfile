FROM openjdk:8-jre
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar /workspace/app.jar
WORKDIR /workspace
EXPOSE 80
CMD ["java", "-jar", "app.jar", "--server.port=80"]

HEALTHCHECK --interval=5s --timeout=10s \
  CMD curl --fail http://localhost/health.json || exit 1
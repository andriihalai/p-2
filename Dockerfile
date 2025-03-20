FROM openjdk:latest

COPY out/artifacts/2_lab_jar/2-lab.jar app.jar

CMD ["java", "-jar", "app.jar"]


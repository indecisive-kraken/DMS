FROM amazon-corretto:21
WORKDIR /dms/src

COPY ./target/docker-java-jar-0.0.1-SNAPSHOT.jar app.jar
RUN ./gradlew clean build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
FROM openjdk:20
VOLUME /tmb
EXPOSE 8080
add ./target/ecommerce-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
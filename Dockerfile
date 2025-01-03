FROM openjdk:21-jdk

WORKDIR /app

COPY target/chatgpt-api-0.0.1-SNAPSHOT.jar /app/chatgpt-api.jar

EXPOSE 8080

CMD ["java", "-jar", "chatgpt-api.jar"]
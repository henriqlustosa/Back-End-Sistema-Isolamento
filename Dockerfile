FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/*.jar /app/isolamento-api.jar

EXPOSE 8080

CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=${PORT} isolamento-jar a-api.jar
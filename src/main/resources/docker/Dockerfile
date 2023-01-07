FROM eclipse-temurin:17-jdk-jammy

RUN sed -i 's/https/http/g' /etc/apk/repositories
RUN apk update && apk upgrade

VOLUME /tmp
EXPOSE 8080

ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]

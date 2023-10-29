FROM eclipse-temurin:17-jre-ubi9-minimal

RUN mkdir /app

WORKDIR /app

ARG JAR_FILE

ENV LINUX_JAR_FILE=${JAR_FILE}

ADD ./target/RSO-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "app.jar"]
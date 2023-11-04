FROM maven:3.8.3-openjdk-17 AS build

COPY ./ /app

WORKDIR /app

RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM eclipse-temurin:17-jre-ubi9-minimal

RUN mkdir /app

WORKDIR /app

ARG JAR_FILE

ENV LINUX_JAR_FILE=${JAR_FILE}

ADD ./RSO-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "app.jar"]

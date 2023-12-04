FROM node:17-alpine3.12 AS build

COPY ./ /app

WORKDIR /app

RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM eclipse-temurin:17-jre-ubi9-minimal

RUN mkdir /app

WORKDIR /app

ARG JAR_FILE

ENV LINUX_JAR_FILE=${JAR_FILE}

ENV DB_URL=${JAR_FILE}

COPY COPY . .

EXPOSE 3000

CMD ["npm", "start"]
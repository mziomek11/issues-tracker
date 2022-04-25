FROM openjdk:17-alpine AS dev

ARG MODULE_NAME

RUN apk add inotify-tools

WORKDIR /usr/src/app

COPY . .

RUN cd issues-tracker/ && ./mvnw install -pl ${MODULE_NAME} -am

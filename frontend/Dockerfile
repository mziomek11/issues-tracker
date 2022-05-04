FROM node:16-alpine as dev

WORKDIR /usr/src/cache

COPY package.json yarn.lock ./

RUN yarn install

WORKDIR /usr/src/app

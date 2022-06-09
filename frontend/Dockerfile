FROM node:16-alpine as dev

WORKDIR /usr/src/app

COPY entrypoint.sh ./

RUN chmod +x ./entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]

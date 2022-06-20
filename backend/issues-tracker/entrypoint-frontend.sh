#!/bin/sh

cd issues-tracker
./mvnw -Dmaven.repo.local=/usr/mvn -Dmaven.test.skip=true package spring-boot:repackage -pl $MODULE_NAME -am && cd $MODULE_NAME && cp -r target /usr/src/app

exec "$@"

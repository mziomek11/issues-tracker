#!/bin/bash

rm -rf /usr/src/app/node_modules/
cp -r /usr/src/cache/node_modules/ /usr/src/app/node_modules/
exec yarn dev --host

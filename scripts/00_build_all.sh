#!/usr/bin/env bash

for SERVICE in  secureServer secureUi
do
  (cd ../$SERVICE ; mvn clean package)
done

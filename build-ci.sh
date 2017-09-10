#!/usr/bin/env bash
dir=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
project_name=$(basename ${dir})
docker run -it --rm --name ${project_name} \
    -u $(id -u):$(id -g) \
    -v "$dir":/usr/src/${project_name} \
    -w /usr/src/${project_name} \
    maven:3-jdk-9 \
    mvn clean install

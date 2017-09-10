#!/usr/bin/env bash
export PROJECT_DIR=$(cd -P -- "$(dirname -- "$0")" && pwd -P)
export PROJECT_NAME=$(basename ${PROJECT_DIR})

docker-compose -f ${PROJECT_DIR}/docker/build/docker-compose.build.yml up --build --force-recreate
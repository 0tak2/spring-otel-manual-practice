#!/bin/sh

docker compose -f jaeger-docker-compose.yml up -d
echo "접속: http://localhost:16686"

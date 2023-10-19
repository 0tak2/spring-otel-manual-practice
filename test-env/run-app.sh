#!/bin/bash

show_help() {
  echo "패러미터는 log와 jaeger만 올 수 있습니다."
  echo "예시"
  echo "  ./run-app.sh log"
  echo "  ./run-app.sh jaeger"
}

if [ -z "$1" ]; then
  show_help
  exit 1
fi

export OTEL_SERVICE_NAME=Simple-TODO
if [ $1 = "log" ]; then
  export OTEL_TRACES_EXPORTER=logging \
     OTEL_METRICS_EXPORTER=logging \
     OTEL_LOGS_EXPORTER=logging
elif [ $1 = "jaeger" ]; then
  export OTEL_TRACES_EXPORTER=jaeger \
     OTEL_METRICS_EXPORTER=logging \
     OTEL_LOGS_EXPORTER=logging
else
  show_help
  exit 1
fi

cd ../otel-manual-inst/
./gradlew assemble
java -jar ./build/libs/otel-manual-inst-0.0.1-SNAPSHOT.jar

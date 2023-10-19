# spring-otel-manual-practice
OpenTelemetry Manual Instrumentation 연습

1. 다음과 같이 Jaeger와 앱을 실행

```sh
cd ./test-env/
./run-jaeger.sh
./run-app.sh jaeger
```

2. http://localhost:16686/ 접속

3. 여러 API를 호출하면서 모니터링

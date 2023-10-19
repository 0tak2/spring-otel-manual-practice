package kr.pe.otak2.study.otel.otelmanualinst.common;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class TraceAspect {
    private final OpenTelemetry openTelemetry;

    @Around("execution(* kr.pe.otak2.study.otel.otelmanualinst.controller.*.*(..))")
    public Object makeParentSpan(ProceedingJoinPoint joinPoint) throws Throwable {
        Tracer tracer = openTelemetry.getTracer(joinPoint.getClass().getName(), "0.1.0");
        Span span = tracer.spanBuilder(joinPoint.getSignature().getName()).startSpan();

        Object result;
        try (Scope scope = span.makeCurrent()) {
            result = joinPoint.proceed();
            span.setAttribute("args", Arrays.toString(joinPoint.getArgs()));
            span.setAttribute("return", result.toString());
        } catch (Throwable t) {
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }

        return result;
    }

    @Around("execution(* kr.pe.otak2.study.otel.otelmanualinst.service.*.*(..))")
    public Object makeNestedSpan(ProceedingJoinPoint joinPoint) throws Throwable {
        Tracer tracer = openTelemetry.getTracer(joinPoint.getClass().getName(), "0.1.0");
        Span span = tracer.spanBuilder(joinPoint.getSignature().getName())
                .setParent(Context.current())
                .startSpan();

        Object result;
        try (Scope scope = span.makeCurrent()) {
            result = joinPoint.proceed();
            span.setAttribute("args", Arrays.toString(joinPoint.getArgs()));
            span.setAttribute("return", result.toString());
        } catch (Throwable t) {
            span.recordException(t);
            throw t;
        } finally {
            span.end();
        }

        return result;
    }
}

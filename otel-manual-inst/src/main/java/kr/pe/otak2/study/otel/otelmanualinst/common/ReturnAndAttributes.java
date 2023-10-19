package kr.pe.otak2.study.otel.otelmanualinst.common;

import io.opentelemetry.api.common.Attributes;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class ReturnAndAttributes <T> {
    private T returnValue;
    private Attributes attributes;

    public T extract() {
        return returnValue;
    }

    public Attributes getAttributes() {
        Attributes copied = attributes;
        attributes = null;
        return copied;
    }
}

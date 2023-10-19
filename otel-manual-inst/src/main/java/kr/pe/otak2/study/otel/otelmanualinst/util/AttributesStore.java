package kr.pe.otak2.study.otel.otelmanualinst.util;

import io.opentelemetry.api.trace.Span;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AttributesStore {
    private final HashMap<String, HashMap<String, String>> store;
    private final TraceHelper traceHelper;

    public AttributesStore(TraceHelper traceHelper) {
        store = new HashMap<>();
        this.traceHelper = traceHelper;
    }

    private String makeKeyOf(String methodName) {
        return traceHelper.getUUIDofRequest() + methodName;
    }

    public void storeAttribute(String methodName, String key, String value) {
        HashMap<String, String> attributes = store.getOrDefault(makeKeyOf(methodName),
                new HashMap<>());

        attributes.put(key, value);
        store.put(makeKeyOf(methodName), attributes);
    }

    public Span addAttributesTo(String methodName, Span span) {
        HashMap<String, String> attributes = store.getOrDefault(makeKeyOf(methodName),
                new HashMap<>());

        attributes.forEach(span::setAttribute);

        store.remove(makeKeyOf(methodName));

        return span;
    }
}

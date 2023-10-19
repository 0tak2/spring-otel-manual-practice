package kr.pe.otak2.study.otel.otelmanualinst.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class TraceHelper {
    public String getUUIDofRequest() {
        return (String)((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getAttribute("uuid");
    }
}

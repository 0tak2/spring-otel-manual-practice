package kr.pe.otak2.study.otel.otelmanualinst.common;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("uuid", UUID.randomUUID().toString());

        chain.doFilter(request, response);
    }
}

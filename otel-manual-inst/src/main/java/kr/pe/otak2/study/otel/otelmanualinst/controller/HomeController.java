package kr.pe.otak2.study.otel.otelmanualinst.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping("/test")
    public String testHandler() {
        return "Hello, World!";
    }
}

package kr.pe.otak2.study.otel.otelmanualinst.controller;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import kr.pe.otak2.study.otel.otelmanualinst.dto.BasicResponse;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo")
@Slf4j
public class ToDoController {
    private final TodoService service;
    private final Tracer tracer;

    @Autowired
    public ToDoController(TodoService service, OpenTelemetry openTelemetry) {
        this.service = service;
        this.tracer = openTelemetry.getTracer(ToDoController.class.getName(), "0.1.0");
    }

    @PostMapping
    public BasicResponse<TodoDto> newTodoHandler(@RequestBody @Valid TodoDto dto) {
        return BasicResponse.success(service.addTodo(dto));
    }

    @GetMapping("/{todoId}")
    public BasicResponse<TodoDto> getOneTodoHandler(@PathVariable Long todoId) {
        return BasicResponse.success(service.getTodo(todoId));
    }

    @GetMapping
    public BasicResponse<List<TodoDto>> getTodoListHandler(@RequestParam(required = false) Boolean isComplete) {
        return BasicResponse.success(service.getTodoList(isComplete).extract());
    }

    @PutMapping("/{todoId}")
    public BasicResponse<TodoDto> updateTodoHandler(@PathVariable Long todoId, @RequestBody @Valid TodoDto dto) {
        return BasicResponse.success(service.updateTodo(todoId, dto));
    }

    @DeleteMapping
    public BasicResponse<?> deleteAllHandler(@RequestParam(required = false) Boolean isComplete) {
        service.deleteAllTodo(isComplete);
        return BasicResponse.successWithoutPayload();
    }

    @DeleteMapping("/{todoId}")
    public BasicResponse<?> deleteOneTodoHandler(@PathVariable Long todoId) {
        service.deleteTodo(todoId);
        return BasicResponse.successWithoutPayload();
    }
}

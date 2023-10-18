package kr.pe.otak2.study.otel.otelmanualinst.controller;

import kr.pe.otak2.study.otel.otelmanualinst.dto.BasicResponse;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/todo")
@AllArgsConstructor
public class ToDoController {
    private final TodoService service;

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
        return BasicResponse.success(service.getTodoList(isComplete));
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

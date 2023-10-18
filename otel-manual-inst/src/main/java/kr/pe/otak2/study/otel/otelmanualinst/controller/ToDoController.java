package kr.pe.otak2.study.otel.otelmanualinst.controller;

import kr.pe.otak2.study.otel.otelmanualinst.dto.BasicResponse;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/todo")
@AllArgsConstructor
public class ToDoController {
    TodoService service;

    @PostMapping
    public BasicResponse<TodoDto> newTodoHandler(@RequestBody @Valid TodoDto dto) {
        return BasicResponse.success("Todo를 성공적으로 추가했습니다.", service.addTodo(dto));
    }
}

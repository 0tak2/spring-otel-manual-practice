package kr.pe.otak2.study.otel.otelmanualinst.service;

import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import kr.pe.otak2.study.otel.otelmanualinst.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    TodoRepository repository;

    @Override
    @Transactional
    public TodoDto addTodo(TodoDto dto) {
        Todo savedTodo = repository.save(Todo.from(dto));

        return TodoDto.from(savedTodo);
    }
}

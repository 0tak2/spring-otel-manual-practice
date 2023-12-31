package kr.pe.otak2.study.otel.otelmanualinst.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import kr.pe.otak2.study.otel.otelmanualinst.common.CustomException;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
import kr.pe.otak2.study.otel.otelmanualinst.common.ReturnAndAttributes;
import kr.pe.otak2.study.otel.otelmanualinst.controller.ToDoController;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import kr.pe.otak2.study.otel.otelmanualinst.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;
    private final Tracer tracer;

    public TodoServiceImpl(TodoRepository repository, OpenTelemetry openTelemetry) {
        this.repository = repository;
        this.tracer = openTelemetry.getTracer(TodoServiceImpl.class.getName(), "0.1.0");
    }

    @Override
    @Transactional
    public TodoDto addTodo(TodoDto dto) {
        Todo savedTodo = repository.save(Todo.from(dto));

        return TodoDto.from(savedTodo);
    }

    @Override
    @Transactional
    public TodoDto getTodo(Long todoId) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        return TodoDto.from(todo);
    }

    @Override
    @Transactional
    public ReturnAndAttributes<List<TodoDto>> getTodoList(Boolean isComplete) {
        List<Todo> todoList = isComplete != null ? repository.findByIsComplete(isComplete)
                : repository.findAll();

        List<TodoDto> todoDtoList = todoList.stream().map(TodoDto::from).toList();

        return ReturnAndAttributes.<List<TodoDto>>builder()
                .returnValue(todoDtoList)
                .attributes(
                        Attributes.of(
                                AttributeKey.stringKey("countAll"),
                                String.valueOf(todoDtoList.size()),
                                AttributeKey.stringKey("countComplete"),
                                String.valueOf(
                                        todoDtoList.stream().filter(TodoDto::getIsComplete).toList().size()
                                )
                        )
                )
                .build();
    }

    @Override
    @Transactional
    public TodoDto updateTodo(Long todoId, TodoDto dto) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        todo.setContent(dto.getContent());
        todo.setComplete(dto.getIsComplete());

        return TodoDto.from(repository.save(todo));
    }

    @Override
    @Transactional
    public void deleteAllTodo(Boolean isComplete) {
        if (isComplete != null) {
            repository.deleteAllByIsComplete(isComplete);
        } else {
            repository.deleteAll();
        }
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = repository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));

        repository.delete(todo);
    }
}

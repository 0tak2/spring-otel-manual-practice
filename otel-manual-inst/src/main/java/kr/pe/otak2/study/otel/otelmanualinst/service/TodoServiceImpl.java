package kr.pe.otak2.study.otel.otelmanualinst.service;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import kr.pe.otak2.study.otel.otelmanualinst.common.CustomException;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
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
        Span childSpan = tracer.spanBuilder("addTodo")
                .setParent(Context.current())
                .startSpan();

        Todo savedTodo = repository.save(Todo.from(dto));

        childSpan.setAttribute("new-data", TodoDto.from(savedTodo).toString());
        childSpan.end();

        return TodoDto.from(savedTodo);
    }

    @Override
    @Transactional
    public TodoDto getTodo(Long todoId) {
        Span span = tracer.spanBuilder("getTodo")
                .setParent(Context.current())
                .startSpan();

        Todo todo = null;
        try {
            todo = repository.findById(todoId).orElseThrow(
                    () -> new CustomException(ErrorDetail.NOT_FOUND_TODO_ERROR));
            span.setAttribute("got-data", TodoDto.from(todo).toString());
        } catch (Throwable t) {
            span.recordException(t, Attributes.builder().put("exception", t.getMessage()).build());
            throw t;
        } finally {
            span.end();
        }

        return TodoDto.from(todo);
    }

    @Override
    @Transactional
    public List<TodoDto> getTodoList(Boolean isComplete) {
        List<Todo> todoList = isComplete != null ? repository.findByIsComplete(isComplete)
                : repository.findAll();

        return todoList.stream().map(TodoDto::from).toList();
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

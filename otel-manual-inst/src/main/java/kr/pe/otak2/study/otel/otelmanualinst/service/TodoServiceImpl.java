package kr.pe.otak2.study.otel.otelmanualinst.service;

import kr.pe.otak2.study.otel.otelmanualinst.common.CustomException;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetail;
import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import kr.pe.otak2.study.otel.otelmanualinst.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;

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

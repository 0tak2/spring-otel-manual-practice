package kr.pe.otak2.study.otel.otelmanualinst.repository;

import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}

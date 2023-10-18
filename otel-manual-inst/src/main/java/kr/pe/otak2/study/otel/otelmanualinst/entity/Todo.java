package kr.pe.otak2.study.otel.otelmanualinst.entity;

import kr.pe.otak2.study.otel.otelmanualinst.dto.TodoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    @ColumnDefault("false")
    private boolean isComplete;

    @Column
    @CreationTimestamp
    private LocalDateTime createDt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDt;

    public static Todo from(TodoDto dto) {
        return Todo.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .isComplete(dto.isComplete())
                .build();
    }
}

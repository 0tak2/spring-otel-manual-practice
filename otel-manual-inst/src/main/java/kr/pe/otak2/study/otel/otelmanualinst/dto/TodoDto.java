package kr.pe.otak2.study.otel.otelmanualinst.dto;


import kr.pe.otak2.study.otel.otelmanualinst.entity.Todo;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class TodoDto {
    private Long id;

    @NotNull
    private String content;

    private boolean isComplete;

    public static TodoDto from(Todo entity) {
        return TodoDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .isComplete(entity.isComplete())
                .build();
    }
}

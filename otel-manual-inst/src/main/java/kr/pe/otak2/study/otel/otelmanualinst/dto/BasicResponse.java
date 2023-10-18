package kr.pe.otak2.study.otel.otelmanualinst.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.pe.otak2.study.otel.otelmanualinst.common.ErrorDetailEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BasicResponse<T> {
    private String message;

    private boolean result;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;

    private int code;

    public static BasicResponse<?> error(ErrorDetailEnum errorDetail) {
        return BasicResponse.builder()
                .message(errorDetail.getMessage())
                .result(false)
                .code(errorDetail.getCode())
                .build();
    }

    public static BasicResponse<?> errorWithAdditionalInfo(ErrorDetailEnum errorDetail, String info) {
        return BasicResponse.builder()
                .message(errorDetail.getMessage() + "\n" + info)
                .result(false)
                .code(errorDetail.getCode())
                .build();
    }

    public static BasicResponse<?> successWithoutPayload(String message) {
        return BasicResponse.builder()
                .message(message)
                .result(true)
                .code(20000)
                .build();
    }

    public static <T> BasicResponse<T> success(String message, T payload) {
        return BasicResponse.<T>builder()
                .message(message)
                .result(true)
                .payload(payload)
                .code(20000)
                .build();
    }
}

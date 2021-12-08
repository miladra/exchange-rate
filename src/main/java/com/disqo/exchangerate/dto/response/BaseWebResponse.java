package com.disqo.exchangerate.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class BaseWebResponse<T> implements Serializable {

    private static final long serialVersionUID = 951503127196410560L;

    private String result;
    private String errorMessage;
    private T data;

    public static BaseWebResponse successNoData() {
        return BaseWebResponse.builder()
                .result("success")
                .build();
    }

    public static <T> BaseWebResponse<T> successWithData(T data) {
        return BaseWebResponse.<T>builder()
                .data(data)
                .result("success")
                .build();
    }

    public static BaseWebResponse error(String errorMessage) {
        return BaseWebResponse.builder()
                .errorMessage(errorMessage)
                .result("error")
                .build();
    }
}

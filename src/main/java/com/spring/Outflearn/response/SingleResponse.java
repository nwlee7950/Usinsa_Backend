package com.spring.Outflearn.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResponse<T> extends CommonResponse {
    private T data;
}

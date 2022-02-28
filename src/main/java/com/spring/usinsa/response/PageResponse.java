package com.spring.usinsa.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> extends CommonResponse {

    private int pagePerCount;
    private long totalCount;
}

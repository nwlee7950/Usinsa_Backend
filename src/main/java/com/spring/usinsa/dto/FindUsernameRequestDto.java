package com.spring.usinsa.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUsernameRequestDto {

    private String email;
    private String name;
}

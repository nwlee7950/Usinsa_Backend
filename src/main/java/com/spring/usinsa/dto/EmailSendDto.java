package com.spring.usinsa.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailSendDto {

    private String to;
    private String subject;
    private String text;
}

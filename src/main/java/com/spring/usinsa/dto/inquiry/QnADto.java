package com.spring.usinsa.dto.inquiry;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnADto {

    private long id;
    private long orderId;
    private String category;
    private String email;
    private String name;
    private String phone;
    private String title;
    private String body;
    private long createdAt;

}

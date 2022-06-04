package com.spring.usinsa.dto.inquiry;

import com.spring.usinsa.model.inquiry.Qna;
import lombok.*;

public class QnADto {

    @Getter
    public static class Request{
        private long id;
        private long orderId;
        private String qnaCategory;
        private String email;
        private String name;
        private String phone;
        private String title;
        private String body;
        private long createdAt;

        public Qna toQnaEntity(long userId){
            return Qna.builder()
                    .orderId(orderId)
                    .createdAt(System.currentTimeMillis())
                    .email(email)
                    .name(name)
                    .phone(phone)
                    .title(title)
                    .body(body)
                    .userId(userId)
                    .build();
        }
    }

}

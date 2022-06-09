package com.spring.usinsa.dto.inquiry;

import com.spring.usinsa.model.inquiry.Qna;
import com.spring.usinsa.model.inquiry.QnaAnswer;
import com.spring.usinsa.model.inquiry.QnaCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class QnADto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Request{
        private Long userId;
//        private Long orderId;
        private Long qnaCategoryId;
        private String email;
        private String name;
        private String phone;
        private String title;
        private String body;
//        private List<MultipartFile> images;

        public Qna toQnaEntity(QnaCategory qnaCategory){
            return Qna.builder()
//                    .orderId(orderId)
                    .email(email)
                    .name(name)
                    .phone(phone)
                    .title(title)
                    .body(body)
                    .userId(userId)
                    .qnaCategory(qnaCategory)
                    .build();
        }
    }

    @Getter
    @Setter @Builder
    public static class Response{

        private Long qnaId;
        private String qnaCategory;
        private String email;
        private String name;
        private String phone;
        private String title;
        private String body;
        private Long createdAt;

        private Boolean existsAnswer;
        private String answerBody;
//        private List<String> images;

        public static QnADto.Response toQnaResponseDto(Qna qna){

            // 답변이 안달렸을 경우를
            String answerBody = "";
            Boolean existAnswer = false;

            if(qna.getQnaAnswer() != null) {
                existAnswer = true;
                answerBody = qna.getQnaAnswer().getBody();
            }

            return Response.builder()
                    .qnaCategory(qna.getQnaCategory().getTitle())
                    .email(qna.getEmail())
                    .name(qna.getName())
                    .phone(qna.getPhone())
                    .title(qna.getTitle())
                    .body(qna.getBody())
                    .qnaId(qna.getId())
                    .createdAt(qna.getCreatedAt())
                    .existsAnswer(existAnswer)
                    .answerBody(answerBody)

//                    .images(qna.getQnaImages().stream().map(m -> m.getImage()).collect(Collectors.toList()) )
                    .build();
        }
    }

}

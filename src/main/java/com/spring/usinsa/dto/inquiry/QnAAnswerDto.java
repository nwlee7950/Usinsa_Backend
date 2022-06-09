package com.spring.usinsa.dto.inquiry;

import com.spring.usinsa.model.inquiry.Qna;
import com.spring.usinsa.model.inquiry.QnaAnswer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QnAAnswerDto {
    private Long qnaId;
    private String body;
    private Long qnaAnswerId;
    private Long createAt;
    private Long updateAt;

    public QnaAnswer toQnaAnswerEntity(Qna qna){
        return QnaAnswer.builder()
                .body(body)
                .qna(qna)
                .build();
    }

    public static QnAAnswerDto toQnaAnswerDto(QnaAnswer qnaAnswer){

        return QnAAnswerDto.builder()
                .body(qnaAnswer.getBody())
                .qnaAnswerId(qnaAnswer.getId())
                .createAt(qnaAnswer.getCreatedAt())
                .updateAt(qnaAnswer.getUpdatedAt())
                .build();
    }
}

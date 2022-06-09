package com.spring.usinsa.dto.inquiry;

import com.spring.usinsa.model.inquiry.QnaCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QnACategoryDto {
    private String qnaTitle;
    private Long qnaId;

    public QnACategoryDto(QnaCategory qna){
        this.qnaTitle = qna.getTitle();
        this.qnaId = qna.getId();
    }
}

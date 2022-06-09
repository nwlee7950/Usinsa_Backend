package com.spring.usinsa.model.inquiry;

import com.spring.usinsa.dto.inquiry.QnAAnswerDto;
import com.spring.usinsa.model.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private QnaCategory qnaCategory;

    @OneToMany
    @JoinColumn(name = "qna_id")
    private List<QnaImage> qnaImages;

    @OneToOne(mappedBy = "qna", fetch = FetchType.EAGER)
    private QnaAnswer qnaAnswer;

    private Long orderId;
    private Long userId;
    private String email;
    private String name;
    private String phone;
    private String title;
    private String body;
}

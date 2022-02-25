package com.spring.usinsa.model.inquiry;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Qna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private QnaCategory qnaCategory;

    @OneToMany
    @JoinColumn(name = "qna_id")
    private List<QnaImage> qnaImages;

    private long userId;
    private long orderId;
    private String email;
    private String name;
    private String phone;
    private String title;
    private String body;
    private long createdAt;

}

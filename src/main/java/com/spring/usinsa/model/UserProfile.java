package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

    // User ID 를 PK 로 가짐
    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    private Tier tier;

    private String birth;   // 생년월일
    private String gender;  // 성별
    private Long height;    // 키
    private Long weight;    // 몸무게
    private String refundAccount;   // 환불 계좌


    public UserProfile(Long userId) {
        this.userId = userId;
    }

}

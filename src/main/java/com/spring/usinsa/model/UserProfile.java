package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    @Column
    private String profileImage;

    @Column(length = 30)
    private String nickname;

    @Column(length = 30)
    private String job;

    @Column
    private String introduction;

    public UserProfile(Long userId) {
        this.userId = userId;
    }

    public UserProfile(Long userId, String nickname, String profileImage) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileImage = profileImage;
    }
}

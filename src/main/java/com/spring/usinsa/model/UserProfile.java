package com.spring.usinsa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column
    private String profileImage;

    @Column(length = 30)
    private String nickname;

    @Column(length = 30)
    private String job;

    @Column(length = 30)
    private String email;

    @Column
    private String introduction;

    public UserProfile(long userId) {
        this.userId = userId;
    }

    public UserProfile(long userId, String nickname, String email, String profileImage) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }
}

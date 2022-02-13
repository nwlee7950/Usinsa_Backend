package com.spring.usinsa.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username; // 소셜 가입의 경우 아이디 없이 회원가입 시켜야 하므로 username null 허용
    private String password; // 소셜 가입의 경우 비밀번호가 필요 없으므로 password null 허용
    private String name; // 실명
    private String nickname;
    private String phone;

    @Email
    @Column(nullable = false, unique = true, length = 30)
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role; // 회원 권한 (ex - user, admin, super_admin)

    @Enumerated(EnumType.STRING)
    private Social social; // 소셜명 (ex - usinsa, kakao, naver, google)
    private String socialId; // 소셜의 PK
    private boolean disable; // 회원탈퇴 여부

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

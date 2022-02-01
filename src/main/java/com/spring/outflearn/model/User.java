package com.spring.outflearn.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTimeEntity implements UserDetails {

//    @AllArgsConstructor
//    @Getter
//    public enum Role{
////        SUPER_ADMIN("ROLE_SUPER_ADMIN"),
////        ADMIN("ROLE_ADMIN");
//        USER("ROLE_USER");
//
//        String value;
//    }

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(
//            name="user_role",
//            joinColumns=@JoinColumn(name="user_id")
//    )
//    @Column(name = "role")
//    @Builder.Default
//    private List<String> roles = new ArrayList<>();

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    public List<String> getRoles() {
//        return roles;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Email
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

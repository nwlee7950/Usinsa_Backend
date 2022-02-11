package com.spring.usinsa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResetPasswordRequestDto {

    private String code;
    private String newPassword;
    private String newPasswordConfirm;
}

package com.spring.usinsa.service;

import com.spring.usinsa.dto.EmailSendDto;
import com.spring.usinsa.model.User;

import javax.mail.MessagingException;

public interface MailService {

    void sendMail(EmailSendDto emailSendDto) throws MessagingException;

    EmailSendDto setIdMailDto(String email, User user);
    EmailSendDto setPwMailDto(String email, String code);
}

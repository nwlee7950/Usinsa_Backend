package com.spring.usinsa.serviceImpl;

import com.spring.usinsa.dto.EmailSendDto;
import com.spring.usinsa.exception.ApiErrorCode;
import com.spring.usinsa.exception.ApiException;
import com.spring.usinsa.model.User;
import com.spring.usinsa.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    private final String FIND_USERNAME_SUBJECT = "[아웃프런] 입력하신 이메일 주소로 가입된 아이디입니다.";
    private final String FIND_PASSWORD_SUBJECT = "[아웃프런] 비밀번호 변경을 위한 주소입니다.";
    private final String UPDATE_PASSWORD_URL = "<a href='http://localhost:8080/frontend-url?code={code}'>링크</a>";
    
    @Async
    @Override
    public void sendMail(EmailSendDto emailSendDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("rlawodus518@gmail.com");
        helper.setTo(emailSendDto.getTo());
        helper.setSubject(emailSendDto.getSubject());
        helper.setText(emailSendDto.getText(), true);

//         파일 첨부
//        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\creativesoft\\Pictures\\1.PNG"));
//        helper.addAttachment("fileName", file);

        try {
            javaMailSender.send(message);
            log.info("이메일 수신 주소: {}", emailSendDto.getTo());
        } catch (MailException e) {
            log.error(String.valueOf(e));
            throw new ApiException(ApiErrorCode.MAIL_SEND_FAIL);
        }
    }

    @Override
    public EmailSendDto setIdMailDto(String email, User user) {
        EmailSendDto emailSendDto = EmailSendDto.builder()
                .to(email)
                .subject(FIND_USERNAME_SUBJECT)
                .text("입력하신 이메일로 가입된 아이디는 " + user.getUsername() + " 입니다.")
                .build();

        return emailSendDto;
    }

    @Override
    public EmailSendDto setPwMailDto(String email, String code) {
        EmailSendDto emailSendDto = EmailSendDto.builder()
                .to(email)
                .subject(FIND_PASSWORD_SUBJECT)
                .text(UPDATE_PASSWORD_URL.replace("{code}", code) + " 를 클릭하셔서 사용하실 비밀번호를 새로 설정해주세요.")
                .build();

        return emailSendDto;
    }
}

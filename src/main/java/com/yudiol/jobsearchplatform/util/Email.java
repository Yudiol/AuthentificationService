package com.yudiol.jobsearchplatform.util;

import com.yudiol.jobsearchplatform.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Email {

    private final EmailService emailService;

    @Value("${email.url}")
    private String emailUrl;

    public void sendEmail(String email, String activeCode) {
        emailService.sendSimpleEmail(email, "Подтверждение E-mail", String.format(
                "Привет, %s! \n" +
                        "Пожалуйста пройдите по ссылке что бы подтвердить свою учетную запись на JobSearchPlatform : " + emailUrl + "/auth/activate/%s",
                email,
                activeCode));
    }
}

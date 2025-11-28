package com.jsp.book.util;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailHelper {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendOtp(int otp, String name, String email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            // IMPORTANT FIX – must be your Gmail ID
            helper.setFrom("harishsiva1703@gmail.com", "Book-My-Ticket");

            helper.setTo(email);
            helper.setSubject("OTP for Creating Account with BookMyTicket");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("otp", otp);

            String text = templateEngine.process("email-template.html", context);
            helper.setText(text, true);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println("====== OTP SEND ERROR ======");
            e.printStackTrace();   // <-- SHOW FULL ERROR
        }
    }
}

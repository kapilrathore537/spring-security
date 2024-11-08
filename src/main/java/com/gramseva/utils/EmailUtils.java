package com.gramseva.utils;

import com.gramseva.payload.responses.ApiResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailSender;

    public ResponseEntity<ApiResponse> sendEmail(String sendTo, String otp) throws MessagingException, IOException {

        String subject = "Email Verification";

        // Load the HTML template
        ClassPathResource htmlResource = new ClassPathResource("templates/VerificationEmail.html");
        InputStream inputStream = htmlResource.getInputStream();
        Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
        String htmlContent = scanner.hasNext() ? scanner.next() : "";

        htmlContent = htmlContent.replace("[UserName]", sendTo);
        htmlContent = htmlContent.replace("[OTP]",otp);
        htmlContent = htmlContent.replace("[verification_link]",
                "http://localhost:4200/login?email=" + sendTo + "&&value=" + otp);

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("javatesting406@gmail.com");
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

//		FileSystemResource file = new FileSystemResource(new File("C:/Users/Hp/Downloads/New-file.gif"));
//		helper.addAttachment("Invoice", file);

        emailSender.send(message);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().message("Email Sent Successfully to " + sendTo).build());
    }

}

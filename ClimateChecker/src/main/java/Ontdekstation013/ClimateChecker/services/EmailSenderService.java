package Ontdekstation013.ClimateChecker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String name, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kayletmail@gmail.com");
        message.setTo(toEmail);
        message.setSubject(String.format("Welcome %s", name));
        message.setText(String.format("Welcome %s", body));


        mailSender.send(message);

        System.out.printf("Mail Send");
    }

    public void sendSignupMail(String toEmail, String name) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        File file = new File(".\\ClimateChecker\\src\\main\\resources\\images\\ontdekstation013-logo.png");
        helper.addAttachment("logo.png", file);

        String body = "Je hebt net je account aangemaakt bij Ontdekstation 013,"
                + "<br>"
                + "<br>"
                + "<br>"
                + "<br>"
                + "Met vriendelijke groet,"
                + "<br>"
                + " Ontdekstation 013"
                + "<br>"
                + "<img src=\"cid:logo.png\"></img><br/>";

        helper.setTo("kayletmail@host.com");
        helper.setTo(toEmail);
        helper.setSubject(String.format("Welkom %s", name));
        helper.setText(body, true);

        mailSender.send(message);

        System.out.printf("Mail Send");
    }

}

package Ontdekstation013.ClimateChecker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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

    public void sendSignupMail(String toEmail, String name){
        String body = "Je hebt net je account aangemaakt bij Ontdekstation 013!";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kayleschooldrive@gmail.com");
        message.setTo(toEmail);
        message.setSubject(String.format("Welkom %s", name));
        message.setText(String.format(body));


        mailSender.send(message);

        System.out.printf("Mail Send");
    }

}

package space.sedov.server.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailServiceInterface {
    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("mdsedov@gmail.com");
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        emailSender.send(mail);
    }
}

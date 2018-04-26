package pro.xway.registration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pro.xway.registration.Config;
import pro.xway.registration.Constant;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService implements Constant {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public boolean sendConfirmation(String sendTo, String subject, String bodyMail) {
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            messageHelper.setFrom(Config.INFO_NICE_DECOR_RU);
            messageHelper.setSubject(subject);
            messageHelper.setText(buildTemplate(bodyMail), true);
            messageHelper.setTo(sendTo);

            mailSender.send(mailMessage);
            return true;
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String buildTemplate(String password){
        Context context = new Context();
        context.setVariable("password", password);
        return templateEngine.process("mail", context);
    }
}

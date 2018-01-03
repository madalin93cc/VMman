package ro.upb.dai.mcc.vmman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ro.upb.dai.mcc.vmman.service.MailService;

/**
 * Mail service implementation.
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(SimpleMailMessage simpleMessage) {
        emailSender.send(simpleMessage);
    }
}

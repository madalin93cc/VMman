package ro.upb.dai.mcc.vmman.service;

import org.springframework.mail.SimpleMailMessage;

/**
 * Service Interface for sending Emails.
 */
public interface MailService {
    void sendSimpleMessage(SimpleMailMessage message);
}

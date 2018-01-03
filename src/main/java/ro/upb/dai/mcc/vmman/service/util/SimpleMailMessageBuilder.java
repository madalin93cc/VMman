package ro.upb.dai.mcc.vmman.service.util;

import org.springframework.mail.SimpleMailMessage;

/**
 * SimpleMailMessageBuilder util class.
 */
public class SimpleMailMessageBuilder {
    private String to;
    private String subject;
    private String text;

    public SimpleMailMessageBuilder(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public SimpleMailMessageBuilder setTo(String to) {
        this.to = to;
        return this;
    }

    public SimpleMailMessageBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public SimpleMailMessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public SimpleMailMessage build() {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(this.to);
        sm.setSubject(this.subject);
        sm.setText(this.text);
        return sm;
    }
}

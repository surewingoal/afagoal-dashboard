package com.afagoal.config;

import com.afagoal.mail.AfagoalMainProperties;
import com.afagoal.mail.AfagoalMainSender;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
@Configuration
public class MailConfig {

    @Bean(name = "mailSender")
    public JavaMailSenderImpl createMailSender(AfagoalMainProperties afagoalMainProperties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(afagoalMainProperties.getSenderHost());
        sender.setPort(afagoalMainProperties.getSenderPost());
        sender.setUsername(afagoalMainProperties.getSenderUsername());
        sender.setPassword(afagoalMainProperties.getSenderPassword());
        sender.setDefaultEncoding(afagoalMainProperties.getSenderDefaultEncoding());
        sender.setJavaMailProperties(afagoalMainProperties.getSenderProperties());
        return sender;
    }

    @Bean
    public AfagoalMainSender afagoalMainSender(JavaMailSenderImpl mailSender, AfagoalMainProperties afagoalMainProperties) {
        AfagoalMainSender afagoalMainSender = new AfagoalMainSender(mailSender);
        afagoalMainSender.setOrganization(afagoalMainProperties.getMessageOrganization());
        afagoalMainSender.setBaseContent(afagoalMainProperties.getMessageBaseContent());
        afagoalMainSender.setFrom(afagoalMainProperties.getMessageFrom());
        return afagoalMainSender;
    }

    @Bean(name = "afagoalMainProperties")
    @ConfigurationProperties(prefix = "afagoal.mail")
    public AfagoalMainProperties afagoalMainProperties() {
        return new AfagoalMainProperties();
    }

}

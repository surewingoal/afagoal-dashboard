package com.afagoal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
@Configuration
public class MailConfig {

    // TODO  灵活配置

    private static final String HOST = "smtp.163.com";
    private static final int POST = 25;
    private static final String USERNAME = "18296154779@163.com";
    private static final String PASSWORD = "baocai123";
    private static final String DEFAULT_ENCODING = "Utf-8";
    private static final String PROPERTY_TIMEOUT = "mail.smtp.timeout";
    private static final String VALUE_TIMEOUT = "25000";
    private static final String PROPERTY_AUTH = "mail.smtp.auth";
    private static final String VALUE_AUTH = "false";

    @Bean(name = "mailSender")
    public JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(POST);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding(DEFAULT_ENCODING);
        Properties p = new Properties();
        p.setProperty(PROPERTY_TIMEOUT, VALUE_TIMEOUT);
        p.setProperty(PROPERTY_AUTH, VALUE_AUTH);
        sender.setJavaMailProperties(p);
        return sender;
    }

    @Bean
    public AfagoalMainSender afagoalMainSender(JavaMailSenderImpl mailSender){
        return new AfagoalMainSender(mailSender);
    }

}

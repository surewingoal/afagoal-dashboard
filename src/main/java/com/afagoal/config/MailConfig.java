package com.afagoal.config;

import com.afagoal.mail.AfagoalMailProperties;
import com.afagoal.mail.AfagoalMainSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
@Configuration
public class MailConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    @Bean
    public AfagoalMainSender afagoalMainSender(AfagoalMailProperties afagoalMailProperties) {
        AfagoalMainSender afagoalMainSender = new AfagoalMainSender(javaMailSender);
        afagoalMainSender.setOrganization(afagoalMailProperties.getOrganization());
        afagoalMainSender.setBaseContent(afagoalMailProperties.getBaseContent());
        afagoalMainSender.setFrom(afagoalMailProperties.getFrom());
        return afagoalMainSender;
    }

    @Bean(name = "afagoalMailProperties")
    @ConfigurationProperties(prefix = "afagoal.mail")
    public AfagoalMailProperties afagoalMainProperties() {
        return new AfagoalMailProperties();
    }

}

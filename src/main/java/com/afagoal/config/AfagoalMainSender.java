package com.afagoal.config;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
//TODO  放到工具包中
public class AfagoalMainSender {

    private JavaMailSenderImpl sender;

    private static final String ORGANIZATION = "AFAGOAL虚拟币行情";
    private static final String FROM = "18296154779@163.com";

    private static final String BASE_CONTENT = "<div style='float:right;'><a class='btn  btn-primary btn-block' href=http://118.24.27.218:8080/login>联系我们</a></div>";

    public AfagoalMainSender(JavaMailSenderImpl sender) {
        this.sender = sender;
    }


    public void send(String content, String to, String subject) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = sender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(FROM, ORGANIZATION);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content + BASE_CONTENT, true);
        //发送邮件
        sender.send(mimeMessage);
    }

}

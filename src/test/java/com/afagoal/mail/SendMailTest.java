package com.afagoal.mail;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
public class SendMailTest {

    @Test
    public void sendMailTest() throws MessagingException, UnsupportedEncodingException {
        AfagoalMainSender afagoalMainSender = new AfagoalMainSender(createMailSender());
        afagoalMainSender.setFrom("18296154779@163.com");
        afagoalMainSender.setOrganization("AFAGOAL");
        String content = "您关注的币种:XNN最近价格波动比较大。<br/>1天内，价格下降0.10%。<br/>2018-06-08价格：0.0067000000$；<br/>今日价格：0.0060000000$。";
        content += "<br/>";
        content += "<br/>";
        content += "<div style='float:right;'><a class='btn  btn-primary btn-block' href=http://localhost:18080/login>联系我们</a></div>";

        afagoalMainSender.send(content,"xiaxi@jianbaolife.com","币种价格浮动");
        afagoalMainSender.send(content,"756271987@qq.com","币种价格浮动");
    }

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.163.com");
        sender.setPort(25);
        sender.setUsername("18296154779@163.com");
        sender.setPassword("baocai1234");
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "25000");
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
    }

}

package com.afagoal.mail;

import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by BaoCai on 18/6/11.
 * Description:
 */
public class SendMailTest {

    @Test
    public void springMailTest() throws MessagingException, UnsupportedEncodingException {
        AfagoalMainSender afagoalMainSender = new AfagoalMainSender(createMailSender());
        afagoalMainSender.setFrom("18296154779@163.com");
        afagoalMainSender.setOrganization("AFAGOAL");
        String content = "您关注的币种:XNN最近价格波动比较大。<br/>1天内，价格下降0.10%。<br/>2018-06-08价格：0.0067000000$；<br/>今日价格：0.0060000000$。";
        content += "<br/>";
        content += "<br/>";
        content += "<div style='float:right;'><a class='btn  btn-primary btn-block' href=http://localhost:18080/login>联系我们</a></div>";

        afagoalMainSender.send(content, "xiaxi@jianbaolife.com", "币种价格浮动");
        afagoalMainSender.send(content, "756271987@qq.com", "币种价格浮动");
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


    @Test
    public void sendMailTest() throws UnsupportedEncodingException, MessagingException {
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.host", "smtp.163.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop); // 创建出与指定邮件服务器会话的session
        /*
         * 为了看清javamail这套API到底是如何向服务器发邮件的，可以把session的Debug开关打开，
         * 把这个调试开关打开，javamail这套API会把它与服务器的交互过程打印在命令行窗口
         */
        session.setDebug(true);
        Message message = createMessage(session);

        Transport ts = session.getTransport();
        ts.connect("18296154779@163.com", "baocai1234"); // 连接上邮件服务器，其内部会自动帮你进行base64编码
        ts.sendMessage(message, message.getAllRecipients()); // 向谁发送一封邮件
        ts.close(); // 断开与服务器的连接
    }

    private static Message createMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("18296154779@163.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("xiaxi@jianbaolife.com"));
        message.setSubject("hello");
        message.setContent("hello hello baocai", "text/html");
        message.saveChanges();
        return message;
    }

}

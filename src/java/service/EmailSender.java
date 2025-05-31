package service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.Properties;

public class EmailSender {

    public static void send(String to, String subject, String content) {
        final String from = "goviet1901@gmail.com";
        final String password = "jmcjkgtcqiwrlfsm"; // App password của Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, password);
                }
            }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Gửi tiêu đề với mã hóa UTF-8 để không lỗi tiếng Việt
            message.setSubject("=?UTF-8?B?" + Base64.getEncoder().encodeToString(subject.getBytes("UTF-8")) + "?=");

            // Gửi nội dung với charset UTF-8
            message.setContent(content, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Email sent successfully");

        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

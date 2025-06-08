/*
 * Copyright (C) 2025, Group 6.
 * ProjectCode/Short Name of Application: TravelSystemService 
 * Support Management and Provide Travel Service System 
 *
 * Record of change:
 * DATE        Version    AUTHOR            DESCRIPTION
 * 2025-06-07  1.0        Hà Thị Duyên          First implementation
 */

/**
 * Handles sending emails with optional attachments to specified recipients.<br>
 * This class configures the SMTP server, authenticates using provided credentials,
 * and manages text content and file attachments.<br>
 * <p>Bugs: No validation for invalid email addresses.</p>
 *
 * @author Hà Thị Duyên 
 */
package service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Base64;
import java.util.Properties;
import java.io.File;
import java.io.IOException;

public class EmailSender {

    public final static String FROM_EMAIL = "goviet1901@gmail.com";
    public final static String APP_PASSWORD = "jmcjkgtcqiwrlfsm";

    /**
     * Sends an email with optional attachments to the specified recipient.<br>
     * This method configures the SMTP server, authenticates with the provided
     * credentials, and handles both text content and file attachments.<br>
     *
     * @param to The recipient's email address, must be a valid email format
     * @param subject The subject line of the email, encoded in UTF-8
     * @param content The HTML content of the email
     * @param attachments An array of file paths for attachments, or null if
     * none
     * @return true if the email is sent successfully, false otherwise
     * @throws MessagingException If an error occurs during email sending
     * @throws IllegalArgumentException If the recipient email or subject is
     * invalid
     * @throws java.io.IOException If an error occurs while attaching files
     */

    // Block comment to describe the method
    /* 
     * Validates input parameters and sends email with attachments.
     * Uses Gmail SMTP server with TLS authentication.
     * Handles exceptions for email sending and file attachment errors.
     */
    public static void send(String to, String subject, String content, String[] attachments) throws MessagingException, IOException {
        /* Validate input parameters */
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient email is required");
        }
        if (subject == null) {
            throw new IllegalArgumentException("Subject is required");
        }
        /* Validate email format */
        try {
            InternetAddress emailAddr = new InternetAddress(to);
            emailAddr.validate();
        } catch (AddressException e) {
            throw new IllegalArgumentException("Invalid recipient email address");
        }
        /* Truncate subject if too long */
        if (subject.length() > 255) {
            subject = subject.substring(0, 255);
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");// Set Gmail SMTP server
        props.put("mail.smtp.port", "587");// Use TLS port
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        }
        );

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            /* Encode the subject in UTF-8 to support Vietnamese characters */
            message.setSubject("=?UTF-8?B?" + Base64.getEncoder().encodeToString(subject.getBytes("UTF-8")) + "?=");

            /* Create a MimeMultipart object to combine content and attachments */
            MimeMultipart multipart = new MimeMultipart();

            /* Add text content */
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");
            multipart.addBodyPart(messageBodyPart);

            /* Add attachments if provided */
            if (attachments != null) {
                for (String attachmentPath : attachments) {
                    File attachmentFile = new File(attachmentPath);
                    if (attachmentFile.exists()) {
                        messageBodyPart = new MimeBodyPart();
                        messageBodyPart.attachFile(attachmentFile);
                        multipart.addBodyPart(messageBodyPart);
                    }
                }
            }

            /* Set the message content to multipart */
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully with attachments");

        } catch (MessagingException | java.io.IOException e) {
            throw e; // Throw exception to be handled by caller
        }
    }

    /**
     * Sends an email without attachments to the specified recipient.<br>
     * This is a legacy method for backward compatibility, internally calling
     * the main send method with no attachments.<br>
     *
     * @param to The recipient's email address, must be a valid email format
     * @param subject The subject line of the email, encoded in UTF-8
     * @param content The HTML content of the email
     * @return true if the email is sent successfully, false otherwise
     * @throws MessagingException If an error occurs during email sending
     * @throws IllegalArgumentException If the recipient email or subject is
     * invalid
     */

    // Block comment to describe the method
    /* 
     * Legacy method for sending email without attachments.
     * Delegates to the main send method with null attachments.
     * Maintains backward compatibility with older code.
     */
    public static void send(String to, String subject, String content) throws MessagingException, IOException {
        send(to, subject, content, null);// IOException không xảy ra khi attachments là null
    }

}

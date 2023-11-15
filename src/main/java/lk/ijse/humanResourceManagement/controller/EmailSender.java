package lk.ijse.humanResourceManagement.controller;

import javafx.scene.image.WritableImage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class EmailSender {
    public static void sendEmail(String to, String subject, String body, WritableImage qrCodeBytes, String fileName) throws MessagingException {
        // Email configuration
        String from = "glen.alloy@yahoo.com";
        String password = "osxsxqsjbqawfukk";

        // Yahoo SMTP settings for less secure apps
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.mail.yahoo.com");

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        session.setDebug(true);

        // Create MimeMessage
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);

        // Create MimeBodyPart for email body
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(body);

        // Create MimeBodyPart for QR code attachment
        MimeBodyPart qrCodeAttachment = new MimeBodyPart();
        qrCodeAttachment.setContent(qrCodeBytes, "image/png");
        qrCodeAttachment.setFileName(fileName);

        // Create Multipart and add parts
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(qrCodeAttachment);

        // Set Multipart as the content of the message
        message.setContent(multipart);

        // Send the message
        Transport.send(message);
    }
}



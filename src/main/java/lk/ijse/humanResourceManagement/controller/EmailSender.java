package lk.ijse.humanResourceManagement.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class EmailSender implements Runnable{
    private String msg;
    private String to;
    private String subject;
    private WritableImage qrCodeImage;


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void sendMail() throws MessagingException {
        String from = "glenalloy8@gmail.com";
        String password = "zmzs njnk qtkd mvmm";
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(this.subject);
        mimeMessage.setText(this.msg);
        Transport.send(mimeMessage);

        System.out.println("sent");
    }

    @Override
    public void run() {
        if (msg != null) {
            try {
                sendMail();
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("not sent. empty msg!");
        }
    }

    public void setImage(WritableImage qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }
}



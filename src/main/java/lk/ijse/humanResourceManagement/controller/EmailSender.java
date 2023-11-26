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

    public void setImage(WritableImage qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public void sendMail() throws MessagingException, IOException {
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

        // Create a MimeMultipart object
        MimeMultipart multipart = new MimeMultipart();

        // Create the text part
        BodyPart textPart = new MimeBodyPart();
        textPart.setText(this.msg);

        // Add the text part to the MimeMultipart
        multipart.addBodyPart(textPart);

        // Create the image part
        if (qrCodeImage != null) {
            BodyPart imagePart = new MimeBodyPart();
            // Convert the WritableImage to InputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(qrCodeImage, null), "png", outputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            DataSource dataSource = new ByteArrayDataSource(inputStream, "image/png");
            imagePart.setDataHandler(new DataHandler(dataSource));
            imagePart.setFileName("qrCode.png");

            // Add the image part to the MimeMultipart
            multipart.addBodyPart(imagePart);
        }

        // Set the content of the message to the MimeMultipart
        mimeMessage.setContent(multipart);

        // Send the message
        Transport.send(mimeMessage);

        System.out.println("Email sent successfully");
    }

    @Override
    public void run() {
        if (msg != null) {
            try {
                sendMail();
            } catch (MessagingException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Email not sent. Empty message!");
        }
    }
}



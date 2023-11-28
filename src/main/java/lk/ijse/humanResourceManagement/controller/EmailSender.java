package lk.ijse.humanResourceManagement.controller;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


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

        MimeMultipart multipart = new MimeMultipart();

        BodyPart textPart = new MimeBodyPart();
        textPart.setText(this.msg);

        multipart.addBodyPart(textPart);

        if (qrCodeImage != null) {

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(qrCodeImage, null), "png", byteArrayOutputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDImageXObject imageXObject = PDImageXObject.createFromByteArray(document, byteArrayOutputStream.toByteArray(), "qrCode");
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            float centerX = (PDRectangle.A4.getWidth() - imageXObject.getWidth()) / 2;
            float centerY = (PDRectangle.A4.getHeight() - imageXObject.getHeight()) / 2;
            contentStream.drawImage(imageXObject, centerX, centerY);

            contentStream.close();


            ByteArrayOutputStream byteArrayOutputStreamPdf = new ByteArrayOutputStream();
            document.save(byteArrayOutputStreamPdf);
            document.close();
            DataSource dataSource = new ByteArrayDataSource(byteArrayOutputStreamPdf.toByteArray(), "application/pdf");

            BodyPart pdfPart = new MimeBodyPart();
            pdfPart.setDataHandler(new DataHandler(dataSource));
            pdfPart.setFileName("qrCode.pdf");


            multipart.addBodyPart(pdfPart);
        }

        mimeMessage.setContent(multipart);

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



package lk.ijse.humanResourceManagement.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;



import java.awt.*;
import java.awt.image.BufferedImage;

public class QRCodeGeneratorClass {
    public static QRCodeGeneratorClass CodeGenerator;

    public static BufferedImage generateQRCode(String data, int width, int height) {
        try {
            BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            return qrImage;
        } catch (Exception e) {
            throw new RuntimeException("Error generating QR code", e);
        }
    }
}

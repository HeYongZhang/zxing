package com.hyzgt.matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hyzgt.entity.LogoProperties;
import com.hyzgt.entity.MatrixProperties;
import com.sun.javafx.iio.ImageStorage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MatrixLogoCreate {

    private final int BLACK=0xFF000000;

    private final int WHITE = 0xFFFFFFFF;

    private MatrixProperties matrixProperties;

    private LogoProperties logoProperties;

    public MatrixLogoCreate(MatrixProperties matrixProperties,LogoProperties logoProperties){
        if(matrixProperties.getHints().containsKey(EncodeHintType.ERROR_CORRECTION)){
            matrixProperties.getHints().put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }
        this.matrixProperties = matrixProperties;
        this.logoProperties = logoProperties;
    }

    public BufferedImage createMatrix() throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(matrixProperties.getContext(), BarcodeFormat.QR_CODE,matrixProperties.getWidth(),matrixProperties.getHeight(),matrixProperties.getHints());
        BufferedImage bufferedImage = BitMatrixToBufferedImage(matrix);
        BufferedImage image = this.DrawMatrixLogo(bufferedImage);
        return image;
    }

    private BufferedImage DrawMatrixLogo(BufferedImage image) throws IOException {
        int matrixWidth = image.getWidth();
        int matrixHeight = image.getHeight();
        Image logo = ImageIO.read(new File(logoProperties.getLogoUri())).getScaledInstance(matrixWidth/5+2,matrixHeight/5+2,image.SCALE_SMOOTH);

        Graphics2D g2 = image.createGraphics();
        g2.drawImage(logo,matrixWidth/5*2,matrixHeight/5*2,null);

        //设置画笔
        BasicStroke stroke = new BasicStroke(2,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER);
        g2.setStroke(stroke);
        Rectangle2D rectangle2D  = new Rectangle2D.Float(matrixWidth/5*2,matrixHeight/5*2,matrixWidth/5+2,matrixHeight/5+2);
        g2.setColor(Color.white);
        g2.draw(rectangle2D);

        g2.dispose();
        image.flush();
        return image;
    }

    private BufferedImage BitMatrixToBufferedImage(BitMatrix matrix){
        BufferedImage image = new BufferedImage(matrix.getWidth(),matrix.getHeight(),BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < matrix.getWidth(); x++) {
            for (int y = 0; y < matrix.getHeight(); y++) {
                image.setRGB(x,y,matrix.get(x,y)?BLACK:WHITE);
            }
        }
        return image;
    }
}

package com.hyzgt.matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.hyzgt.entity.LogoProperties;
import com.hyzgt.entity.MatrixProperties;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MatrixCreate {

    private final int BLACK=0xFF000000;

    private final int WHITE = 0xFFFFFFFF;

    private MatrixProperties matrixProperties;

    public MatrixCreate(MatrixProperties properties){
        this.matrixProperties = properties;
    }

    public BitMatrix createMatrix() throws WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(matrixProperties.getContext(), BarcodeFormat.QR_CODE,matrixProperties.getWidth(),matrixProperties.getHeight(),matrixProperties.getHints());
       return matrix;
    }

    public BufferedImage createBufferedImage() throws WriterException {
        BitMatrix matrix = createMatrix();
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}

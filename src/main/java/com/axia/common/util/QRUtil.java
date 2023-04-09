package com.axia.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class QRUtil {
    public static void generateQR(String url, Map<String, Object> map) {
        int width = 200;
        int height = 200;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, width, height);
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            map.put("qr_code", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

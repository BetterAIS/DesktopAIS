package com.bais.dais;

import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import com.bais.dais.baisclient.models.Accommodation;
import javafx.scene.image.ImageView;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;

public class accommodation_Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label dormitory;

    @FXML
    private Label block;

    @FXML
    private Label floor;

    @FXML
    private Label cell;

    @FXML
    private Label room;

    @FXML
    private Label cost;

    @FXML
    private Label iban;

    @FXML
    private Label swift;

    @FXML
    private Label variable_symbol;

    @FXML
    private Label arrears;

    @FXML
    private ImageView scantopay;

    public void loadAccommodation(Accommodation accommodation) {
        Accommodation.Residence residence = accommodation.getResidence();
        dormitory.setText(residence.getDormitory());
        block.setText(residence.getBlock());
        floor.setText(Integer.toString(residence.getFloor()));
        cell.setText(Integer.toString(residence.getCell()));
        room.setText(Integer.toString(residence.getRoom()));
        cost.setText(Integer.toString(residence.getCost()));

        Accommodation.Payment payment = accommodation.getPayment();

        iban.setText(payment.getIban());
        swift.setText(payment.getSwift());
        variable_symbol.setText(payment.getVariable_symbol());
        arrears.setText(Integer.toString(payment.getArrears()));

        Image qrCodeImage = null;
        try {
            qrCodeImage = generateQRCodeImage(payment.getScan_to_pay_code(), 200, 200);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        scantopay.setImage(qrCodeImage);
    }

    private Image generateQRCodeImage(String text, int width, int height) throws WriterException, Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.MARGIN, 1);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new Image(inputStream);
    }

    @FXML
    void initialize() {
        assert dormitory != null : "fx:id=\"dormitory\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert block != null : "fx:id=\"block\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert floor != null : "fx:id=\"floor\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert cell != null : "fx:id=\"cell\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert room != null : "fx:id=\"room\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert cost != null : "fx:id=\"cost\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert iban != null : "fx:id=\"iban\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert swift != null : "fx:id=\"swift\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert variable_symbol != null : "fx:id=\"variable_symbol\" was not injected: check your FXML file 'accommodation.fxml'.";
        assert arrears != null : "fx:id=\"arrears\" was not injected: check your FXML file 'accommodation.fxml'.";
    }
}

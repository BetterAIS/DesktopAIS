package com.bais.dais;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

import java.io.File;

public class documents_Controller {
    @FXML
    WebView webView;

    @FXML
    void initialize() {
        File htmlFile = new File("src/main/resources/com/bais/dais/mock/DocumentServer.html");
        webView.getEngine().load(htmlFile.toURI().toString());
    }
}

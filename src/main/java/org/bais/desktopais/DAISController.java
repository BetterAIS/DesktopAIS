package org.bais.desktopais;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DAISController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
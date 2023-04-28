package com.bais.dais;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bais.dais.baisclient.BAISClient;

import java.util.regex.Pattern;

public class login_Controller {
    private static Logger logger = LoggerFactory.getLogger(login_Controller.class);

    @FXML
    public ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private Button switchThemeButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    private static final Pattern VALID_USERNAME_REGEX = Pattern.compile("^[a-zA-Z0-9_]{3,16}$", Pattern.CASE_INSENSITIVE);

    private void validateUsername(String username) throws Exception {
        if (!VALID_USERNAME_REGEX.matcher(username).find()) {
            throw new Exception("Invalid username");
        }
    }

    private void setLocale(ResourceBundle langBundle) {
        usernameField.setPromptText(langBundle.getString("login.usernamefieldPrompt"));
        passwordField.setPromptText(langBundle.getString("login.passwordfieldPrompt"));
        loginButton.setText(langBundle.getString("login.loginButton"));
    }

    @FXML
    void initialize() {
        logger.debug("Initializing login controller...");

        ThemeLangManager themeLangManager = ThemeLangManager.getInstance();
        themeLangManager.setTheme("nord");
        themeLangManager.setLang("sk");
        setLocale(themeLangManager.getLangBundle());

        switchThemeButton.setOnAction(event -> {
            logger.debug("Switching theme...");
            themeLangManager.switchTheme();
        });

        loginButton.setOnAction(event -> {
            logger.debug("Logging in...");
            BAISClient client = BAISClient.getInstance();
            try {
                validateUsername(usernameField.getText());
                client.login(usernameField.getText(), passwordField.getText());
            } catch (Exception e) {
                logger.error("Error logging in: " + e.getMessage());
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("base.fxml"));

                Scene scene = themeLangManager.getScene();
                scene.setRoot(loader.load());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}


package com.bais.dais;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import com.bais.dais.baisclient.BAISClient;

public class Main extends Application {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        if (BAISClient.getInstance().isLoggedIn()) {
            logger.debug("User is logged in, loading base.fxml...");
            fxmlLoader = new FXMLLoader(Main.class.getResource("base.fxml"));
        } else {
            logger.debug("User is not logged in, loading login.fxml...");
            fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        }

        Scene scene = new Scene(fxmlLoader.load(), 1200, 650);

        ThemeLangManager themeLangManager = ThemeLangManager.getInstance();
        themeLangManager.setScene(scene);
        themeLangManager.setTheme(BAISClient.getInstance().getTheme(themeLangManager.getTheme()));

        stage.setTitle("DAIS");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        logger.debug("Starting DAIS...");
        BAISClient.getInstance();

        launch();
    }
}
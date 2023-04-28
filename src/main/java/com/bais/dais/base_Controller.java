package com.bais.dais;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import com.bais.dais.baisclient.BAISClient;
import com.bais.dais.baisclient.models.*;

public class base_Controller {
    private static Logger logger = LoggerFactory.getLogger(login_Controller.class);

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button accommodation_button;

    @FXML
    private Button documents_button;

    @FXML
    private Button homeworks_button;

    @FXML
    private Button timetable_button;

    @FXML
    private Button mails_button;

    @FXML
    private Button logout_button;

    @FXML
    private Button switchThemeButton;

    @FXML
    private HBox content;

    private void setLocale(ResourceBundle langBundle) {
        accommodation_button.setText(langBundle.getString("base.accommodation_button"));
        documents_button.setText(langBundle.getString("base.documents_button"));
        homeworks_button.setText(langBundle.getString("base.homeworks_button"));
        timetable_button.setText(langBundle.getString("base.timetable_button"));
        mails_button.setText(langBundle.getString("base.mails_button"));
    }

    @FXML
    void initialize() {
        logger.debug("Initializing base controller...");
        BAISClient client = BAISClient.getInstance();
        ThemeLangManager themeLangManager = ThemeLangManager.getInstance();
//
//        client.setLang("sk");
//
        if (themeLangManager.getScene() == null)
            themeLangManager.setScene(accommodation_button.getScene());

        themeLangManager.setTheme(client.getTheme(themeLangManager.getTheme()));
        themeLangManager.setLang(client.getLang(themeLangManager.getLang()));

        setLocale(themeLangManager.getLangBundle());

        accommodation_button.setOnAction(event -> {
            logger.debug("Switching to accommodation scene...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("accommodation.fxml"));
            try {
                Accommodation accommodation = client.getAccommodation();
                content.getChildren().clear();
                content.getChildren().add(loader.load());
                ((accommodation_Controller)loader.getController()).loadAccommodation(accommodation);
            } catch (IOException e) {
                logger.error("Failed to load accommodation scene", e);
            }
        });

        timetable_button.setOnAction(event -> {
            logger.debug("Switching to timetable scene...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timetable.fxml"));
            LinkedList<Timetable> timetableList;

            try {
                timetableList = client.getTimetable();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[][] timetable = new String[5][11];
            for (int i = 0; i < timetableList.size(); i++) {
                Timetable t = timetableList.get(i);
                timetable[t.getDay()][t.getTime()] = t.getLesson() + "\n" + t.getTeacher() + "\n" + t.getRoom();
            }


            try {
                content.getChildren().clear();
                content.getChildren().add(loader.load());
                ((timetable_Controller)loader.getController()).initGrid(timetable);
            } catch (IOException e) {
                logger.error("Failed to load timetable scene", e);
            }
        });

        homeworks_button.setOnAction(event -> {
            logger.debug("Switching to homeworks scene...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeworks.fxml"));
            try {
                content.getChildren().clear();
                content.getChildren().add(loader.load());
                LinkedList<Homework> homeworks = client.getHomework();
                ((homework_Controller)loader.getController()).loadHomework(homeworks);
            } catch (IOException e) {
                logger.error("Failed to load homeworks scene", e);
            }
        });

        documents_button.setOnAction(event -> {
            logger.debug("Switching to documents scene...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("documents.fxml"));
            try {
                content.getChildren().clear();
                content.getChildren().add(loader.load());
            } catch (IOException e) {
                logger.error("Failed to load documents scene", e);
            }
        });

        mails_button.setOnAction(event -> {
            logger.debug("Switching to mails scene...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mails.fxml"));
            try {
                content.getChildren().clear();
                content.getChildren().add(loader.load());
                LinkedList<Mail> mails = client.getMails();
                ((mails_Controller)loader.getController()).loadMails(mails);
            } catch (IOException e) {
                logger.error("Failed to load mails scene", e);
            }
        });

        switchThemeButton.setOnAction(event -> {
            logger.debug("Switching theme...");
            ThemeLangManager themeM = ThemeLangManager.getInstance();
            themeM.switchTheme();
            client.setTheme(themeM.getTheme());
        });

        logout_button.setOnAction(event -> {
            logger.debug("Logging out...");
            client.logout();
            try {
                content.getScene().setRoot(FXMLLoader.load(getClass().getResource("login.fxml")));
            } catch (IOException e) {
                logger.error("Failed to load login scene", e);
            }
        });
    }
}

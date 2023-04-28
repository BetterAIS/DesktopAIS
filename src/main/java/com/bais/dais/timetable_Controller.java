package com.bais.dais;

import com.bais.dais.login_Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class timetable_Controller {
    private static Logger logger = LoggerFactory.getLogger(login_Controller.class);

    @FXML
    GridPane gridPan;

    public void initGrid(String[][] timetable) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 11; j++) {
                gridPan.add(new Label(timetable[i][j]), j, i+1);
            }
        }
    }

    @FXML
    void initialize() {
        logger.debug("Initializing timetable controller...");
    }
}

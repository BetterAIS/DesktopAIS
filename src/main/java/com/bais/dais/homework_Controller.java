package com.bais.dais;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.bais.dais.baisclient.models.*;
import lombok.Setter;

import java.util.LinkedList;

public class homework_Controller {
    public class OpenHomework {
        @Getter
        @Setter
        private String courseTitle;
        @Getter
        @Setter
        private String name;
        @Getter
        @Setter
        private String until;
        @Getter
        @Setter
        private String putUpBy;
    }

    @FXML
    private TableView<OpenHomework> openHomeworkTable;

    @FXML
    private TableColumn<OpenHomework, String> courseTitleColumn;

    @FXML
    private TableColumn<OpenHomework, String> nameColumn;

    @FXML
    private TableColumn<OpenHomework, String> untilColumn;

    @FXML
    private TableColumn<OpenHomework, String> putUpByColumn;

    public void loadHomework(LinkedList<Homework> homework) {
        openHomeworkTable.getItems().clear();
        for (Homework hw : homework) {
            OpenHomework openHomework = new OpenHomework();
            openHomework.setCourseTitle(hw.getTitle());
            openHomework.setName(hw.getDescription());
            openHomework.setUntil(hw.getCreated_at()); // WTF? TODO: Fix this
            openHomework.setPutUpBy("TODO");
            openHomeworkTable.getItems().add(openHomework);
        }
    }

    @FXML
    private void initialize() {
        courseTitleColumn.setCellValueFactory(new PropertyValueFactory<>("courseTitle"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        untilColumn.setCellValueFactory(new PropertyValueFactory<>("until"));
        putUpByColumn.setCellValueFactory(new PropertyValueFactory<>("putUpBy"));
    }
}

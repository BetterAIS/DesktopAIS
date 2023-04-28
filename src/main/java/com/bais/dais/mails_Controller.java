package com.bais.dais;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import com.bais.dais.baisclient.models.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;

public class mails_Controller {
    public class MailTable {
        private String sender;
        private String subject;
        private String received;
        private String size;

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getReceived() {
            return received;
        }

        public void setReceived(String received) {
            this.received = received;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
    @FXML TableView<MailTable> mailsTable;
    @FXML TableColumn<Mail, String> senderColumn;
    @FXML TableColumn<Mail, String> subjectColumn;
    @FXML TableColumn<Mail, String> receivedColumn;
    @FXML TableColumn<Mail, String> sizeColumn;

    public void loadMails(LinkedList<Mail> mails) {
        mailsTable.getItems().clear();
        for (Mail mail : mails) {
            MailTable mailTable = new MailTable();
            mailTable.setSender(mail.getSender());
            mailTable.setSubject(mail.getSubject());
            mailTable.setReceived("");
            mailTable.setSize(mail.getBody().length() + "");
            mailsTable.getItems().add(mailTable);
        }
    }

    @FXML
    private void initialize() {
//        mock data
//        MailTable mail = new MailTable();
//        mail.setSender("sender");
//        mail.setSubject("subject");
//        mail.setReceived("received");
//        mail.setSize("size");
//
//        mailsTable.getItems().add(mail);

//        PropertyValueFactory
        senderColumn.setCellValueFactory(new PropertyValueFactory<>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        receivedColumn.setCellValueFactory(new PropertyValueFactory<>("received"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
    }
}

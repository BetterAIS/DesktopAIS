module com.bais.dais {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires lombok;
    requires java.sql;
    requires com.google.zxing;
    requires java.desktop;
    requires com.google.zxing.javase;
    requires javafx.web;


    opens com.bais.dais to javafx.fxml;
    exports com.bais.dais;
    exports com.bais.dais.baisclient;
    exports com.bais.dais.baisclient.models;
}
module org.bais.desktopais {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bais.desktopais to javafx.fxml;
    exports org.bais.desktopais;
}
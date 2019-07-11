package de.hdm_stuttgart.mi.sd2.Gui;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


public class GuiDriver extends Application {

    private static GuiDriver application;

    private static Stage stage = null;

    private static final Logger log = LogManager.getLogger(GuiDriver.class);

    static GuiDriver getApplication() {
        return application;
    }

    public static void main(String[] args) {
        log.info("Application started");
        launch(args);
        log.info("Application closed");
        System.exit(0);
    }

    public void start(Stage stage) throws Exception {
        application = this;
        GuiDriver.stage = stage;
        setScene("/fxml/Menu.fxml", "Menu", 600, 400);
        stage.setResizable(false);
        stage.show();
    }

    void setScene(String fxml, String title, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        Parent root = loader.load();
        log.debug("FXML file " + fxml + " loaded");
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        log.info("New scene: " + title + " opened.");
    }

}
package de.hdm_stuttgart.mi.sd2.Gui;

import javafx.application.Application;
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

    /**
     * Setting stage of GUI
     * @param stage Stage to set
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {
        application = this;
        GuiDriver.stage = stage;
        setScene("/fxml/Menu.fxml", "Menu", 600, 400);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Setting (new) scenes for GUI
     * @param fxml FXML file
     * @param title Shown title of the scene
     * @param width Width of scene
     * @param height Height of scene
     * @throws IOException
     */
    void setScene(String fxml, String title, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        log.debug("Load FXML file: " + fxml);
        Parent root = loader.load();
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.setTitle(title);
        log.info("Scene created: " + title);
    }

}
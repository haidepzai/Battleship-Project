package de.hdm_stuttgart.mi.sd2;



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

    static final Logger log = LogManager.getLogger(GuiDriver.class);

    public static GuiDriver getApplication() {
        return application;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        application = this;
        GuiDriver.stage = stage;
        setScene("/fxml/Menu.fxml", "Menu", 600, 400);
        stage.setResizable(false);
        stage.show();

        //final String fxmlFile = "/fxml/BattlePhase.fxml";
        //log.debug("Loading FXML for main view from: {}", fxmlFile);
        //final FXMLLoader loader = new FXMLLoader();
        //final Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        //log.debug("Showing JFX scene");
        //final Scene scene = new Scene(rootNode, 1001, 559);

        //scene.getStylesheets().add(GuiDriver.class.getResource("/pictures/pictures.css").toExternalForm());

    }

    void setScene(String fxml, String title, int width, int height) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root, width, height);
        log.debug("FXML-Datei von " + fxml + " geladen");
        stage.setScene(scene);
        stage.setTitle(title);
    }



}
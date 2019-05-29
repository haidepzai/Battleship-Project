package de.hdm_stuttgart.mi.sd2;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Driver class for a simple JavaFX demonstration.
 *
 */
public class GuiDriver extends Application {


    private static final Logger log = LogManager.getLogger(GuiDriver.class);


    public void start(Stage stage) throws Exception {
        log.info("Starting Hello JavaFX and Maven demonstration application");

        final String fxmlFile = "/fxml/hello.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        final FXMLLoader loader = new FXMLLoader();
        final Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        final Scene scene = new Scene(rootNode, 1001, 559);

        scene.getStylesheets().add(GuiDriver.class.getResource("/styles/styles.css").toExternalForm());


        stage.setTitle("Hello JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
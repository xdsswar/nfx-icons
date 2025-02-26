

package xss.it.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author XDSSWAR
 * Created on 02/20/2025
 */
public class Demo extends Application {

    /**
     * The entry point of the Java application.
     * This method calls the launch method to start a JavaFX application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is called after the application has been launched.
     * Override this method to create and set up the primary stage of the application.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Nfx Icons Finder");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/icon.png")).toExternalForm()));
        var pane = load("/view.fxml", new Controller());
        stage.setScene(new Scene(pane));
        stage.show();
    }

    /**
     * The initialization method for the application.
     * This method is called immediately after the application class is loaded and
     * constructed. An application can override this method to perform initialization
     * tasks before the application is shown.
     *
     * @throws Exception if an error occurs during initialization.
     */
    @Override
    public void init() throws Exception {
        super.init();
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     *
     * @throws Exception if an error occurs during stopping the application.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
    }

    /**
     * Loads an FXML resource and binds it to the specified controller.
     *
     * @param <T>         the type of {@link Parent} to be loaded
     * @param location    the location of the FXML resource as a {@link String}
     * @param controller  the controller instance to bind to the resource (can be {@code null})
     * @return the loaded {@link Parent} instance of type {@code T}
     * @throws IOException if an error occurs during loading
     */
    public <T extends Parent> T load(String location, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        if (controller != null) {
            loader.setController(controller);
        }
        return loader.load();
    }
}

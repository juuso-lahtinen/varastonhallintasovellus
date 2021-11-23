package r13.javafx.Varastonhallinta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Locale fiLocale = new Locale("fi", "FI");
    private static Locale enLocale = new Locale("en", "US");
    private static ResourceBundle bundle = ResourceBundle.getBundle("bundles/TextResources", fiLocale);

    @Override
    public void start(Stage stage) throws IOException {	
        scene = new Scene(loadFXML("loginscreen"), 800, 600);
        stage.setTitle("Varastonhallinta");
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setResources(bundle);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}

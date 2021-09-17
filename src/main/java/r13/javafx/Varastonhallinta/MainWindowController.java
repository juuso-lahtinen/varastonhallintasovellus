package r13.javafx.Varastonhallinta;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainWindowController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
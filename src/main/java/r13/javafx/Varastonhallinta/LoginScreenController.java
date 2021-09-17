package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginScreenController {
	
	@FXML
	private Button loginButton;
	@FXML
	private Label loginError;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	
	@FXML
	public void userLogin(ActionEvent event) throws IOException {
		checkLogin();
	}
	
	@FXML
	private void checkLogin() throws IOException {
		
		if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
			App.setRoot("mainwindow");
		} else {
			loginError.setText("Väärä käyttäjätunnus tai salasana.");
		}
	}

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("mainwindow");
    }
}

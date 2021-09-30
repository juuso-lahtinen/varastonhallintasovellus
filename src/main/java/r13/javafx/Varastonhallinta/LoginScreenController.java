package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

public class LoginScreenController {
	
	private UserAccessObject dao = new UserAccessObject();
	
	@FXML
	private Button loginButton;
	@FXML
	private Label loginError;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	@FXML
	private void checkLogin() throws IOException {

		if (dao.checkLogin(username.getText().toString(), password.getText().toString())) {
			System.out.println("Login successful");
			App.setRoot("mainwindow");
		} else {
			loginError.setText("Väärä käyttäjätunnus tai salasana.");
		}
	}
}
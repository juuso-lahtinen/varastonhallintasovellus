package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

public class LoginScreenController implements Initializable {
	
	private UserAccessObject dao = new UserAccessObject();	
	
	public String currentUser;
	private String loginErrorText;
	
	@FXML
	private Button loginButton;
	@FXML
	private Label loginError;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	
	@FXML
	public void checkLogin(ActionEvent event) throws IOException {

		if (dao.checkLogin(username.getText().toString(), password.getText().toString())) {
					
			currentUser = username.getText().toString();
			System.out.println("Login successful, logged in as: " + currentUser);	
			Singleton.getInstance().setUsername(username.getText().toString());
			
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("mainWindowNew.fxml"));
	        Parent mainWindowParent = loader.load();
	        
	        Scene mainWindowScene = new Scene(mainWindowParent);
	        
	        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        window.setScene(mainWindowScene);
	        window.show(); 
	        
		} else {
			loginError.setText(loginErrorText);
		}		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			ResourceBundle bundle = Singleton.getInstance().getBundle();	
			
			loginErrorText = bundle.getString("loginErrorText");				
	}
	
}

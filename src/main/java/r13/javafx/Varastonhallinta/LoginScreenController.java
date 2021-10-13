package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

public class LoginScreenController {
	
	private UserAccessObject dao = new UserAccessObject();	
	
	public String currentUser;
	
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
				
			
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("mainwindow.fxml"));
	        Parent mainWindowParent = loader.load();
	        
	        Scene mainWindowScene = new Scene(mainWindowParent);
	        
	        MainWindowController controller = loader.getController();
	        controller.initUserData(UserAccessObject.getDBUsername(currentUser));
	        
	        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        window.setScene(mainWindowScene);
	        window.show(); 
	        
		} else {
			loginError.setText("Väärä käyttäjätunnus tai salasana.");
		}		
	}

 }
package r13.javafx.Varastonhallinta;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

public class LoginScreenController {
	
	Locale locale;
    ResourceBundle bundle = ResourceBundle.getBundle("bundles/TextResources"); 
    private UserAccessObject dao = new UserAccessObject();	
    public String currentUser;
    @FXML
    private Label loginLabel;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label loginError;

    @FXML
    private ImageView fiLocaleButton;

    @FXML
    private ImageView enLocaleButton;
	
	@FXML
	public void checkLogin(ActionEvent event) throws IOException {

		if (dao.checkLogin(username.getText().toString(), password.getText().toString())) {
					
			currentUser = username.getText().toString();
			System.out.println("Login successful, logged in as: " + currentUser);	
			Singleton.getInstance().setUsername(username.getText().toString());
			
			FXMLLoader loader = new FXMLLoader();
			loader.setResources(bundle);
	        loader.setLocation(getClass().getResource("mainWindow.fxml"));

	        Parent mainWindowParent = loader.load();
	        
	        Scene mainWindowScene = new Scene(mainWindowParent);
	        
	        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        
	        window.setScene(mainWindowScene);
	        window.show(); 
	        
		} else {
			loginError.setText(bundle.getString("loginErrorText"));
		}		
	}
	
	@FXML
	public void changeLocaleToFI() {
		locale = new Locale("fi", "FI");
		bundle = ResourceBundle.getBundle("bundles/TextResources", locale);
		System.out.println("locale changed to FI");
		Singleton.getInstance().setBundle(bundle);
		
		loginLabel.setText(bundle.getString("loginLabel"));
		usernameLabel.setText(bundle.getString("username"));
		passwordLabel.setText(bundle.getString("password"));
		loginButton.setText(bundle.getString("loginButton"));
		username.setPromptText(bundle.getString("username"));
		password.setPromptText(bundle.getString("password"));
	}
	
	@FXML
	public void changeLocaleToEN() {
		locale = new Locale("en", "US");
		bundle = ResourceBundle.getBundle("bundles/TextResources", locale);
		System.out.println("locale changed to EN");
		Singleton.getInstance().setBundle(bundle);
		
		loginLabel.setText(bundle.getString("loginLabel"));
		usernameLabel.setText(bundle.getString("username"));
		passwordLabel.setText(bundle.getString("password"));
		loginButton.setText(bundle.getString("loginButton"));
		username.setPromptText(bundle.getString("username"));
		password.setPromptText(bundle.getString("password"));
	}
	
}

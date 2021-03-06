package r13.javafx.Varastonhallinta;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import r13.javafx.Varastonhallinta.models.Singleton;
import r13.javafx.Varastonhallinta.models.User;
import r13.javafx.Varastonhallinta.models.dao.UserAccessObject;

public class MainWindowController {
	
	public static User userLoggedIn;
	
	@FXML
	private Label usernameField;
	
	@FXML
	private Button logoutButton;

    @FXML
    private Button orderControlBtn;

    @FXML
    private Button productControlBtn;
    

    public void changeSceneToOrderManagementView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("orderManagement.fxml"));
        Scene orderManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(orderManagementViewScene);
        window.show();
    }

    public void changeSceneToProductManagementView(ActionEvent event) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("productManagement.fxml"));
        Scene productManagementViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(productManagementViewScene);
        window.show();
    }

    @FXML
    private void switchToProductManagement() throws IOException {
        App.setRoot("productManagement");
    }
    
    @FXML
    private void logOut(ActionEvent event) throws IOException {
    	Parent mainViewParent = FXMLLoader.load(getClass().getResource("loginscreen.fxml"));
    	Scene loginScreenViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(loginScreenViewScene);
        window.show();
    }
    
    private void initializeUser() {    	
    	Singleton.Instance().setUsername(userLoggedIn.getUsername());    	
    	usernameField.setText(Singleton.Instance().getUsername());
    }
    
    public void initUserData(User user) {
    	userLoggedIn = user;
    	initializeUser();
    }
}
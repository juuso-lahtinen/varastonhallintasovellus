package r13.javafx.Varastonhallinta.models;

import java.util.ResourceBundle;

public class Singleton {
	
	/* Luokan tarkoitus on tallentaa kirjautumisessa käytetty käyttäjätunnus
	 * jotta tätä käyttäjätunnusta voisi hyödyntää muissa Sceneissä
	 * kuten mainWindow:ssa Logged in as -kohdassa
	 * 
	 * Myös lokalisoinnin properties tiedosto (bundle) tallessa täällä
	*/
	private static Singleton instance;
    private String username;
    private ResourceBundle bundle;
   
    private Singleton() {}

    public static Singleton getInstance() {      
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}
}
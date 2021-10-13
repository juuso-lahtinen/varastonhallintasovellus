package r13.javafx.Varastonhallinta.models;

public class Singleton {
	
	private static Singleton instance;
    private String username;
   
    private Singleton() {}

    public static Singleton Instance()
    {      
        if (instance == null)
        {
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
}

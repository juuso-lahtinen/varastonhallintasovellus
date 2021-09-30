package r13.javafx.Varastonhallinta.models;

import javax.persistence.*;

@Entity
@Table(name = "\"User\"")
public class User {
	
	@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private String id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;		

	public User(String id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public User() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

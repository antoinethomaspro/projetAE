package users;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import sql.DB;


@Named
@RequestScoped
public class Login {
	private String name = "test", password;
	
	public String getName() {
		return name;
	}
	  
	public String getPassword() {
		return password;
	}
	
	public void setName(String name) {
		this.name = name;   
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String connect() {
		return "true";
		/*DB db = DB.getInstance();
		if(db.connect(name, password)) return "true";
		else return "false";*/
	}
}

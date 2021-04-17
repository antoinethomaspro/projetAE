package users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import posts.Post;
import sql.DB;

@Named
@RequestScoped
public class User implements Serializable{
	private String name, password, passwordx;
	List<Post> list;
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getPasswordx() {
		return passwordx;
	}
	
	public List<Post> getList() {
		return list;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPasswordx(String passwordx) {
		this.passwordx = passwordx;
	}
	
	public void setList(List<Post> list) {
		this.list = list;
	}
	
	public String signup() {
		if(password.compareTo(passwordx) != 0)
			return "false";
		
		DB db = DB.getInstance();
		if(db.signup(this)) return "true";
		else return "false";
	}
	
	public String connect() {
		System.out.println("name: "+name+" password: "+password);
		FacesContext context = FacesContext.getCurrentInstance();
		DB db = DB.getInstance();
		if(db.connect(name, password)) {			
				System.out.println(db.userExists(name));
				if(db.userExists(name)) {
					list = db.UserProfile(name);
					System.out.println(list);
//					for (Post post : list) {
//						posts += post.getText();
//					    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//					    String formattedDate = LocalDateTime.parse(post.getDate()).format(myFormatObj);
//						posts += "\n\n";
//						posts += formattedDate+"\n";
//					}
				}
				context.getExternalContext().getSessionMap().put("user", this);
				return "true";
			}
		else {
			context.addMessage(null, new FacesMessage("Login or password unknown, try again"));
			return "false";
		}
	}
	
	public String username() {
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		if(user == null) {
			return name;
		}
		else {
			System.out.println(user.getName());
			return user.getName();
		}
	}
	
	public String posts(){
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		StringBuffer str = new StringBuffer();
		for (Post post : user.getList()) {
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    String formattedDate = LocalDateTime.parse(post.getDate()).format(myFormatObj);
			str.append(formattedDate+"\n"+post.getText()+"\n\n");
		}
		return str.toString();
	}
	
	public String following() {
		DB db = DB.getInstance();
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		List<String> follow = db.following(user.getName());
		String str = "";
		for (String string : follow) {
			str+=string+"\n";
		}
		return str;
	}
	
	public String followers() {
		DB db = DB.getInstance();
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		List<String> follow = db.followers(user.getName());
		String str = "";
		for (String string : follow) {
			str+=string+"\n";
		}
		return str;
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "logout";
	}
}
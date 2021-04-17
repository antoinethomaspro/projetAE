package posts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import sql.DB;
import users.User;

@Named
@RequestScoped
public class Post {
	String text;
	String date;
	
	public String getText() {
		return text;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String publish() {
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		System.out.println(user.getName());
		LocalDateTime myObj = LocalDateTime.now();
		date = myObj.toString();
		DB db = DB.getInstance();
		db.publish(this, user.getName());
		
		user.setList(db.UserProfile(user.getName()));
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
		return "publish";
	}
	
	@Override
	public String toString() {
		return date+"\n\n\n"+text;
	}
}

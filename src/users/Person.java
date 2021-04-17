package users;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import posts.Post;
import sql.DB;

@Named
@RequestScoped
public class Person {
	String name;
	List<Post> posts;
	String show;
	String button;
	
	public String getName() {
		return name;
	}
	
	public List<Post> getPosts() {
		return posts;
	}
	
	public String getShow() {
		return show;
	}
	
	public String getButton() {
		return button;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public void setShow(String show) {
		this.show = show;
	}
	
	public void setButton(String button) {
		this.button = button;
	}
	
	public String researchPeople() {
		show = "";
		System.out.println(name);
		DB db = DB.getInstance();
		if(db.userExists(name)) {
			posts = db.UserProfile(name);
			for (Post post : posts) {
				show += post.getText();
			    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
			    String formattedDate = LocalDateTime.parse(post.getDate()).format(myFormatObj);
				show += "\n\n";
				show += formattedDate+"\n";
			}
			User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("person", this);
			System.out.println(db.isFollowed(user.getName(), name));
			if(db.isFollowed(user.getName(), name)) {
				setButton("Ne plus suivre");
			}
			else setButton("Suivre");
			return "research";
		}
		else return "publish";
	}
	
	public String relationShip() {
		DB db = DB.getInstance();
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		Person person = (Person) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("person");
		System.out.println(person.getName());
		if(db.userExists(person.getName())) {
			if(!db.isFollowed(user.getName(), person.getName())) {
				db.addFollow(user.getName(), person.getName());
			}
			else db.removeFollow(user.getName(), person.getName());
		}
		return "home";
	}
	
	public String following() {
		DB db = DB.getInstance();
		Person person = (Person) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("person");
		List<String> follow = db.following(person.getName());
		String str = "";
		for (String string : follow) {
			str+=string+"\n";
		}
		return str;
	}
	
	public String sameRelation() {
		DB db = DB.getInstance();
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
		Person person = (Person) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("person");
		List<String> follow = db.same_follow(user.getName(), person.getName());
		String str = "";
		for (String string : follow) {
			str+=string+"\n";
		}
		return str;
	}
			
}

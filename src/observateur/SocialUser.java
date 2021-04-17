package observateur;

import java.util.ArrayList;

public class SocialUser {
	private ArrayList<SocialUser> followers = new ArrayList<SocialUser>();
	private ArrayList<SocialUser> usersIfollow = new ArrayList<SocialUser>();
	private String txtIpublish;
	private String txtIdisplay;
	private int id;
	
	public void add(SocialUser o) {
		followers.add(o);
	}
	
	public void follow(SocialUser o) {
		usersIfollow.add(o);
	}

	public void removeFollower(SocialUser o) {
		followers.remove(o);
		
	}
	
	public void removeUser(SocialUser o) {
		usersIfollow.remove(o);
	}
	
	public void publish(String txt) {
		this.txtIpublish = txt;
	}
	
	public String getTxt() {
		return txtIpublish;
	}

	public void notifyMyFollowers(int id) {
		for (SocialUser socialUser : followers) {
			socialUser.update(id);
		}
	}

	public void update(int id) {
		txtIdisplay = usersIfollow.get(id).getTxt();
		
	}

	public void display() {
		System.out.println(txtIdisplay);
		
	}

}

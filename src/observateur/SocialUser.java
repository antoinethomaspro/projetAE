package observateur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import sql.DB;
import users.User;

public class SocialUser extends User implements Iobserver{
	Set<SocialPerson> observables;
	
	public SocialUser(User u) {
		DB db = DB.getInstance();
		List<String> followed = db.following(u.getName());
		for (String string : followed) {
			SocialPerson p = new SocialPerson(this);
			p.setName(string);
			p.setPosts(db.getPost(string));
			observables.add(p);
		}
	}
	
	@Override
	public void update(SocialPerson o, int value) {
		observables.add(o);
		display();
	}
	
	public void display() {
		for (SocialPerson p : observables) {
			System.out.println(p.getName()+"a fait "+ p.getValue()+" nouveau(x) post(s).");
		}
	}
}

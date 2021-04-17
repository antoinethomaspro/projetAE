package observateur;

import users.Person;

public class SocialPerson extends Person implements Iobservable{
	//un seul observer
	private Iobserver observer;
	private int value;
	
	public SocialPerson(Iobserver o) {
		observer = o;
	}
	
	@Override
	public void notifyMe() {
		observer.update(this, value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}

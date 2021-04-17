package observateur;
import java.util.ArrayList;

public class Observable implements Iobservable {
	private ArrayList<Iobserver> observers = new ArrayList<Iobserver>();
	private String text;

	@Override
	public void add(Iobserver o) {
		this.observers.add(o);
		
	}

	@Override
	public void remove(Iobserver o) {
		this.observers.remove(o);
		
	}

	@Override
	public void notifyMe() {
		for (Iobserver iobserver : observers) {
			iobserver.update();
		}
		
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return this.text;
	}

}

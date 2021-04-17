package observateur;

public class Observer implements Iobserver, Idisplay {
	
	private Observable observable;
	private String text;
	
	public void setObservable(Observable observable) {
		this.observable = observable;
	}
	@Override
	public void update() {
		text = observable.getText();
		
	}
	@Override
	public void display() {
		System.out.println(text);
		
	}

}

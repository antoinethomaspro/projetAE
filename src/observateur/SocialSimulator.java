package observateur;

public class SocialSimulator {
	
	public static void main(String[] args) {
		Observer observer = new Observer();
		Observable observable = new Observable();
		
		observable.add(observer);
		observer.setObservable(observable);
		observable.setText("coucou je suis le text de l'observable");
		observable.notifyMe();
		observer.display();
		
	}

}

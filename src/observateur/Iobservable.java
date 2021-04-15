package observateur;

public interface Iobservable {
	public void add(Iobserver o);
	public void remove(Iobserver o);
	public void notifyMe();

}

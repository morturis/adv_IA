package input;

public class Tuple {
	State s;
	Action a;
	
	public Tuple(State s, Action a) {
		this.s = s;
		this.a = a;
	}
	
	public State getState() {return s;}
	public Action getAction() {return a;}

}

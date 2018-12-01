package input;

public class Tuple {
	State s;
	Action a;
	
	public Tuple(State s, Action a) {
		this.s = s;
		this.a = a;
	}
	//Used for persistence
	public Tuple(String string) {
		String[] tupleDiv = string.split("t");
		this.s = new State(tupleDiv[0]);
		this.a = new Action(Integer.parseInt(tupleDiv[1]));
	}

	public State getState() {return s;}
	public Action getAction() {return a;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
	
	public String toString() {
		return s.toString() + "t" + a.toString();		
	}

}

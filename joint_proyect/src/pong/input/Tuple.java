package pong.input;

public class Tuple { 
	public final State s; 
	public final Action a; 
	  
	public Tuple(State x, Action y) { 
		this.s = x; 
		this.a = y; 
	}
	 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		result = prime * result + ((a == null) ? 0 : a.hashCode());
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
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		return true;
	} 
	
	
	public String toString(){
		return s.toString() + "/" + a.toString();
	}
	
	public Action getAction() {
		return a;
	}
	
	public State getState() {
		return s;
	}
	
	
} 
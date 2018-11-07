package input;

public class Tuple { 
	public final ActorState x; 
	public final Action y; 
	  
	public Tuple(ActorState x, Action y) { 
		this.x = x; 
		this.y = y; 
	}
	  
	public Tuple(String s) {
		  String[] tupleAttr = s.split("/");
		  x = new ActorState(tupleAttr[0]);
		  y = new Action(tupleAttr[1]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
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
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	} 
	
	
	public String toString(){
		return x.toString() + "/" + y.toString();
	}
	
	
} 
package pong.input;

public class Action {
	public static final int LEFT = -1;
	public static final int STAY = 0;
	public static final int RIGHT = 1;
	
	int action;
	
	public Action(int a) {
		this.action = a;
	}
	
	public Action(String s) {
		action = Integer.parseInt(s);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + action;
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
		Action other = (Action) obj;
		if (action != other.action)
			return false;
		return true;
	}
	
	public String toString() {
		return "" + action;
	}
	
}

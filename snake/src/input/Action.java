package input;

public class Action {
	int action;

	public Action(int x) {
		action = x;
	}
	
	public int getAction() {
		return action;
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
		return ""+action;
	}
}

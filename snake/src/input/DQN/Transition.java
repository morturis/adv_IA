package input.DQN;

import input.Action;
import input.State;

public class Transition {
	public State initialState;
	public Action action;
	public double reward;
	
	public Transition(State initialState, Action action, double reward) {
		super();
		this.initialState = initialState;
		this.action = action;
		this.reward = reward;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((initialState == null) ? 0 : initialState.hashCode());
		long temp;
		temp = Double.doubleToLongBits(reward);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Transition other = (Transition) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (initialState == null) {
			if (other.initialState != null)
				return false;
		} else if (!initialState.equals(other.initialState))
			return false;
		if (Double.doubleToLongBits(reward) != Double.doubleToLongBits(other.reward))
			return false;
		return true;
	}
}
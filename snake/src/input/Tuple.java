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
	public double[] toDoubleArray() {	
		double[] result = new double[7];
		result[0] = s.upCellContent;
		result[1] = s.downCellContent;
		result[2] = s.leftCellContent;
		result[3] = s.rightCellContent;
		/*
		if(s.distanceX == 0) result[4] = 0;
		else result[4] = s.distanceX / Math.abs(s.distanceX);
		if(s.distanceY == 0) result[5] = 0;
		else result[5] = s.distanceY / Math.abs(s.distanceY);
		*/
		result[4] = s.distanceX;
		result[5] = s.distanceY;
		result[6] = a.getAction();
		return result;
	}

}

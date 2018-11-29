package input;

import board.Board;

public abstract class Player{
	private volatile static int nextId;
	final int id;
	final Board board;
	int reward;
	
	public Player(Board board) {
		this.id = nextId;
		nextId++;
		this.board = board;
	}
	
	abstract void chooseAction();	
	abstract void update();
	abstract void saveToFile();
	public void reward(int x) {
		reward = x;
	}
	
	public int getReward() {
		int r = reward;
		reward = 0;
		return r;
	}
	
	public void run() {
		chooseAction();
		reward = 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}
}

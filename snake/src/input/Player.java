package input;

import board.Board;

public abstract class Player implements Runnable {
	private volatile static int nextId;
	final int id;
	final Board board;
	
	public Player(Board board) {
		this.id = nextId;
		nextId++;
		this.board = board;
	}
	
	abstract Action chooseAction();
	abstract public void reward(int reward);
	
	void takeAction(Action a) {
		board.moveSnake(a.getAction());
	}	
	
	public synchronized void run() {
		takeAction(chooseAction());
	}
	
	abstract void saveToFile();

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

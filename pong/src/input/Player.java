package input;

import game.Board;

public class Player{
	final int id;
	final Board board;
	
	public Player(int id, Board board) {
		this.id = id;
		this.board = board;
	}
	
	/*
	 * Moves the player by calling board.movePlayer
	 * dir = -1 = left
	 * dir = 0 = stay
	 * dir = 1 = right
	 */
	void move(int dir) {
		board.movePlayer(id, dir);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
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
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (id != other.id)
			return false;
		return true;
	}	
}
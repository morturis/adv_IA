package pong.input;

import pong.game.Board;

public abstract class Player{
	protected  static final double LEARNING_RATE = 0.1;
	protected  static final double DISCOUNT_FACTOR = 0.9;
	protected final int id;
	protected final Board board;
	protected double reward = 0;
	protected Tuple currentTuple;
	protected int counter = 0;
	protected double EPSILON = 0.9;
	protected int lowIncrementsInARow;
	protected int whenItWasTerminal;
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

	
	protected abstract Action chooseAction();
	protected void takeAction(Action a) {
		board.movePlayer(id, a.action);
	}
	protected abstract void update();
	public abstract void saveToFile();
	public void reward (int r) {
		this.reward = r;
	}
	
	public void pulse() {
		State s = new State(id, board);
		Action a = chooseAction();
		currentTuple = new Tuple(s, a);
		takeAction(a);
		update();
		counter++;
		if(counter%1000 == 0 && EPSILON > 0.1) EPSILON = EPSILON - 0.1;
		System.out.println(counter);
	}
	
	public void noMoreRandomness() {
		EPSILON = 0;
	}
	

}
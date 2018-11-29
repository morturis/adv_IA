package input;

import board.Board;
import input.DQN.Network;

public class DQNPlayer extends Player{
	public static final double LEARNING_RATE = 0.1;
	public static final double DISCOUNT_FACTOR = 0.9;
	
	private Network network;
	
	public DQNPlayer(Board board) {
		super(board);
		network = new Network();
	}

	@Override
	void chooseAction() {
		double result = network.makeDecision(new State(board));
		
		
		//TODO convert double to action
		int temp = (int) Math.round(result);

		Action a = new Action(temp);	
		board.moveSnake(a);
		
		double nextResult = network.makeDecision(new State(board));
		
		//TODO convert results to expectedvalue there is 0% chance this is right
		double expectedValue = result + LEARNING_RATE*(reward + DISCOUNT_FACTOR*nextResult - result);
		network.updateWeights(expectedValue);
		System.out.println(result +" " +nextResult + " " + expectedValue);
		
		
		
	}

	

	@Override
	void saveToFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}


}

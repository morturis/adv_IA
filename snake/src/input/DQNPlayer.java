package input;

import board.Board;
import input.DQN.Network;

public class DQNPlayer extends Player{
	
	private Network network;
	
	public DQNPlayer(Board board) {
		super(board);
		network = new Network();
	}

	@Override
	Action chooseAction() {
		State state = new State(board);
		return network.makeDecision(state);
	}

	@Override
	public void reward(int reward) {
		// TODO Auto-generated method stub
	}

	@Override
	void saveToFile() {
		// TODO Auto-generated method stub	
	}
}

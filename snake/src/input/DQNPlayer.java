package input;

import java.util.concurrent.ThreadLocalRandom;

import board.Board;
import input.DQN.Network;
import input.DQN.Neuron;

public class DQNPlayer extends Player{
	
	int randomMoves = 100000;
	private Network network;
	State s;
	public DQNPlayer(Board board) {
		super(board);
		network = new Network();
	}

	@Override
	Action chooseAction() {
		this.s = new State(board);
		Tuple tupleUp = new Tuple(s, new Action(Board.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(Board.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(Board.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(Board.MOVE_RIGHT));
		double resUp = network.evaluate(tupleUp);
		double resDown = network.evaluate(tupleDown);
		double resLeft = network.evaluate(tupleLeft);
		double resRight = network.evaluate(tupleRight);
		
		double max = 0;
		
		if(randomMoves > 0) {
			System.out.println(randomMoves);
			switch(ThreadLocalRandom.current().nextInt(0, 4)) {
			case 0:
				max = resUp;
				break;
			case 1:
				max = resDown;
				break;
			case 2: 
				max = resLeft;
				break;
			case 3: 
				max = resRight;
				break;
			}
			randomMoves--;
		}else {
			max = Math.max(resUp, resDown);
			max = Math.max(max,  resLeft);
			max = Math.max(max, resRight);
		}
		System.out.println("chosen "+max);
		if(max == resUp) {
			//System.out.println("up"+resUp);
			return tupleUp.getAction();
		}
		if(max == resDown) {
			//System.out.println("down"+resDown);
			return tupleDown.getAction();
		}
		if(max == resLeft) {
			//System.out.println("left"+resLeft);
			return tupleLeft.getAction();
		}
		if(max == resRight) {
			//System.out.println("right"+resRight);
			return tupleRight.getAction();	
		}
		return null;
	}

	void takeAction(Action a) {
		//if(a == null) throw new RuntimeException("null action");
		board.moveSnake(a);	//This sets the reward
		double expectedValue;
		if (reward <0) expectedValue = reward;
		else {
			expectedValue = reward + Neuron.LEARNING_RATE * predict(new State(a, board));
		}
		network.updateWeights(new Tuple(s, a), expectedValue);
		reward = 0;
		
	}
	
	
	double predict(State s) {
		Tuple tupleUp = new Tuple(s, new Action(Board.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(Board.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(Board.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(Board.MOVE_RIGHT));
		double resUp = network.evaluate(tupleUp);
		double resDown = network.evaluate(tupleDown);
		double resLeft = network.evaluate(tupleLeft);
		double resRight = network.evaluate(tupleRight);
		
		double max = Math.max(resUp, resDown);
		max = Math.max(max, resLeft);
		max = Math.max(max, resRight);
		return max;
	}
	
	@Override
	public	void saveToFile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}



}

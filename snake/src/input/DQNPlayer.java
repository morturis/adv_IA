package input;

import java.util.concurrent.ThreadLocalRandom;

import board.Board;
import input.DQN.Network;

public class DQNPlayer extends Player{
	public static final double LEARNING_RATE = 0.1;
	public static final double DISCOUNT_FACTOR = 0.9;
	
	int randomMoves = 100;
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
		System.out.println(resUp + "up");
		System.out.println(resDown + "down");
		System.out.println(resLeft + "left");
		System.out.println(resRight + "right");
		double max = 0;
		System.out.println(randomMoves);
		if(randomMoves > 0) {
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
		
		if(max == resUp) {
			System.out.println("up");
			return tupleUp.getAction();
		}
		if(max == resDown) {
			System.out.println("down");
			return tupleDown.getAction();
		}
		if(max == resLeft) {
			System.out.println("left");
			return tupleLeft.getAction();
		}
		if(max == resRight) {
			System.out.println("right");
			return tupleRight.getAction();	
		}
		return null;
	}

	void takeAction(Action a) {
		//if(a == null) throw new RuntimeException("null action");
		board.moveSnake(a);	//This sets the reward
		network.updateWeights(new Tuple(s, a), reward);
		reward = 0;
		System.out.println("---");
		
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

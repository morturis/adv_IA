package input;

import java.util.HashMap;
import java.util.Map;

import game.Board;

public class AIPlayer extends Player implements Runnable {	
	
	static final double DISCOUNT_FACTOR = 0.9;
	static final double LEARNING_RATE = 0.1;
	static final int NUMBER_OF_GRID = 10;
	static final int NUMBER_OF_ACTIONS = 3;	//Left, stay, right
		
	Map <Tuple, Integer> map;
	int storedReward = 0;

	public AIPlayer(int id, Board board) {
		super(id, board);
		map = new HashMap<Tuple, Integer>();
	}

	@Override
	public synchronized void run() {
		while(true) {
			takeAction();
			try {
				wait(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void reward(int amount) {
		//System.out.println("Player"+id+" receives reward "+amount);
		storedReward += amount;	
	}
	
	/*
	 * write code for policy and select action
	 * update stateArray as described in the algorithm
	 */
	synchronized void takeAction() {
		ActorState currentState = new ActorState(id, board);
		
		int chosenAction = Action.STAY;
		
		Tuple moveLeft = new Tuple(currentState, new Action(Action.LEFT));
		Tuple stay = new Tuple(currentState, new Action(Action.STAY));
		Tuple moveRight = new Tuple(currentState, new Action(Action.RIGHT));
		map.putIfAbsent(moveLeft, 0);
		map.putIfAbsent(stay, 0);
		map.putIfAbsent(moveRight, 0);
		
		if(map.get(moveLeft) > map.get(stay) && map.get(moveLeft) > map.get(moveRight)) {
			chosenAction = Action.LEFT;
		}else if(map.get(moveRight) > map.get(stay) && map.get(moveRight) >= map.get(moveLeft)) {
			//Right move has priority over left move
			chosenAction = Action.RIGHT;
		}
		
		board.movePlayer(id, chosenAction);
		
	}

}
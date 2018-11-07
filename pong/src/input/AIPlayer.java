package input;

import java.util.HashMap;
import java.util.Map;

import game.Board;

public class AIPlayer extends Player implements Runnable {	
	
	static final double DISCOUNT_FACTOR = 0.9;
	static final double LEARNING_RATE = 0.1;
	static final int NUMBER_OF_GRID = Board.BOARD_WIDTH/Board.PLAYER_SPEED;
	static final int NUMBER_OF_ACTIONS = 3;	//Left, stay, right
		
	Map <Tuple, Double> map;
	int storedReward = 0;

	public AIPlayer(int id, Board board) {
		super(id, board);
		map = new HashMap<Tuple, Double>();
	}

	@Override
	public synchronized void run() {
		while(true) {
			takeAction();
			try {
				wait(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
		
		Action chosenAction = applyPolicy(currentState);		
		
		board.movePlayer(id, chosenAction.action);
		
		ActorState nextState = new ActorState(id, board);
		Action nextAction = applyPolicy(nextState);
		Tuple nextTuple = new Tuple (nextState, nextAction);
		
		Tuple currentTuple = new Tuple(currentState, chosenAction);
		Double updatedValue = map.get(currentTuple)+LEARNING_RATE*(storedReward + 
				DISCOUNT_FACTOR*map.get(nextTuple) - map.get(currentTuple));
		map.put(currentTuple, updatedValue);		
		storedReward = 0;
	}
	
	Action applyPolicy (ActorState currentState) {
		Tuple moveLeft = new Tuple(currentState, new Action(Action.LEFT));
		Tuple stay = new Tuple(currentState, new Action(Action.STAY));
		Tuple moveRight = new Tuple(currentState, new Action(Action.RIGHT));
		map.putIfAbsent(moveLeft, 0.0);
		map.putIfAbsent(stay, 0.0);
		map.putIfAbsent(moveRight, 0.0);
		
		/*
		 * this is the policy Greedy
		 */
		if(map.get(moveLeft) > map.get(stay) && map.get(moveLeft) > map.get(moveRight)) {
			return new Action(Action.LEFT);
		}else if(map.get(moveRight) > map.get(stay) && map.get(moveRight) >= map.get(moveLeft)) {
			//Right move has priority over left move
			return new Action(Action.RIGHT);
		}
		return new Action(Action.STAY);
	}

}
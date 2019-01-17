package snake.input.QLearning;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import snake.board.SnakeBoard;
import snake.input.Action;
import snake.input.Player;
import snake.input.State;
import snake.input.Tuple;

public class QPlayer extends Player {
	QFunction q;
	public QPlayer(SnakeBoard board) {
		super(board);
		q = new QFunction();
		
	}

	@Override
	public	Action chooseAction() {
		State s = new State(board);
		Tuple tupleUp = new Tuple(s, new Action(SnakeBoard.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(SnakeBoard.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(SnakeBoard.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(SnakeBoard.MOVE_RIGHT));
		
		double resultUp = q.function(tupleUp);
		double resultDown = q.function(tupleDown);
		double resultLeft = q.function(tupleLeft);
		double resultRight = q.function(tupleRight);		
		
		double max = Math.max(resultUp, resultDown);
		max = Math.max(max, resultLeft);
		max = Math.max(max, resultRight);
				
		//Policy = € greedy
		if(Math.random() > EPSILON) {
			//System.out.println("not exploring");
			if(max == resultUp) return tupleUp.getAction();
			if(max == resultDown) return tupleDown.getAction();
			if(max == resultLeft) return tupleLeft.getAction();
			if(max == resultRight) return tupleRight.getAction();
		}else {
			//System.out.println("exploring");
			int randomInt = ThreadLocalRandom.current().nextInt(0, 4);
			switch(randomInt) {
				case 0:
					return tupleUp.getAction();
				case 1: 
					return tupleDown.getAction();
				case 2:
					return tupleLeft.getAction();
				case 3:
					return tupleRight.getAction();
			}
		}		
		return null;	//This never executes
	}

	@Override
	protected void update() {
		double previousMapTotal = q.getSum();
		
		State statePrime = new State(board);
		//Because QLearning is off policy, is DOES NOT use its policy again
		Action actionPrime = chooseActionWithoutPolicy();		
		Tuple tuplePrime = new Tuple(statePrime, actionPrime);
		
		if(reward == SnakeBoard.REWARD_COLLIDE) tuplePrime = null;
		q.update(currentTuple, tuplePrime, reward, LEARNING_RATE, DISCOUNT_FACTOR);
		reward = 0;	
		
		double newMapTotal = q.getSum();
		if(Math.abs(previousMapTotal-newMapTotal) < 1) {
			lowIncrementsInARow++;
			if(lowIncrementsInARow == 100) whenItWasTerminal = counter;
			if(lowIncrementsInARow >= 100) {
				System.out.println("Possibly terminal "+ whenItWasTerminal);
			}
		}else {
			lowIncrementsInARow = 0;
		}

	}

	@Override
	public void saveToFile() {
		
	}

	Action chooseActionWithoutPolicy() {
		State s = new State(board);
		Tuple tupleUp = new Tuple(s, new Action(SnakeBoard.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(SnakeBoard.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(SnakeBoard.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(SnakeBoard.MOVE_RIGHT));
		
		double resultUp = q.function(tupleUp);
		double resultDown = q.function(tupleDown);
		double resultLeft = q.function(tupleLeft);
		double resultRight = q.function(tupleRight);		
		
		double max = Math.max(resultUp, resultDown);
		max = Math.max(max, resultLeft);
		max = Math.max(max, resultRight);
		
		if(max == resultUp) return tupleUp.getAction();
		if(max == resultDown) return tupleDown.getAction();
		if(max == resultLeft) return tupleLeft.getAction();
		if(max == resultRight) return tupleRight.getAction();
		//Should never execute
		return null;
	}
}

package pong.input.SARSA;

import java.util.concurrent.ThreadLocalRandom;

import pong.game.Board;
import pong.input.Action;
import pong.input.Player;
import pong.input.State;
import pong.input.Tuple;

public class SARSAPlayer extends Player {
	SARSAQFunction q;

	public SARSAPlayer(int id, Board board) {
		super(id, board);
		q = new SARSAQFunction();
	}

	@Override
	protected
	Action chooseAction() {
		State s = new State(id, board);
		Tuple tupleRight = new Tuple(s, new Action(Action.RIGHT));
		Tuple tupleStay = new Tuple(s, new Action(Action.STAY));
		Tuple tupleLeft = new Tuple(s, new Action(Action.LEFT));
		
		double resRight = q.function(tupleRight);
		double resStay = q.function(tupleStay);
		double resLeft = q.function(tupleLeft);
		
		double max = Math.max(resRight, resStay);
		max = Math.max(max, resLeft);
		
		//GreedyPolicy
		if(Math.random() > EPSILON) {
			if(max == resRight) return tupleRight.getAction();
			if(max == resStay) return tupleStay.getAction();
			if(max == resLeft) return tupleLeft.getAction();
		}else {

			int randomInt = ThreadLocalRandom.current().nextInt(0, 3);
			switch(randomInt) {
				case 0:
					return tupleRight.getAction();
				case 1: 
					return tupleStay.getAction();
				case 2:
					return tupleLeft.getAction();
			}
		}
		//NEver executes
		return null;
	}

	@Override
	protected
	void update() {
		double previousMapTotal = q.getSum();
		
		State statePrime = new State(id, board);
		//Because sarsa is on-policy, it applies its policy once more in order to update 
		Action actionPrime = chooseAction();
		
		Tuple tuplePrime = new Tuple(statePrime, actionPrime);
		if(reward == Board.REWARD_MISS) tuplePrime = null;
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
		// TODO Auto-generated method stub

	}

}

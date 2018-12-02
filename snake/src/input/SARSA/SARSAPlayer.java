package input.SARSA;


import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import Persistence.Persistence;
import board.Board;
import input.Action;
import input.Player;
import input.State;
import input.Tuple;

public class SARSAPlayer extends Player{
	SARSAQFunction q;
	
	public SARSAPlayer(Board board) {
		super(board);
		q = new SARSAQFunction();
		
		try {
			p = new Persistence("C:\\Users\\pikac\\Desktop\\bot_savefiles\\snake_sarsa.txt");
			//p.loadFromFile(q);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public	Action chooseAction() {
		State s = new State(board);
		Tuple tupleUp = new Tuple(s, new Action(Board.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(Board.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(Board.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(Board.MOVE_RIGHT));
		
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
		State statePrime = new State(board);
		//Because sarsa is on-policy, it applies its policy once more in order to update 
		Action actionPrime = chooseAction();
		
		Tuple tuplePrime = new Tuple(statePrime, actionPrime);
		if(reward == Board.REWARD_COLLIDE) tuplePrime = null;
		q.update(currentTuple, tuplePrime, reward, LEARNING_RATE, DISCOUNT_FACTOR);
		reward = 0;		
	}

	@Override
	public void saveToFile() {
		try {
			p.saveToFile(q);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
}
	
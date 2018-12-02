package input.DQN;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;


import Persistence.Persistence;
import board.Board;
import input.Action;
import input.Player;
import input.State;
import input.Tuple;

public class DQNPlayer extends Player{
	public static final int MEMORY_SIZE = 10000;
	public static final int SAMPLE_SIZE = 2500;
	public static final double LEARNING_RATE = 0.1;
	public static final double DISCOUNT_FACTOR = 0.9;
	public static final double MOMENTUM = .5;
	Network n;
	ReplayMemory r;
	
	public DQNPlayer(Board board) {
		super(board);
		r = new ReplayMemory(MEMORY_SIZE);
		n = new Network(State.SIZE+1, (State.SIZE+1)*(State.SIZE+1), 1, 1);
		try {
			p = new Persistence("C:\\Users\\pikac\\Desktop\\bot_savefiles\\snake_dqn.txt");
			//p.loadFromFile(q);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected
	Action chooseAction() {
		State s = new State(board);
		Tuple tupleUp = new Tuple(s, new Action(Board.MOVE_UP));
		Tuple tupleDown = new Tuple(s, new Action(Board.MOVE_DOWN));
		Tuple tupleLeft = new Tuple(s, new Action(Board.MOVE_LEFT));
		Tuple tupleRight = new Tuple(s, new Action(Board.MOVE_RIGHT));
		
		double resultUp = n.function(tupleUp);
		double resultDown = n.function(tupleDown);
		double resultLeft = n.function(tupleLeft);
		double resultRight = n.function(tupleRight);
		
		double max = Math.max(resultUp, resultDown);
		max = Math.max(max, resultLeft);
		max = Math.max(max, resultRight);
		
		//Policy = € greedy
		if(Math.random() > EPSILON) {
			//System.out.println("not exploring");
			if(max == resultUp) {
				//System.out.println("chose up "+ max);
				return tupleUp.getAction();
			}
			if(max == resultDown) {
				//System.out.println("chose down "+ max);
				return tupleDown.getAction();
			}
			if(max == resultLeft) {
				//System.out.println("chose left "+ max);
				return tupleLeft.getAction();
			}
			if(max == resultRight) {
				//System.out.println("chose right "+ max);
				return tupleRight.getAction();
			}
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
		return null;
	}


	@Override
	protected
	void update() {
		//Implements experience replay
		Transition t;
		if(reward == Board.REWARD_COLLIDE) {
			t = new Transition(currentTuple.getState(), currentTuple.getAction(), reward);
		}else {
			reward += LEARNING_RATE * n.function(new Tuple(new State(board), chooseAction()));
			t = new Transition(currentTuple.getState(), currentTuple.getAction(), reward);
		}
		reward = 0;
		r.add(t);
		if(counter%SAMPLE_SIZE == SAMPLE_SIZE-1) n.learn(r);
		/*
		for(Transition aux: r.sample(SAMPLE_SIZE)) {
			double [] r = {aux.reward};
			n.learn(new Tuple(aux.initialState, aux.action), r);
		}
		*/
	}

	@Override
	public void saveToFile() {
		//p.saveToFile(n);
	}

	

	
}

package input.SARSA;

import java.util.HashMap;

import game.Board;
import input.Tuple;

public class SARSAQFunction {
	HashMap<Tuple, Double> map;
	
	public SARSAQFunction() {
		map = new HashMap<Tuple, Double>();
		map.putIfAbsent(null, (double) Board.REWARD_MISS);
	}

	public double function(Tuple t) {
		map.putIfAbsent(t, 0.0);
		return map.get(t);
	}

	public void update(Tuple currentTuple, Tuple tuplePrime, double reward, double learningRate, double discountFactor) {
		double delta = learningRate * (reward + discountFactor*map.get(tuplePrime) - map.get(currentTuple));
		Double newQValue = map.get(currentTuple) + delta;
		map.put(currentTuple, newQValue);
		
	}

	public HashMap<Tuple, Double> getMap() {
		return map;
	}

	public void setMap(HashMap<Tuple, Double> map) {
		this.map = map;
		this.map.putIfAbsent(null, -100.0);
	}

}

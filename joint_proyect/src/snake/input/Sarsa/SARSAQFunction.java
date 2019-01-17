package snake.input.Sarsa;

import java.util.HashMap;
import java.util.HashSet;

import snake.board.SnakeBoard;
import snake.input.Tuple;

public class SARSAQFunction {
	HashMap<Tuple, Double> map;
	HashMap<Tuple, Double> eligibilityTracesMap;
	HashSet<Tuple> movesUntilNow;
	double lambda = 0.9;
	
	public SARSAQFunction() {
		map = new HashMap<>();
		eligibilityTracesMap = new HashMap<>();
		movesUntilNow = new HashSet<>();
		map.putIfAbsent(null, (double) SnakeBoard.REWARD_COLLIDE);
		eligibilityTracesMap.putIfAbsent(null, 0.0);
	}

	public double function(Tuple t) {
		map.putIfAbsent(t, 0.0);
		eligibilityTracesMap.putIfAbsent(t, 0.0);
		return map.get(t);
	}

	public void update(Tuple currentTuple, Tuple tuplePrime, double reward, double learningRate, double discountFactor) {
		//e(s, a) = e(s, a) +1
		eligibilityTracesMap.put(currentTuple, eligibilityTracesMap.get(currentTuple)+1);
		double delta = reward + discountFactor * map.get(tuplePrime) - map.get(currentTuple);
		//update all values of Q and eMap
		movesUntilNow.add(currentTuple);
		for(Tuple e: movesUntilNow) {
			//eligibilityTracesMap.putIfAbsent(e.getKey(), 0.0);
			map.put(e, map.get(e) + learningRate * delta * eligibilityTracesMap.get(e));
			eligibilityTracesMap.put(e, discountFactor * lambda * eligibilityTracesMap.get(e));
		}
		if(reward == SnakeBoard.REWARD_COLLIDE) movesUntilNow.clear();
	}

	public HashMap<Tuple, Double> getMap() {
		return map;
	}

	public void setMap(HashMap<Tuple, Double> map) {
		this.map = map;
		this.map.putIfAbsent(null, -100.0);
	}
	
	public double getSum() {
		double result = 0;
		for(Double d: map.values()) {
			result += d;
		}
		return result;
	}
	

}

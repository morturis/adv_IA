package pong.input.SARSA;

import java.util.HashMap;

import pong.game.Board;
import pong.input.Tuple;

public class SARSAQFunction {
	HashMap<Tuple, Double> map;
	HashMap<Tuple, Double> eligibilityTracesMap;
	double lambda = 0.9;
	
	public SARSAQFunction() {
		map = new HashMap<>();
		eligibilityTracesMap = new HashMap<>();
		map.putIfAbsent(null, (double) Board.REWARD_MISS);
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
		
		for(Tuple t: map.keySet()) {
			//eligibilityTracesMap.putIfAbsent(e.getKey(), 0.0);
			map.put(t, map.get(t) + learningRate * delta * eligibilityTracesMap.get(t));
			eligibilityTracesMap.put(t, discountFactor * lambda * eligibilityTracesMap.get(t));
		}
				
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

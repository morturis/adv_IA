package input.SARSA;

import java.util.HashMap;

import input.Tuple;

public class QFunction {
	HashMap<Tuple, Double> map;
	
	public QFunction() {
		map = new HashMap<Tuple, Double>();
		map.putIfAbsent(null, -100.0);
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

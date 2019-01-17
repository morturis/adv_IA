package snake.input.QLearning;

import java.util.HashMap;

import snake.board.SnakeBoard;
import snake.input.Tuple;


public class QFunction {
	HashMap<Tuple, Double> map;
	
	public QFunction() {
		map = new HashMap<Tuple, Double>();
		map.putIfAbsent(null, (double) SnakeBoard.REWARD_COLLIDE);
	}

	public double function(Tuple t) {
		map.putIfAbsent(t, 0.0);
		return map.get(t);
	}
	
	public void update(Tuple currentTuple, Tuple tuplePrime, double reward, double learningRate, double discountFactor) {
		
		double newValue = (1-learningRate) * function(currentTuple) + learningRate * (reward + discountFactor * function(tuplePrime));
		map.put(currentTuple, newValue);
		
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

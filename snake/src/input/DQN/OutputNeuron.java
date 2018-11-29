package input.DQN;

import java.util.ArrayList;


public class OutputNeuron extends Neuron {

	public ArrayList<Synapse> inputList;
	
	OutputNeuron(){
		super();
		inputList = new ArrayList<Synapse>();
	}

	@Override
	void calcOutput() {
		double sum = 0;
		for(Synapse s: inputList) {
			sum += s.start.getResult() * s.weight;
		}
		result = sum;
		System.out.println("o "+result);
	}

	@Override
	void updateWeights() {
		for(Synapse s : inputList) {
			double deltaOldWeight = s.deltaWeight;
			double deltaNewWeight = LEARNING_RATE*s.start.getResult() * gradient+DISCOUNT_FACTOR * deltaOldWeight;
			s.deltaWeight = deltaNewWeight;
			s.weight += deltaNewWeight;
		}		
	}

	@Override
	protected void calcGradient(double expectedValue) {
		gradient = (expectedValue - result)*outputDerivative();		
	}

}

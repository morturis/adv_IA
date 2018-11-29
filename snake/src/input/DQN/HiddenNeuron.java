package input.DQN;

import java.util.ArrayList;


class HiddenNeuron extends Neuron{
	public ArrayList<Synapse> inputList, outputList;
	
	
	HiddenNeuron(){
		super();
		inputList = new ArrayList<Synapse>();
		outputList = new ArrayList<Synapse>();
	}

	ArrayList<Synapse> getInputList() {
		return inputList;
	}

	ArrayList<Synapse> getOutputList() {
		return outputList;
	}
	
	
	@Override
	void calcOutput() {
		double sum = 0;
		for(Synapse s: inputList) {
			sum += s.start.getResult() * s.weight;
		}
		//TODO tanh doesnt work, always outputs 1
		//result = Math.tanh(sum);
		result = sum;
		System.out.println("h "+result);
	}
	
	void calcGradient() {
		double sum = 0;
		for(Synapse s: outputList) {
			sum += s.weight * s.end.gradient ;
		}
		gradient = sum*outputDerivative();
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
		double sum = 0;
		for(Synapse s: outputList) {
			sum+= s.weight*s.end.gradient;
		}
		gradient = sum*outputDerivative();
		
	}
	
	
}

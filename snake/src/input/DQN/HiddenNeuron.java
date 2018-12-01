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
		//Sigmoid
		result = 1/(1+Math.pow(Math.E, -sum));
	}
	
	void calcGradient() {
		double sum = 0;
		for(Synapse s: outputList) {
			sum += s.weight * s.end.gradient ;
		}
		gradient = sum*outputDerivative();
	}

	void updateWeights() {
		for(Synapse s : inputList) {
			s.weight = s.weight + LEARNING_RATE*gradient*s.start.getResult();
		}		
	}

	@Override
	//Expected value is not used but needs to be overriden
	protected void calcGradient(double expectedValue) {
		double sum = 0;
		for(Synapse s: outputList) {
			sum+= s.weight*s.end.gradient;
		}
		gradient = sum*outputDerivative();
		
	}
	
	
}

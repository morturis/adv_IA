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
		result = Math.tanh(sum);
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
		// TODO Auto-generated method stub
		
	}
	
	
}

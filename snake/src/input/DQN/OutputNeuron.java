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
		//Sigmoid
		result = 1/(1+Math.pow(Math.E, -sum));
	}

	void updateWeights() {
		for(Synapse s : inputList) {
			//wegith = weight + Learningrate*error*input
			s.weight = s.weight + LEARNING_RATE*gradient*s.start.getResult();
		}		
	}

	@Override
	protected void calcGradient(double expectedValue) {
		gradient = (expectedValue - result)*outputDerivative();
		//System.out.println(outputDerivative()+ "x");
	}

}

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

	}

	@Override
	void updateWeights() {
		// TODO Auto-generated method stub
		
	}

}

package input.DQN;

import java.util.ArrayList;

import board.Cell;

public class InputNeuron extends Neuron{
	int input;
	public ArrayList<Synapse> outputList;
	
	InputNeuron(){
		super();
		outputList = new ArrayList<Synapse>();
	}
	
	public void setInput(int input) {
		this.input = input;
	}
	
	@Override
	void calcOutput() {
		result = (double) input;
	}

	@Override
	//This method is never called
	void updateWeights() {
		throw new RuntimeException("Input neuron called updateweights");
		
	}

	@Override
	//This method is never called 
	protected void calcGradient(double expectedValue) {
		throw new RuntimeException("Input neuron called calcGradient");
		
	}
	
	
}

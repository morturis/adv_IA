package input.DQN;

import java.util.ArrayList;

import board.Cell;

public class InputNeuron extends Neuron{
	Cell input;
	public ArrayList<Synapse> outputList;
	
	InputNeuron(){
		super();
		outputList = new ArrayList<Synapse>();
	}
	
	public void setInput(Cell input) {
		this.input = input;
	}
	
	@Override
	void calcOutput() {
		result = (double)(input.getState());
	}

	@Override
	void updateWeights() {
		// TODO Auto-generated method stub
		
	}
	
	
}

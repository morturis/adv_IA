package input.DQN;

import java.util.ArrayList;

import board.Cell;

public class InputNeuron extends Neuron{
	Cell input;
	public ArrayList<Synapse> outputList;
	
	InputNeuron(Cell c){
		super();
		input = c;
		outputList = new ArrayList<Synapse>();
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

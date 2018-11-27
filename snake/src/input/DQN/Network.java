package input.DQN;

import java.util.ArrayList;

import board.Board;
import input.Action;
import input.State;

public class Network{
	public static final double LEARNING_RATE = 0.1;
	public static final double DISCOUNT_FACTOR = 0.9;
	private static int SIZE_OF_INPUT = (State.LOOK_AHEAD*2+1)^2+1;
	private static int SIZE_OF_HIDDEN = SIZE_OF_INPUT/2;
	
	public ArrayList<Neuron[]> network;
	
	public Network() {
		network = new ArrayList<Neuron[]>(3);	//3 = number of layers: input = 1, hidden = 1; output = 1
		
		//Init input neurons
		Neuron[] temp = new Neuron[SIZE_OF_INPUT];
		for(int i = 0; i<temp.length;i++) {
			temp[i] = new InputNeuron();
		}
		network.add(temp);
		
		//Init hidden neurons
		temp = new Neuron[SIZE_OF_HIDDEN];
		for(int i = 0; i<temp.length;i++) {
			temp[i] = new HiddenNeuron();
		}
		network.add(temp);
		
		//init outputneurons
		temp = new Neuron[1];
		temp[0] = new OutputNeuron();
		network.add(temp);
		
		//Creating synapses for inputneurons and inputsynapses for hidden neurons
		for(int i = 0; i<network.get(0).length;i++) {
			InputNeuron a = (InputNeuron)network.get(0)[i];
			for(int j = 0; j<network.get(1).length;j++) {
				HiddenNeuron b = (HiddenNeuron)network.get(1)[j];
				Synapse s = new Synapse(a, b, Math.random());
				a.outputList.add(s);
				b.inputList.add(s);
			}
		}
		
		//Creating synapses from hidden layer to outputlayer
		OutputNeuron c = (OutputNeuron)network.get(2)[0];
		for(int i = 0; i<network.get(1).length; i++) {
			HiddenNeuron b = (HiddenNeuron)network.get(1)[i];
			Synapse s = new Synapse(b, c, Math.random());
			b.outputList.add(s);
			c.inputList.add(s);
		}
	}
	
	public Action makeDecision(State state) {
		
		// Link input neurons with their cells and have them work
		for(int i=0; i<SIZE_OF_INPUT; i++) {
			InputNeuron temp = (InputNeuron) network.get(0)[i];
			temp.setInput(state.stateList.get(i));
			temp.calcOutput();
		}
		
		// Have hidden neurons work
		for(int i=0; i<SIZE_OF_HIDDEN; i++) {
			network.get(1)[i].calcOutput();	
		}
		
		network.get(2)[0].calcOutput();
		
		// TODO
		// Might have to mess with this again
		return new Action(Math.round((float) network.get(2)[0].getResult()));
	}
}
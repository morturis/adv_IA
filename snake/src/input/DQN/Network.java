package input.DQN;

import java.util.ArrayList;

import board.Board;
import input.Action;
import input.Player;
import input.State;
import input.Tuple;

public class Network{
	private static int SIZE_OF_INPUT = State.SIZE+2;
	private static int SIZE_OF_HIDDEN = SIZE_OF_INPUT*2;
	
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
	
	public void updateWeights(Tuple t, double expectedValue) {
		//This is used to set the proper inputs
		evaluate(t);
		
		//Calc gradient for outputneuron
		network.get(2)[0].calcGradient(expectedValue);
		
		//Gradient for hidden layer
		for(int i = 0; i<network.get(1).length;i++) {
			//Expected value is not used but needs to be overriden
			network.get(1)[i].calcGradient(expectedValue);
		}
		
		//Updating all weights
		network.get(2)[0].updateWeights();
		for(int i = 0; i<network.get(1).length;i++) {
			network.get(1)[i].updateWeights();
		}
	}
	
	private void work() {
		// Link input neurons with their cells and have them work
		for(int i=0; i<SIZE_OF_INPUT; i++) {
			InputNeuron temp = (InputNeuron) network.get(0)[i];
			temp.calcOutput();
		}
		
		// Have hidden neurons work
		for(int i=0; i<SIZE_OF_HIDDEN; i++) {
			HiddenNeuron temp = (HiddenNeuron)network.get(1)[i];
			temp.calcOutput();
		}
		
		OutputNeuron temp = (OutputNeuron)network.get(2)[0];
		temp.calcOutput();	
		
	}
	
	public double evaluate(Tuple t) {
		//Setting input 
		State s = t.getState();
		((InputNeuron) network.get(0)[0]).setInput(s.upCellContent);
		((InputNeuron) network.get(0)[1]).setInput(s.downCellContent);
		((InputNeuron) network.get(0)[2]).setInput(s.leftCellContent);
		((InputNeuron) network.get(0)[3]).setInput(s.rightCellContent);
		((InputNeuron) network.get(0)[4]).setInput(s.distanceX);		
		((InputNeuron) network.get(0)[5]).setInput(s.distanceY);		
		

		if(t.getAction().getAction() == Board.MOVE_UP) {
			((InputNeuron) network.get(0)[State.SIZE]).setInput(0);
			((InputNeuron) network.get(0)[State.SIZE+1]).setInput(-1);
		}
		if(t.getAction().getAction() == Board.MOVE_DOWN) {
			((InputNeuron) network.get(0)[State.SIZE]).setInput(0);
			((InputNeuron) network.get(0)[State.SIZE+1]).setInput(+1);
		}
		if(t.getAction().getAction() == Board.MOVE_LEFT) {
			((InputNeuron) network.get(0)[State.SIZE]).setInput(-1);
			((InputNeuron) network.get(0)[State.SIZE+1]).setInput(0);
		}
		if(t.getAction().getAction() == Board.MOVE_RIGHT) {
			((InputNeuron) network.get(0)[State.SIZE]).setInput(+1);
			((InputNeuron) network.get(0)[State.SIZE+1]).setInput(0);
		}
		
		work();
		return network.get(2)[0].getResult();
	}
	
	
	
}
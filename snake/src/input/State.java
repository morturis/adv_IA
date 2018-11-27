package input;

import java.util.ArrayList;

import board.Board;
import board.Cell;

public class State {
	
	public static final int LOOK_AHEAD = 1; 
	public ArrayList<Cell> stateList;	// firsts --> adjacent to the head; last --> food cell
	
	State(Board b){
		int[] snakeHeadCoord = b.getSnake().getHead().getCellCoord();
		
		stateList = new ArrayList<Cell>((LOOK_AHEAD*2+1)^2+1);
		
		for(int i=-LOOK_AHEAD; i <= LOOK_AHEAD; i++) {
			for(int j=-LOOK_AHEAD; j <= LOOK_AHEAD; j++) {
				stateList.add(b.getCell(snakeHeadCoord[0]+i, snakeHeadCoord[1]+j));
			}
		}
		
		stateList.add(b.getFoodCell());
	}
}
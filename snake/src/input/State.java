package input;

import board.Board;
import board.Cell;

public class State {
	
	public Cell snakeHead;
	public Cell[][] ArrayCells;
	public Cell food;
	
	State(Board b){
		snakeHead = b.getSnake().getHead();
		ArrayCells = b.getArrayCells();
		food = b.getFood();
	}
}

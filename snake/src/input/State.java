package input;

import java.util.ArrayList;

import board.Board;
import board.Cell;

public class State {
	public static final int SIZE = 5;
	
	public int upCellContent;
	public int downCellContent;
	public int leftCellContent;
	public int rightCellContent;
	public int distance;
	
	State(Board b){
		int x = b.getSnake().getHead().getCellCoord()[0];
		int y = b.getSnake().getHead().getCellCoord()[1];
		//Up
		if(y-1 <0) {
			upCellContent = Cell.BORDER;
		}else {
			upCellContent = b.getCell(x, y-1).getContent();
		}
		//Left
		if(x-1 <0) {
			leftCellContent = Cell.BORDER;
		}else {
			leftCellContent = b.getCell(x-1, y).getContent();
		}
		//Down
		if(y+1 >Board.BOARD_HEIGHT-1) {
			upCellContent = Cell.BORDER;
		}else {
			upCellContent = b.getCell(x, y+1).getContent();
		}
		//Right
		if(x+1 >Board.BOARD_WIDTH-1) {
			upCellContent = Cell.BORDER;
		}else {
			upCellContent = b.getCell(x+1, y).getContent();
		}
		distance = Math.abs(b.getFoodCell().getCellCoord()[0] - x);
		distance += Math.abs(b.getFoodCell().getCellCoord()[1] - y);
		
	}
}
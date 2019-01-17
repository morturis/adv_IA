package snake.board;

import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {
	private LinkedList<Cell> snakeCells;
	private SnakeBoard board;
	
	public Snake (SnakeBoard board) {
		snakeCells = new LinkedList<Cell>();
		this.board = board;
		
	}
	
	public int[] getPos() {
		return snakeCells.getFirst().getCellCoord();
	}
	
	
	public void resetSnake() {
		snakeCells.clear();
		int[] snakePos = new int[2];
		snakePos[0] = (int) SnakeBoard.BOARD_WIDTH/2;
		snakePos[1] = (int) SnakeBoard.BOARD_HEIGHT/2;
		Cell[][] boardCells = board.getArrayCells();
		snakeCells.addFirst(boardCells[snakePos[0]-2][snakePos[1]]);
		snakeCells.addFirst(boardCells[snakePos[0]-1][snakePos[1]]);
		snakeCells.addFirst(boardCells[snakePos[0]][snakePos[1]]);
		boardCells[snakePos[0]][snakePos[1]].setState(Cell.SNAKE_HEAD);
		boardCells[snakePos[0]-1][snakePos[1]].setState(Cell.SNAKE);
		boardCells[snakePos[0]-2][snakePos[1]].setState(Cell.SNAKE);
	}
		

	public void advance(boolean hasEaten, Cell nextCell, ArrayList<Cell> emptyCellList) {
		if(!hasEaten) {
			Cell formerTail = snakeCells.removeLast();
			emptyCellList.add(formerTail);
			formerTail.setState(Cell.VOID);
		}
		
		emptyCellList.remove(nextCell);
		snakeCells.getFirst().setState(Cell.SNAKE);
		snakeCells.addFirst(nextCell);	
		nextCell.setState(Cell.SNAKE_HEAD);
		
	}
	
	public Cell getHead() {
		return snakeCells.getFirst();
	}
	
}

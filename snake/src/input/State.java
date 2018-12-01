package input;


import board.Board;
import board.Cell;

public class State {
	public static final int SIZE = 6;
	static final int SAFE = 1;
	static final int UNSAFE = 0;

	public int upCellContent = SAFE;
	public int downCellContent = SAFE;
	public int leftCellContent = SAFE;
	public int rightCellContent = SAFE;
	public int distanceX;
	public int distanceY;
	
	State(Board b){
		int x = b.getSnake().getHead().getCellCoord()[0];
		int y = b.getSnake().getHead().getCellCoord()[1];
		//Up
		if(y-1 <0) {
			upCellContent = UNSAFE;
		}else {
			if(b.getCell(x, y-1).getContent() == Cell.SNAKE) upCellContent = UNSAFE;
		}
		//Left
		if(x-1 <0) {
			leftCellContent = UNSAFE;
		}else {
			if(b.getCell(x-1, y).getContent() == Cell.SNAKE) leftCellContent = UNSAFE;
		}
		//Down
		if(y+1 >Board.BOARD_HEIGHT-1) {
			downCellContent = UNSAFE;
		}else {
			if(b.getCell(x, y+1).getContent() == Cell.SNAKE) downCellContent = UNSAFE;
		}
		//Right
		if(x+1 >Board.BOARD_WIDTH-1) {
			rightCellContent = UNSAFE;
		}else {
			if(b.getCell(x+1, y).getContent() == Cell.SNAKE) rightCellContent = UNSAFE;
		}
		distanceX = b.getFoodCell().getCellCoord()[0] - x;
		distanceY = b.getFoodCell().getCellCoord()[1] - y;
		
	}
	
	//Creates a simulated state of the board AFTER taking action A
	State (Action a, Board b){
		//TODO
	}
	
	
	//Used for persistence
	public State(String string) {
		String[] stateDiv = string.split(" ");
		upCellContent = Integer.parseInt(stateDiv[0]);
		downCellContent = Integer.parseInt(stateDiv[1]);
		leftCellContent = Integer.parseInt(stateDiv[2]);
		rightCellContent = Integer.parseInt(stateDiv[3]);
		distanceX = Integer.parseInt(stateDiv[4]);
		distanceY = Integer.parseInt(stateDiv[5]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + distanceX;
		result = prime * result + distanceY;
		result = prime * result + downCellContent;
		result = prime * result + leftCellContent;
		result = prime * result + rightCellContent;
		result = prime * result + upCellContent;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (distanceX != other.distanceX)
			return false;
		if (distanceY != other.distanceY)
			return false;
		if (downCellContent != other.downCellContent)
			return false;
		if (leftCellContent != other.leftCellContent)
			return false;
		if (rightCellContent != other.rightCellContent)
			return false;
		if (upCellContent != other.upCellContent)
			return false;
		return true;
	}
	
	public String toString() {
		return ""+upCellContent+" "+downCellContent+" "+leftCellContent+" "+rightCellContent+" "+distanceX+" "+distanceY;
	}
}
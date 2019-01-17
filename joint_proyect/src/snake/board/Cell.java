package snake.board;

public class Cell {
	
	public static final int VOID = 0;
	public static final int SNAKE = 1;
	public static final int SNAKE_HEAD = 2;
	public static final int FOOD = 3;
	
	private int state;
	private int [] cellCoord;
	
	public Cell(int i, int j) {
		cellCoord = new int[2];
		cellCoord[0] = i;
		cellCoord[1] = j;
	}
	
	public int getContent(){
		return state;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int[] getCellCoord() {
		return cellCoord;
	}
	
	public String toString() {
		return cellCoord[0]+" "+cellCoord[1];
	}
}

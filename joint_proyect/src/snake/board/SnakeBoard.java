package snake.board;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import snake.input.Action;
import snake.input.Player;
import snake.input.Sarsa.SARSAPlayer;

public class SnakeBoard {

	public final static int MOVE_LEFT = -2;
	public final static int MOVE_RIGHT = 2;
	public final static int MOVE_UP = -1;
	public final static int MOVE_DOWN = 1;
	public final static int REWARD_MOVE = -10;
	public final static int REWARD_FOOD = 500;
	public final static int REWARD_COLLIDE = -100;
	private static int nextId = 1;
	
	private Cell[][] ArrayCells;
	private ArrayList<Cell> EmptyCellList;
	private final int id;
	private final Snake snake;
	private Player player;
	private Cell food;
	public static int BOARD_WIDTH;
	public static int BOARD_HEIGHT;
	private volatile int snakeDir;
	
	public SnakeBoard(int width, int height) {
		BOARD_WIDTH = width;
		BOARD_HEIGHT = height;
		this.id = nextId;
		nextId++;
		ArrayCells = new Cell[BOARD_WIDTH][BOARD_HEIGHT];
		for(int i = 0; i <BOARD_WIDTH;i++) {
			for(int j = 0; j<BOARD_HEIGHT;j++) {
				ArrayCells[i][j] = new Cell(i, j);
			}
		}
		EmptyCellList = new ArrayList<Cell>();
		snake = new Snake(this);
		reset();
	
	}
	
	public Cell[][] getArrayCells() {
		return ArrayCells;
	}
	
	public void moveSnake(Action a) {
		snakeDir = a.getAction();
		pulse();
	}
	
	public void pulse() {
		int[] snakePos = snake.getPos();
		Cell nextCell;
		switch(snakeDir) {
			case MOVE_LEFT:
				if(snakePos[0] == 0) {
					collide();
					return;
				}
				nextCell = ArrayCells[snakePos[0]-1][snakePos[1]];	
				break;			
			case MOVE_RIGHT:
				if(snakePos[0] == BOARD_WIDTH-1) {
					collide();
					return;
				}
				nextCell = ArrayCells[snakePos[0]+1][snakePos[1]];		
				break;
			case MOVE_UP:
				if(snakePos[1] == 0) {
					collide();
					return;
				}
				nextCell = ArrayCells[snakePos[0]][snakePos[1]-1];		
				break;
			default:
				if(snakePos[1] == BOARD_HEIGHT-1) {
					collide();
					return;
				}
				nextCell = ArrayCells[snakePos[0]][snakePos[1]+1];		
				break;
		}
		if(nextCell.getContent() == Cell.SNAKE) {
			//System.out.println("COLLIDING WITH SNAKE"); 
			collide();

		}else if(nextCell.getContent() == Cell.FOOD) {
			//System.out.println("eaten apple");
			giveReward(REWARD_FOOD);
			snake.advance(true, nextCell, EmptyCellList);
			genFood();

		}else if(nextCell.getContent() == Cell.VOID) {
			giveReward(REWARD_MOVE);
			snake.advance(false, nextCell, EmptyCellList);
		}		
	}
	
	private void genFood() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, EmptyCellList.size());
		//int randomNum = 0;	//No random food
		food = EmptyCellList.remove(randomNum);
		food.setState(Cell.FOOD);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		SnakeBoard other = (SnakeBoard) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	private void collide() {
		giveReward(REWARD_COLLIDE);
		reset();
	}
	
	private void giveReward(int x) {	
		player.reward(x);
	}
	
	private void reset() {
		initializeCells();
		snake.resetSnake();	
		snakeDir = MOVE_RIGHT;
		fillEmptyCellList();
		genFood();
	}
	
	private void initializeCells() {	
		for(int i = 0; i <BOARD_WIDTH;i++) {
			for(int j = 0; j<BOARD_HEIGHT;j++) {
				ArrayCells[i][j].setState(Cell.VOID);
			}
		}	
	}
	
	private void fillEmptyCellList() {
		EmptyCellList.clear();
		for(Cell[] a: ArrayCells) {
			for(Cell e: a) {
				if(e.getContent() == Cell.VOID) EmptyCellList.add(e);
			}
		}
		
	}
	
	public Cell getCell(int x, int y) {
		if(x>=0 && x<BOARD_WIDTH && y>=0 && y<BOARD_HEIGHT) {
			return ArrayCells[x][y];
		}else {
			return null;
		}
	}
	
	public Snake getSnake() {
		return snake;
	}
	
	public Cell getFoodCell() {
		return food;
	}

	public void setPlayer(Player p1) {
		this.player=p1;
		
	}

	public Player getPlayer() {
		return player;
	}
}

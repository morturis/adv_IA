package board;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import input.Player;
import input.SARSAPlayer;

public class Board {

	public final static int BOARD_WIDTH = 16;
	public final static int BOARD_HEIGHT = 12;
	private final static int MOVE_LEFT = 1;
	private final static int MOVE_RIGHT = 2;
	private final static int MOVE_UP = 3;
	private final static int MOVE_DOWN = 4;
	private final static int REWARD_MOVE = 1;
	private final static int REWARD_FOOD = 100;
	private final static int REWARD_COLLIDE = -10;
	private static int nextId = 1;
	
	private Cell[][] ArrayCells;
	private ArrayList<Cell> EmptyCellList;
	private final int id;
	private final Snake snake;
	private final Player player;
	
	private volatile int snakeDir;
	private int forbiddenDir;
	
	public Board() {
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
		player = new SARSAPlayer(this);	
		reset();
	
	}
	
	public Cell[][] getArrayCells() {
		return ArrayCells;
	}
	
	public void moveSnake(int x) {
		if (x != forbiddenDir) {
			snakeDir = x;
			switch(x) {
				case MOVE_LEFT:
					forbiddenDir = MOVE_RIGHT;
					break;
				case MOVE_RIGHT:
					forbiddenDir = MOVE_LEFT;
					break;
				case MOVE_UP:
					forbiddenDir = MOVE_DOWN;
					break;
				default:
					forbiddenDir = MOVE_UP;
					break;
			}
		}
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
		
		if(nextCell.getState() == Cell.SNAKE) {
			collide();

		}else if(nextCell.getState() == Cell.FOOD) {
			giveReward(REWARD_FOOD);
			snake.advance(true, nextCell, EmptyCellList);
			genFood();

		}else if(nextCell.getState() == Cell.VOID) {
			giveReward(REWARD_MOVE);
			snake.advance(false, nextCell, EmptyCellList);
		}
		
	}
	
	private void genFood() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, EmptyCellList.size());
		EmptyCellList.remove(randomNum).setState(Cell.FOOD);
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
		Board other = (Board) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	private void collide() {
		reset();
		giveReward(REWARD_COLLIDE);
	}
	
	private void giveReward(int x) {	
		player.reward(x);
	}
	
	private void reset() {
		initializeCells();
		snake.resetSnake();	
		snakeDir = MOVE_RIGHT;
		forbiddenDir = MOVE_LEFT;	
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
				if(e.getState() == Cell.VOID) EmptyCellList.add(e);
			}
		}
		
	}

}

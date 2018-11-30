package view;

import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import board.Board;
import board.Cell;
import input.DQNPlayer;
import input.Player;

@SuppressWarnings("serial")
public class View extends GraphicsProgram{
	private final static int CELL_SIZE = 30;
	
	private Player p1;
	private Board board;
	Cell[][]ArrayCells;	
	GRect[][] viewCells;
	
	public void init() {
		setSize(Board.BOARD_WIDTH*CELL_SIZE, Board.BOARD_HEIGHT*CELL_SIZE);
		board = new Board();
		ArrayCells = board.getArrayCells();
		viewCells = new GRect[Board.BOARD_WIDTH][Board.BOARD_HEIGHT];
		int rowOffset = 0;
		int columnOffset = 0;
		for(int i = 0; i < Board.BOARD_WIDTH;i++) {
			for(int j = 0; j< Board.BOARD_HEIGHT;j++) {
				viewCells[i][j] = new GRect(CELL_SIZE, CELL_SIZE);
				viewCells[i][j].setLocation(rowOffset, columnOffset);
				viewCells[i][j].setFilled(true);
				add(viewCells[i][j]);
				columnOffset+=CELL_SIZE;
			}
			columnOffset = 0;
			rowOffset += CELL_SIZE;
		}
		p1 = new DQNPlayer(board);
	}
	
	private void displayBoard() {
		ArrayCells = board.getArrayCells();
		for(int i = 0; i < Board.BOARD_WIDTH;i++) {
			for(int j = 0; j< Board.BOARD_HEIGHT;j++) {
				switch(ArrayCells[i][j].getContent()) {
					case Cell.SNAKE_HEAD:
						viewCells[i][j].setFillColor(Color.BLUE);
						break;
					case Cell.SNAKE:
						viewCells[i][j].setFillColor(Color.GREEN);
						break;
					case Cell.FOOD:
						viewCells[i][j].setFillColor(Color.RED);
						break;
					default:
						viewCells[i][j].setFillColor(Color.BLACK);
						break;					
				}				
			}
		}
	}
	
	public synchronized void run() {
		while(true) {
			board.pulse();
			displayBoard();
			p1.run();
			try {
				wait(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
	
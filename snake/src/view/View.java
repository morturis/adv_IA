package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import board.SnakeBoard;
import board.Cell;
import input.Player;
import input.QLearning.QPlayer;
import input.SARSA.SARSAPlayer;

@SuppressWarnings("serial")
public class View extends GraphicsProgram{
	
	public View () {
		super();
	}
	private final static int CELL_SIZE = 30;
	
	private Player p1;
	private SnakeBoard board;
	Cell[][]ArrayCells;	
	GRect[][] viewCells;
	Button buttonEpsilon;
	Button buttonSave;
	Button changeSpeed;
	int speed = 5;	//lower = faster
	
	
	public void init() {
		setSize(SnakeBoard.BOARD_WIDTH*CELL_SIZE + 100, SnakeBoard.BOARD_HEIGHT*CELL_SIZE);
		board = new SnakeBoard();
		
		int type = 1;	//This changes the algorithm to be used
						//1 is sarsa, 0 is qlearning
		initPlayer(type);
		
		board.setPlayer(p1);
		ArrayCells = board.getArrayCells();
		viewCells = new GRect[SnakeBoard.BOARD_WIDTH][SnakeBoard.BOARD_HEIGHT];
		int rowOffset = 0;
		int columnOffset = 0;
		for(int i = 0; i < SnakeBoard.BOARD_WIDTH;i++) {
			for(int j = 0; j< SnakeBoard.BOARD_HEIGHT;j++) {
				viewCells[i][j] = new GRect(CELL_SIZE, CELL_SIZE);
				viewCells[i][j].setLocation(rowOffset, columnOffset);
				viewCells[i][j].setFilled(true);
				add(viewCells[i][j]);
				columnOffset+=CELL_SIZE;
			}
			columnOffset = 0;
			rowOffset += CELL_SIZE;
		}
		buttonEpsilon = new Button("epsilon = 0");
		buttonEpsilon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				p1.noMoreRandomness();
				System.out.println("Stopping epsilon-greedy and assuming map is correct");
			}
				
		});
		add(buttonEpsilon, SnakeBoard.BOARD_WIDTH*CELL_SIZE+5, 5);
		
		
		changeSpeed = new Button("Change speed");
		changeSpeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(speed == 5) speed = 200;
				else speed = 5;
			}
			
		});
		add(changeSpeed, SnakeBoard.BOARD_WIDTH*CELL_SIZE+5, 45);
	}
	
	private void initPlayer(int type) {
		if (type == 0) p1 = new QPlayer(board);
		else if (type == 1) p1 = new SARSAPlayer(board);
		
	}
	
	//Reads board and displays
	private void displayBoard() {
		ArrayCells = board.getArrayCells();
		for(int i = 0; i < SnakeBoard.BOARD_WIDTH;i++) {
			for(int j = 0; j< SnakeBoard.BOARD_HEIGHT;j++) {
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
			displayBoard();
			p1.run();
			try {
				wait(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
	
package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import board.Board;
import board.Cell;
import input.Player;
import input.DQN.DQNPlayer;
import input.QLearning.QPlayer;
import input.SARSA.SARSAPlayer;

@SuppressWarnings("serial")
public class View extends GraphicsProgram{
	
	public View () {
		super();
	}
	private final static int CELL_SIZE = 30;
	
	private Player p1;
	private Board board;
	Cell[][]ArrayCells;	
	GRect[][] viewCells;
	Button buttonEpsilon;
	Button buttonSave;
	Button changeSpeed;
	int speed = 5;	//lower = faster
	
	protected boolean hasSaved = false;
	
	public void init() {
		setSize(Board.BOARD_WIDTH*CELL_SIZE + 100, Board.BOARD_HEIGHT*CELL_SIZE);
		board = new Board();
		
		p1 = new QPlayer(board);
		
		board.setPlayer(p1);
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
		buttonEpsilon = new Button("epsilon = 0");
		buttonEpsilon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				p1.noMoreRandomness();
				System.out.println("Stopping epsilon-greedy and assuming map is correct");
			}
				
		});
		add(buttonEpsilon, Board.BOARD_WIDTH*CELL_SIZE+5, 5);
		
		buttonSave = new Button("Save");
		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
				hasSaved  = true;
				p1.saveToFile();
			}
			
		});
		add(buttonSave, Board.BOARD_WIDTH*CELL_SIZE+5, 25);
		changeSpeed = new Button("Change speed");
		changeSpeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(speed == 5) speed = 200;
				else speed = 5;
			}
			
		});
		add(changeSpeed, Board.BOARD_WIDTH*CELL_SIZE+5, 45);
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
		while(!hasSaved) {
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
	
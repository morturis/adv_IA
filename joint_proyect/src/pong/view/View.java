package pong.view;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
//import persistence.Persistence;
import pong.game.Board;
import pong.input.*;
import pong.input.QLearning.QPlayer;
import pong.input.SARSA.SARSAPlayer;

@SuppressWarnings("serial")
public class View extends GraphicsProgram{
	public final static int PLAYER_HEIGHT = 8;
	public final static int PLAYER_WIDTH = 50;
	public static final int BALL_SIZE = 6;
	Button buttonEpsilon;
	Button changeSpeed;
	Board board;
	GRect field;	
	GOval ball;
	
	GRect p0rect;
	Player p0;
	
	GRect p1rect;
	Player p1;
	
	GLabel scorep0;
	GLabel scorep1;
	
	//The higher wait time, the slower it plays
	int waitTime = 1;
	
	public synchronized void init(int type) {		
		setSize(Board.BOARD_HEIGHT+200, Board.BOARD_WIDTH+20);
		board = new Board();
		board.init();
		
		if(type == 1) {
			p0 = new QPlayer(0, board);
			p1 = new QPlayer(1, board);
		}else if (type == 0){
			p0 = new SARSAPlayer(0, board);
			p1 = new SARSAPlayer(1, board);
		}
		board.setPlayer(0, p0);
		board.setPlayer(1, p1);
		
		int[] playersPos = board.getPlayersPos();		
		
		//p0 is top p1 is bot
		p0rect = new GRect(PLAYER_WIDTH, PLAYER_HEIGHT);
		p0rect.setLocation(playersPos[0], Board.TOP_PLAYER_YOFFSET);	
		p0rect.setFillColor(Color.YELLOW);
		p0rect.setFilled(true);
		p1rect = new GRect(PLAYER_WIDTH, PLAYER_HEIGHT);
		p1rect.setLocation(playersPos[1], Board.BOTTOM_PLAYER_YOFFSET);
		p1rect.setFillColor(Color.YELLOW);
		p1rect.setFilled(true);
		
		ball = new GOval(BALL_SIZE, BALL_SIZE);
		int[]ballPos = board.getBallPos();
		ball.setLocation(ballPos[0], ballPos[1]);
		ball.setFillColor(Color.WHITE);
		ball.setFilled(true);
		
		field = new GRect(Board.BOARD_HEIGHT, Board.BOARD_WIDTH);
		field.setFillColor(Color.BLACK);
		field.setFilled(true);
		
		scorep0 = new GLabel (""+board.getScore(0), Board.BOARD_WIDTH+10, 10);
		scorep1 = new GLabel (""+board.getScore(1), Board.BOARD_WIDTH+10, Board.BOARD_HEIGHT-10);
				
		add(field);
		add(p0rect); add(p1rect); add(ball);
		add(scorep0); add(scorep1);
		
		changeSpeed = new Button("Change speed");
		changeSpeed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(waitTime == 1) waitTime = 10;
				else waitTime = 1;
			}
			
		});
		add(changeSpeed, Board.BOARD_WIDTH+10, 50);
		buttonEpsilon = new Button("epsilon = 0");
		buttonEpsilon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				p0.noMoreRandomness();
				p1.noMoreRandomness();
				System.out.println("Stopping epsilon-greedy and assuming map is correct");
			}
				
		});
		add(buttonEpsilon, Board.BOARD_WIDTH + 10 , 25);
	}
	
	public synchronized void run(){
		while(true) {
			displayBoard();	
			p0.pulse();
			p1.pulse();
			board.pulse();
			try {
				wait(waitTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * Reads from board and displays
	 */
	void displayBoard() {
		int[] playersPos = board.getPlayersPos();
		int[] ballPos = board.getBallPos();
		p0rect.setLocation(playersPos[1], Board.BOTTOM_PLAYER_YOFFSET);
		p1rect.setLocation(playersPos[0], Board.TOP_PLAYER_YOFFSET);
		ball.setLocation(ballPos[0], ballPos[1]);
		scorep0.setLabel(board.getScore(0)+"");
		scorep1.setLabel(board.getScore(1)+"");
	}	
	
	
}

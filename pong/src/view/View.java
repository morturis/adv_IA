package view;

import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import game.Board;
import input.*;

@SuppressWarnings("serial")
public class View extends GraphicsProgram{
	//Refresh rate of the screen
	final static int FPS = 100;
	
	Board board;
	GRect field;	
	GOval ball;
	
	Thread ai1;
	GRect p1;
	AIPlayer player1;
	
	Thread ai2;
	GRect p2;
	AIPlayer player2;
	
	GLabel scoreP1;
	GLabel scoreP2;
	
	public synchronized void init() {
		setSize(Board.BOARD_HEIGHT+50, Board.BOARD_WIDTH+50);
		board = new Board();
		board.init();
		
		int[] playersPos = board.getPlayersPos();		
		p1 = new GRect(Board.PLAYER_LENGTH, Board.PLAYER_HEIGHT);
		p1.setLocation(playersPos[0], Board.BOTTOM_PLAYER_YOFFSET);	
		p1.setFillColor(Color.YELLOW);
		p1.setFilled(true);
		p2 = new GRect(Board.PLAYER_LENGTH, Board.PLAYER_HEIGHT);
		p2.setLocation(playersPos[1], Board.TOP_PLAYER_YOFFSET);
		p2.setFillColor(Color.YELLOW);
		p2.setFilled(true);
		
		player1 = new AIPlayer(0, board);
		player2 = new AIPlayer(1, board);
		
		board.setPlayers(player1, player2);
		ai1 = new Thread(player1);
		ai2 = new Thread(player2);
		
		ai1.start();
		ai2.start();
		
		ball = new GOval(Board.BALL_SIZE, Board.BALL_SIZE);
		int[]ballPos = board.getBallPos();
		ball.setLocation(ballPos[0], ballPos[1]);
		ball.setFillColor(Color.WHITE);
		ball.setFilled(true);
		
		field = new GRect(Board.BOARD_HEIGHT, Board.BOARD_WIDTH);
		field.setFillColor(Color.BLACK);
		field.setFilled(true);
		
		scoreP1 = new GLabel (""+board.getScore(0), Board.BOARD_WIDTH+10, 10);
		scoreP2 = new GLabel (""+board.getScore(1), Board.BOARD_WIDTH+10, Board.BOARD_HEIGHT);
				
		add(field);
		add(p1); add(p2); add(ball);
		add(scoreP1); add(scoreP2);
	}
	
	public void run(){
		while(true) {
			pause(1000/FPS);
			displayBoard();	
			board.moveBall();
			board.checkCollisions();	
		}
	}
	/*
	 * Reads from board and displays
	 */
	void displayBoard() {
		int[] playersPos = board.getPlayersPos();
		int[] ballPos = board.getBallPos();
		p1.setLocation(playersPos[0], Board.BOTTOM_PLAYER_YOFFSET);
		p2.setLocation(playersPos[1], Board.TOP_PLAYER_YOFFSET);
		ball.setLocation(ballPos[0], ballPos[1]);
		scoreP1.setLabel(board.getScore(0)+"");
		scoreP2.setLabel(board.getScore(1)+"");
	}	
	
}

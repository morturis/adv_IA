package pong.game;

import java.util.concurrent.ThreadLocalRandom;

import pong.input.Player;
import pong.view.View;


public class Board {
	public final static int BOARD_HEIGHT = 400;
	public final static int BOARD_WIDTH = 400;
	public final static int PLAYER_SPEED = 8;
	public final static int BALL_SPEED = 7;
	public final static int TOP_PLAYER_YOFFSET = 0;
	public final static int BOTTOM_PLAYER_YOFFSET = BOARD_HEIGHT-View.PLAYER_HEIGHT;
	public final static int REWARD_GOAL = 10;
	public final static int REWARD_HIT = 1;
	public final static int REWARD_MISS = -20;
	
	static int nextId = 1;
	int id;
	
	
	int ballX;
	int ballY;
	
	int ballXSpeed;
	int ballYSpeed;
	
	int p0_x;
	int p1_x;
	
	int p0_score = 0;
	int p1_score = 0;
	
	Player p0;	//top
	Player p1;	//bot
	
	/*
	 * Initializes or resets the board 
	 * Calls resetBall() and sets the players to the leftmost corner
	 */
	public void init() {
		this.id = nextId;
		nextId++;
		resetBall();
		resetPlayers();
	}
	private void resetPlayers() {
		p0_x  = 0;
		p1_x = 0;
	}
	/*
	 * Takes players to be able to give them rewards
	 */
	public void setPlayer(int id, Player p) {
		if(id == 0) p0 = p;
		else if (id == 1) p1 = p;
	}
	
	/*
	 * Checks the position of the ball and the players to calculate possible collisions
	 * doesn't resolve the collisions, but calls collideWall() or collide() to do so
	 */
	private void checkCollisions() {
		if(ballX < 0) collideWall();
		if(ballX > BOARD_WIDTH) collideWall();
		
		//Checking collisions with top player
		if(ballY<=View.PLAYER_HEIGHT) {	//If true, is in a zone of potential collision
			if(ballX + View.BALL_SIZE >= p0_x && ballX <= p0_x + View.PLAYER_WIDTH) {
				p0.reward(REWARD_HIT);
				collidePlayer(0);
			}
		}
		
		//Check collisions with bottom player
		if(ballY>=BOARD_HEIGHT-View.PLAYER_HEIGHT) {	//If true is in a zone of potential collision
			if(ballX + View.BALL_SIZE >= p1_x && ballX<= p1_x + View.PLAYER_WIDTH) {
				p1.reward(REWARD_HIT);
				collidePlayer(1);
			}
		}
	}
	
	/*
	 * Resolves collision with any player
	 */
	private void collidePlayer(int whichPlayer) {
		ballYSpeed = -ballYSpeed;
		float factor;
		if(whichPlayer == 0) {
			float playerMiddle = p0_x + View.PLAYER_WIDTH/2;
			factor = ballX - playerMiddle;
			factor = factor/(View.PLAYER_WIDTH/2);
			ballY = View.PLAYER_HEIGHT+1;
		}else {
			float playerMiddle = p1_x + View.PLAYER_WIDTH/2;
			factor = ballX - playerMiddle;
			factor = factor/(View.PLAYER_WIDTH/2);
			ballY = BOARD_HEIGHT -  View.PLAYER_HEIGHT -1;
		}
		ballXSpeed = Math.round(factor*BALL_SPEED);
		//int randomNum = ThreadLocalRandom.current().nextInt(-1, 2);
		//ballXSpeed += randomNum;
	}
	
	/*
	 * Resolves collision with a wall
	 */
	private void collideWall() {
		ballXSpeed = - ballXSpeed;
	}
	
	/*
	 * This method is call every iteration of the Thread to move the ball to a new location
	 * uses the current position and the current speeds to calculate the new position
	 * This is the method that checks for GOALS
	 */
	private void moveBall () {
		ballX += ballXSpeed;
		ballY += ballYSpeed;
		if(ballY + View.BALL_SIZE/2< 0) {
			//bottom player scores (p1)
			p1.reward(REWARD_GOAL);
			p0.reward(REWARD_MISS);
			p1_score++;
			resetBall();
			//resetPlayers();
		}else if (ballY + View.BALL_SIZE/2> BOARD_HEIGHT) {
			//Top player scores (p0)
			p0.reward(REWARD_GOAL);
			p1.reward(REWARD_MISS);
			p0_score++;
			resetBall();
		}
	}
	
	
	public void movePlayer(int id, int dir) {
		//p0 is top p1 is bot
		if(id == 1) {
			p1_x += PLAYER_SPEED*dir;
			if(p1_x < 0) p1_x = 0;
			if(p1_x > BOARD_WIDTH - View.PLAYER_WIDTH) p1_x = BOARD_WIDTH-View.PLAYER_WIDTH;
		}else if(id == 0) {
			p0_x += PLAYER_SPEED*dir;
			if(p0_x < 0) p0_x = 0;
			if(p0_x > BOARD_WIDTH - View.PLAYER_WIDTH) p0_x = BOARD_WIDTH-View.PLAYER_WIDTH;
		}
	}
	
	/*
	 * Resets the ball to starting location 
	 * also randomizes its speeds (direction+module)
	 */
	private void resetBall() {
		ballX = BOARD_WIDTH/2;
		ballY = BOARD_HEIGHT/2;

		int randomNum = ThreadLocalRandom.current().nextInt(-BALL_SPEED, BALL_SPEED+1);
		ballXSpeed = randomNum;
		if(Math.random() > 0.5) ballYSpeed = BALL_SPEED;
		else ballYSpeed = -BALL_SPEED;
		
	}
	
	/*
	 * Returns ball position as follows
	 * {ballX, ballY}
	 */
	public int[] getBallPos() {
		return new int[] {ballX, ballY};
	}
	/*
	 * return positions of both players as follows
	 * {player0, player1}
	 * only returns X positions since Y position is constants
	 */
	public int[] getPlayersPos() {
		return new int[] {p0_x, p1_x};
	}
	
	public int getScore(int id) {
		if (id == 0) return p0_score;
		else if (id == 1)return p1_score;
		return -1;
	}
	public void pulse() {
		moveBall();
		checkCollisions();
		
	}
	public int[] getBallSpeed() {
		int[] res = {ballXSpeed, ballYSpeed};
		return res;
	}
	
}

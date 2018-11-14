package game;

import input.AIPlayer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	public final static int BOARD_HEIGHT = 600;
	public final static int BOARD_WIDTH = 600;
	public final static int PLAYER_LENGTH = 60;
	public final static int PLAYER_HEIGHT = 20;
	public final static int PLAYER_SPEED = 60;
	public final static int BALL_SIZE = 6;
	public final static int BALL_SPEED = 10;
	public final static int TOP_PLAYER_YOFFSET = 0;
	public final static int BOTTOM_PLAYER_YOFFSET = BOARD_HEIGHT-PLAYER_HEIGHT;
	final static int GOAL_REWARD = 10;
	final static int HIT_REWARD = 1;
	static int nextId = 1;
	int id;
	
	int move_up_speed;
	double move_right_speed;
	
	int ballX;
	int ballY;
	
	int top_player_x;
	int bottom_player_x;
	
	int top_player_score = 0;
	int bottom_player_score = 0;
	
	AIPlayer p0;	//top
	AIPlayer p1;	//bot
	public boolean terminatePlayers = false;
	
	/*
	 * Initializes or resets the board 
	 * Calls resetBall() and sets the players to the leftmost corner
	 */
	public void init() {
		this.id = nextId;
		nextId++;
		resetBall();
		//top_player_x  = (BOARD_WIDTH-PLAYER_LENGTH)/2;
		//bottom_player_x = (BOARD_WIDTH-PLAYER_LENGTH)/2;
		top_player_x = 0;
		bottom_player_x = 0;
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
	/*
	 * Takes players to be able to give them rewards
	 */
	public void setPlayers(AIPlayer p0, AIPlayer p1) {
		this.p0 = p0;
		this.p1 = p1;
	}
	/*
	 * Checks the position of the ball and the players to calculate possible collisions
	 * doesn't resolve the collisions, but calls collideWall() or collide() to do so
	 */
	public void checkCollisions() {
		if(ballX<=0) collideWall();
		if(ballX>=BOARD_WIDTH) collideWall();
		//Checking collisions with top player
		if(ballY<=PLAYER_HEIGHT) {	//If true, is in a zone of potential collision
			if(ballX+BALL_SIZE>=top_player_x && ballX <= top_player_x+PLAYER_LENGTH) {
				collide(0);
				p0.reward(HIT_REWARD);
			}
		}
		
		//Check collisions with bottom player
		if(ballY>=BOARD_HEIGHT-PLAYER_HEIGHT) {	//If true is in a zone of potential collision
			if(ballX+BALL_SIZE>=bottom_player_x &&	ballX<=bottom_player_x+PLAYER_LENGTH) {	
				collide(1);
				p1.reward(HIT_REWARD);
			}
		}
	}
	
	/*
	 * Resolves collision with any player
	 */
	private void collide(int playerID) {
		double contactPoint = 0;
		if(playerID == 0) {
			move_up_speed = BALL_SPEED;	
			contactPoint = (top_player_x+PLAYER_LENGTH/2)-(ballX+BALL_SIZE/2);
		}else if (playerID == 1) {
			move_up_speed = -BALL_SPEED;
			contactPoint = (bottom_player_x+PLAYER_LENGTH/2)-(ballX+BALL_SIZE/2);	
		}
		contactPoint = contactPoint/PLAYER_LENGTH/2;
		//move_right_speed = contactPoint*BALL_SPEED*2+ThreadLocalRandom.current().nextInt(-1, 3);
		move_right_speed = contactPoint*BALL_SPEED*2.5;
		move_right_speed*=-1;
		System.out.println(contactPoint + " " + move_right_speed + " " + move_up_speed);
	}
	
	/*
	 * Resolves collision with a wall
	 */
	private void collideWall() {
		move_right_speed = -move_right_speed;
	}
	
	/*
	 * This method is call every iteration of the Thread to move the ball to a new location
	 * uses the current position and the current speeds to calculate the new position
	 * This is the method that checks for GOALS
	 */
	public void moveBall () {
		ballX = (int) (ballX + Math.round(move_right_speed));
		ballY = ballY+move_up_speed;
		if(ballY<=0) {
			bottom_player_score++;
			if(p0 instanceof AIPlayer) ((AIPlayer) p0).reward(GOAL_REWARD);
			if(p1 instanceof AIPlayer) ((AIPlayer) p1).reward(-GOAL_REWARD);
			resetBall();
		}else if(ballY>=BOARD_HEIGHT) {
			top_player_score++;
			if(p1 instanceof AIPlayer) ((AIPlayer) p1).reward(GOAL_REWARD);
			if(p0 instanceof AIPlayer) ((AIPlayer) p0).reward(-GOAL_REWARD);
			resetBall();
		}
	}
	
	/*
	 * Public method to move a given player
	 * @params id, id of the player to be moved
	 * id is 1 for bottom or 0 for top
	 * @params dir, direction to be moved
	 * dir is +1 for rightmove or -1 for leftmove
	 * also allows dir = 0 to remain still
	 */
	public synchronized void movePlayer(int id, int dir) {
		if(id == 0) {
			bottom_player_x = bottom_player_x+PLAYER_SPEED*dir;
			if(bottom_player_x <0) bottom_player_x = 0;
			if(bottom_player_x > BOARD_WIDTH-PLAYER_LENGTH) bottom_player_x = BOARD_WIDTH-PLAYER_LENGTH;
		}else if(id == 1) {
			top_player_x = top_player_x+PLAYER_SPEED*dir;
			if(top_player_x <0) top_player_x = 0;
			if(top_player_x >BOARD_WIDTH-PLAYER_LENGTH) top_player_x = BOARD_WIDTH-PLAYER_LENGTH;
		}
	}
	
	/*
	 * Resets the ball to starting location 
	 * also randomizes its speeds (direction+module)
	 */
	private void resetBall() {
		ballX = BOARD_WIDTH/2;
		ballY = BOARD_HEIGHT/2;
		
		move_right_speed = ThreadLocalRandom.current().nextInt(-BALL_SPEED*2, BALL_SPEED*2 + 1);
		Random rando = new Random();
		if(rando.nextBoolean()) move_up_speed = BALL_SPEED;
		else move_up_speed = -BALL_SPEED;
		
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
		return new int[] {bottom_player_x, top_player_x};
	}
	
	public int getScore(int id) {
		if (id == 0) return top_player_score;
		else if (id == 1)return bottom_player_score;
		return -1;
	}
	
}

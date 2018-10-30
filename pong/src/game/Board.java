package game;

import input.AIPlayer;
import input.Player;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
	public final static int BOARD_HEIGHT = 600;
	public final static int BOARD_WIDTH = 600;
	public final static int PLAYER_LENGTH = 60;
	public final static int PLAYER_HEIGHT = 20;
	public final static int PLAYER_SPEED = 60;
	public final static int BALL_SIZE = 5;
	public final static int BALL_SPEED = 10;
	public final static int TOP_PLAYER_YOFFSET = 0;
	public final static int BOTTOM_PLAYER_YOFFSET = 580;
	static int nextId = 1;
	int id;
	
	int move_up_speed;
	int move_right_speed;
	
	int ballX;
	int ballY;
	
	int top_player_x;
	int bottom_player_x;
	
	int top_player_score = 0;
	int bottom_player_score = 0;
	
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
	public void setPlayers(Player p0, Player p1) {
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
				collide();
			}
		}
		
		//Check collisions with bottom player
		if(ballY>=BOARD_HEIGHT-PLAYER_HEIGHT) {	//If true is in a zone of potential collision
			if(ballX+BALL_SIZE>=bottom_player_x &&	ballX<=bottom_player_x+PLAYER_LENGTH) {	
				collide();
			}
		}
	}
	
	/*
	 * Resolves collision with any player
	 */
	private void collide() {		
		move_right_speed = ThreadLocalRandom.current().nextInt(-BALL_SPEED, BALL_SPEED + 1);	
		move_up_speed = (int) (-move_up_speed);	
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
			if(p1 instanceof AIPlayer) ((AIPlayer) p1).reward(-100);
			if(p0 instanceof AIPlayer) ((AIPlayer) p0).reward(100);
			resetBall();
		}else if(ballY>=BOARD_HEIGHT) {
			if(p0 instanceof AIPlayer) ((AIPlayer) p0).reward(-100);
			if(p1 instanceof AIPlayer) ((AIPlayer) p1).reward(100);
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
	public void movePlayer(int id, int dir) {
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
		move_right_speed = ThreadLocalRandom.current().nextInt(-BALL_SPEED, BALL_SPEED + 1);
		move_up_speed = BALL_SPEED/2;
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
	
}

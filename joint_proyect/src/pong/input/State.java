package pong.input;

import pong.game.Board;
import pong.view.View;

public class State {	
	
	// Simple state with grid
	int relativeBallX;
	int enemyRelativeBallX;
	
	public State(int id, Board board) {
		int playerPos = board.getPlayersPos()[id];
		int enemyPos = board.getPlayersPos()[(id+1)%2];
		int[] ballPos = board.getBallPos();
		
		//Calc relative X (+1 if to the right, -1 if to the left)
		if(ballPos[0] > playerPos + View.PLAYER_WIDTH) {
			relativeBallX = +1;
		}else if(ballPos[0] < playerPos) {
			relativeBallX = -1;
		}else{
			relativeBallX = 0;
		}
		
		if(ballPos[0] > enemyPos + View.PLAYER_WIDTH) {
			enemyRelativeBallX = 1;
		}else if(ballPos[0] < enemyPos) {
			enemyRelativeBallX = -1;
		}else{
			enemyRelativeBallX = 0;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + relativeBallX;
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
		State other = (State) obj;
		if (relativeBallX != other.relativeBallX)
			return false;
		return true;
	}
	
		
}

package input;

import game.Board;

public class ActorState {	
	static final int GRID_SQUARE_SIZE = Board.BOARD_WIDTH/AIPlayer.NUMBER_OF_GRID;
	
	// Simple state with grid
	int selfGridX;
	int enemyGridX;
	int ballGridX;
	int ballGridY;	
	
	public ActorState(int id, Board board) {
		selfGridX = (int) (board.getPlayersPos()[id]/GRID_SQUARE_SIZE);
		enemyGridX = (int) (board.getPlayersPos()[(id+1)%2]/GRID_SQUARE_SIZE);
		ballGridX = (int) (board.getBallPos()[0]/GRID_SQUARE_SIZE);
		ballGridY = (int) (board.getBallPos()[1]/GRID_SQUARE_SIZE);
	}
	
	ActorState(ActorState pastState){
		this.selfGridX = pastState.selfGridX;
		this.enemyGridX = pastState.enemyGridX;
		this.ballGridX = pastState.ballGridX;
		this.ballGridY = pastState.ballGridY;
	}
	
	public ActorState(String s){
		String[] actorStatesAttr = s.split(" ");
		selfGridX = Integer.parseInt(actorStatesAttr[0]);
		enemyGridX = Integer.parseInt(actorStatesAttr[1]);
		ballGridX = Integer.parseInt(actorStatesAttr[2]);
		ballGridY = Integer.parseInt(actorStatesAttr[3]);
	}
	
	
	public ActorState getFollowing(Action a) {
		ActorState result = new ActorState(this);
		result.selfGridX += a.action;
		if(result.selfGridX <0 || result.selfGridX > AIPlayer.NUMBER_OF_GRID) return null;
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ballGridX;
		result = prime * result + ballGridY;
		result = prime * result + enemyGridX;
		result = prime * result + selfGridX;
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
		ActorState other = (ActorState) obj;
		if (ballGridX != other.ballGridX)
			return false;
		if (ballGridY != other.ballGridY)
			return false;
		if (enemyGridX != other.enemyGridX)
			return false;
		if (selfGridX != other.selfGridX)
			return false;
		return true;
	}
	
	public String toString() {
		return selfGridX + " " + enemyGridX + " " + ballGridX + " " + ballGridY;
	}
	
}

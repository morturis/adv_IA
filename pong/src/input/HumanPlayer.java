package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import game.Board;

public class HumanPlayer extends Player implements KeyListener{
	final static int LEFT = -1;
	final static int RIGHT = 1;

	public HumanPlayer(int id, Board board) {
		super(id, board);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(id == 1) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					board.movePlayer(id, RIGHT);
					break;
				case KeyEvent.VK_LEFT:
					board.movePlayer(id, LEFT);
					break;
			}
		}else if(id == 0) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_RIGHT:
					board.movePlayer(id, RIGHT);
					break;
				case KeyEvent.VK_LEFT:
					board.movePlayer(id, LEFT);
					break;
			}
		}else if (id == 1) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_D:
					board.movePlayer(id, RIGHT);
					break;
				case KeyEvent.VK_A:
					board.movePlayer(id, LEFT);
					break;
			}			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	
}

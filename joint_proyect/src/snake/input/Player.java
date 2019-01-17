package snake.input;


//import Persistence.Persistence;
import snake.board.SnakeBoard;

public abstract class Player{
	protected  static final double LEARNING_RATE = 0.1;	//alfa
	protected  static final double DISCOUNT_FACTOR = 0.9;	//gamma
	
	private volatile static int nextId;
	protected  double EPSILON = 0.9;
	protected  final int id;
	protected  final SnakeBoard board;
	protected double reward;
	protected Tuple currentTuple;	//(currentState, actionToBeTaken)
	protected int counter = 0;
	//protected Persistence p;
	
	protected int lowIncrementsInARow;
	protected int whenItWasTerminal;
	
	protected Player(SnakeBoard board) {
		this.id = nextId;
		nextId++;
		this.board = board;
	}
	
	protected abstract Action chooseAction();	
	protected void takeAction(Action a) {
		board.moveSnake(a);		
	}
	protected abstract void update();
	public abstract void saveToFile();

	
	
	public void reward(int x) {
		reward = x;
	}
	

	public void noMoreRandomness() {
		EPSILON = 0;
	}
	
	@Override
	public  int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public  boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public void run() {
		counter++;
		if(counter%100 == 0 && EPSILON >0.1) EPSILON = EPSILON - 0.1;
		System.out.println(counter);
		//This chooses the action, takes the action and receives the reward via the reward field
		State state = new State(board);
		Action action = chooseAction();
		currentTuple = new Tuple(state, action);
		takeAction(action);		
		update();
	}
	
}

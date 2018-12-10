import view.View;

public class BotProject {

	public BotProject() {
		// TODO Auto-generated constructor stub
	}

	//This main method launches an instance of the View class
	//which then does all the work
	//This is for pong
	public static void main(String[] args) {
		View v = new View();
		v.init();
		v.start();

	}

}

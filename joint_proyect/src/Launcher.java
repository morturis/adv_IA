import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import snake.view.View;

public class Launcher extends JFrame{
	JPanel radioButtons = new JPanel(new GridLayout(2, 1));
	JPanel gameSelect = new JPanel(new FlowLayout());
	ButtonGroup gameSelectGroup = new ButtonGroup();
	JRadioButton snakeButton = new JRadioButton("Snake");
	JRadioButton pongButton = new JRadioButton("Pong");
	
	JPanel algoSelect = new JPanel(new FlowLayout());
	ButtonGroup algoSelectGroup = new ButtonGroup();
	JRadioButton qButton = new JRadioButton("QLearning");
	JRadioButton sarsaButton = new JRadioButton("SarsaLambda");
	
	
	JPanel paramSelect = new JPanel(new GridLayout(2, 2));
	JTextField width = new JTextField();
	JTextField height = new JTextField();
	
	JButton launchButton = new JButton("Launch!");
	
	public Launcher() {
		super("botproject");
		setLayout(new GridLayout(4, 1));
		
		populateRadioButtons();
		populateParamInput();
		createLaunchButton();		
		
		pack();
		setSize(getPreferredSize());
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void createLaunchButton() {
		launchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int algoChosen = 0;
				//1 = q 0 = sarsa
				if(qButton.isSelected()) algoChosen = 1;
				else if (sarsaButton.isSelected()) algoChosen = 0;
				
				
				
				if(snakeButton.isSelected()) {
					int paramWidth = 5;	//Default to 5
					int paramHeight = 5;
					try{
						paramWidth = Integer.parseInt(width.getText());
						paramHeight = Integer.parseInt(height.getText());	
					}catch(NumberFormatException exception) {
						//exception.printStackTrace();
						paramWidth = 5;	//Default to 5
						paramHeight = 5;
					}
					View v = new snake.view.View();
					v.init(paramWidth, paramHeight, algoChosen);
					
					JFrame aux = new JFrame();
					aux.add(v);
					aux.pack();
					aux.setSize(v.getSize());
					aux.setLocationRelativeTo(null);
					aux.setVisible(true);
					
					v.start();
					
				}else if(pongButton.isSelected()) {
					pong.view.View v = new pong.view.View();
					v.init(algoChosen);
					JFrame aux = new JFrame();
					aux.add(v);
					aux.pack();
					aux.setSize(v.getSize());
					aux.setLocationRelativeTo(null);
					aux.setVisible(true);
					v.start();
				}
			}
		});
		this.add(launchButton);
	}

	private void populateParamInput() {

		paramSelect.add(new JLabel("  Board Width"));
		paramSelect.add(width);
		paramSelect.add(new JLabel("  Board Height"));
		paramSelect.add(height);
		this.add(paramSelect);
		
	}

	private void populateRadioButtons() {		
		pongButton.setSelected(true);
		qButton.setSelected(true);
		//Adds the buttons to a group
		gameSelectGroup.add(snakeButton);
		gameSelectGroup.add(pongButton);
		//Adds button to a jpanel
		gameSelect.add(new JLabel("Choose game:"));
		gameSelect.add(snakeButton);
		gameSelect.add(pongButton);
		
		algoSelectGroup.add(qButton);
		algoSelectGroup.add(sarsaButton);
		algoSelect.add(new JLabel("Choose algorithm:"));
		algoSelect.add(qButton);
		algoSelect.add(sarsaButton);

		radioButtons.add(gameSelect);
		radioButtons.add(algoSelect);
		this.add(radioButtons);
	}
}

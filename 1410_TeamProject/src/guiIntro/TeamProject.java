package guiIntro;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI application
 * @author Khoi Nguyen
 *
 */
public class TeamProject extends JFrame{
	
	private JPanel contentPane;
	public static LinkedHashMap<String, Boolean> gameWords;
	private List<String> sixLetterWords;
	
	Timer timer;

    
	/**
	 * Create the frame.
	 */
	public TeamProject(List<String> sixLetterWords) {
		this.sixLetterWords = sixLetterWords;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 402);
		
		JMenuBar menuBar = menuBar();
		setJMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTwist = buttonTwist();
		contentPane.add(btnTwist);
		
		JButton btnEnter = buttonEnter();
		contentPane.add(btnEnter);
		
		JButton btnLastWord = buttonLastWord();
		contentPane.add(btnLastWord);
		
		JButton btnClear = buttonClear();
		contentPane.add(btnClear);
		
		JLabel lblScore = labelScore();
		contentPane.add(lblScore);
		
		JLabel lblTime = labelTime();
		contentPane.add(lblTime);
		
		JLabel lblName = labelName();
		contentPane.add(lblName);
		
		JLabel label = labelYourName();
		contentPane.add(label);
		
		JLabel lblMessage = labelMessage();
		contentPane.add(lblMessage);
		
		JLabel lblTimer = labelTimer();
		contentPane.add(lblTimer);
		
		//String name = JOptionPane.showInputDialog("Please enter your name below:");
		//label.setText(name);
	}

	private JLabel labelTimer() {
		JLabel lblTimer = new JLabel("03:00");
		lblTimer.setBounds(327, 311, 46, 14);
		
		
		timer = new Timer(1000, new ActionListener() {
			int time = 180;
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				time--;
				lblTimer.setText(String.format("%02d:%02d", time / 60, time % 60));
				if (time == 0) {
					final Timer timer = (Timer) e.getSource();
					timer.stop();
					JOptionPane.showMessageDialog(contentPane, "Time's up.");
				}
			}
			
		});
		
	    timer.start();
		
		return lblTimer;
	}

	private JLabel labelMessage() {
		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(408, 236, 230, 81);
		return lblMessage;
	}

	private JLabel labelYourName() {		
		JLabel label = new JLabel("");
		label.setBounds(49, 11, 46, 14);
		return label;
	}

	private JLabel labelName() {
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 46, 14);
		return lblName;
	}

	private JLabel labelTime() {
		JLabel lblTime = new JLabel("TIME");
		lblTime.setBounds(327, 286, 46, 14);
		return lblTime;
	}

	private JLabel labelScore() {
		JLabel lblScore = new JLabel("SCORE");
		lblScore.setBounds(327, 236, 46, 14);
		return lblScore;
	}

	private JButton buttonClear() {
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBounds(570, 168, 71, 40);
		return btnClear;
	}

	private JButton buttonLastWord() {
		JButton btnLastWord = new JButton("Last Word");
		btnLastWord.setBounds(489, 168, 71, 40);
		return btnLastWord;
	}

	private JButton buttonEnter() {
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(408, 168, 71, 40);
		return btnEnter;
	}

	private JButton buttonTwist() {
		JButton btnTwist = new JButton("Twist");
		btnTwist.setBounds(327, 168, 71, 40);
		return btnTwist;
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		
		JMenuItem File_NewGame = new JMenuItem("New Game");
		JMenuItem File_Open = new JMenuItem("Open");
		JMenuItem File_Save = new JMenuItem("Save");
		JMenuItem File_Exit = new JMenuItem("Exit");
		
		menuFile.add(File_NewGame);
		menuFile.add(File_Open);
		menuFile.add(File_Save);
		menuFile.add(File_Exit);
		
		JMenu menuOptions = new JMenu("Options");
		
		JMenuItem Options_UserRanks = new JMenuItem("User Ranks");
		JMenuItem Options_ChangBackground = new JMenuItem("Change Background");
		
		menuOptions.add(Options_UserRanks);
		menuOptions.add(Options_ChangBackground);
		
		menuBar.add(menuFile);
		menuBar.add(menuOptions);
		
		return menuBar;
	}
}

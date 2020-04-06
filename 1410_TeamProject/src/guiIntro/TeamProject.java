package guiIntro;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;

public class TeamProject extends JFrame {
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamProject frame = new TeamProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeamProject() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 402);
		
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
		
		String name = JOptionPane.showInputDialog("Please enter your name below:");
		label.setText(name);
		
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
		lblTime.setBounds(198, 283, 46, 14);
		return lblTime;
	}

	private JLabel labelScore() {
		JLabel lblScore = new JLabel("SCORE");
		lblScore.setBounds(198, 233, 46, 14);
		return lblScore;
	}

	private JButton buttonClear() {
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBounds(462, 169, 71, 40);
		return btnClear;
	}

	private JButton buttonLastWord() {
		JButton btnLastWord = new JButton("Last Word");
		btnLastWord.setBounds(381, 169, 71, 40);
		return btnLastWord;
	}

	private JButton buttonEnter() {
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(300, 169, 71, 40);
		return btnEnter;
	}

	private JButton buttonTwist() {
		JButton btnTwist = new JButton("Twist");
		btnTwist.setBounds(219, 169, 71, 40);
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
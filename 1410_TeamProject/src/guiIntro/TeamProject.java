package guiIntro;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI application
 * @author Khoi Nguyen
 *
 */
public class TeamProject extends JFrame{
	
	private JPanel contentPane;
	private LinkedHashMap<String, Boolean> gameWords;
	private String[] roundSixLetterWords;
	private List<String> allSixLetterWords;
	private ArrayList<JLabel> guessedWords;
	private JLabel[] guessLetters;
	private JLabel[] possibleLetters;
	private final int WORD_SIZE = 6;
	private List<Character> gameWordChars;
	private List<Character> guessWordChars;
	private JLabel lblTimer;
	private Random randNum;
	
	Timer timer;

    
	/**
	 * Create the frame.
	 */
	public TeamProject(List<String> sixLetterWords) {
		this.allSixLetterWords = sixLetterWords;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 402);
		
		randNum = new Random();
		
		JMenuBar menuBar = menuBar();
		setJMenuBar(menuBar);
		gameWordChars = new ArrayList<Character>();
		guessWordChars = new ArrayList<Character>();
		
		contentPane = new JPanel();
		contentPane.setFocusable(true);
		contentPane.requestFocusInWindow();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(255, 211, 1));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				// Move letter from possible label to guess label if it is one of the possible letter.
				if (gameWordChars.contains(e.getKeyChar())) {
					moveLettersUp(e);
				}
				// Sends last letter from guess label back down to possible label.
				else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (!guessWordChars.isEmpty()) {
						char temp = guessWordChars.get(guessWordChars.size() - 1);
						guessWordChars.remove(guessWordChars.size() - 1);
						gameWordChars.add(temp);
						updateCharacterLabels();
					}
				}
			}
			private void moveLettersUp(KeyEvent e) {
				gameWordChars.remove((Character)e.getKeyChar());
				guessWordChars.add(e.getKeyChar());
				updateCharacterLabels();
			}
		});
		
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
		
		lblTimer = labelTimer();
		contentPane.add(lblTimer);
		
		possibleLetters = labelPossibleLetters();
		for (JLabel lbl : possibleLetters) {
			contentPane.add(lbl);
		}
		
		guessLetters = labelGuessLetters();
		for (JLabel lbl : guessLetters) {
			contentPane.add(lbl);
		}
		
		newRound();
		
		//String name = JOptionPane.showInputDialog("Please enter your name below:");
		//label.setText(name);
	}
	
	/**
	 * @author Michael Kamerath
	 * @return list of labels of the letters in the guess word
	 */
	private JLabel[] labelPossibleLetters() {
		JLabel[] lblPossibleLetters = new JLabel[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i) {
			lblPossibleLetters[i] = new JLabel("");
			lblPossibleLetters[i].setBounds(40*i+375, 110, 40, 40);
		}
		return lblPossibleLetters;
	}
	
	/**
	 * @author Michael Kamerath
	 * @return list of possible guess letters
	 */
	private JLabel[] labelGuessLetters() {
		JLabel[] lblGuessLetters = new JLabel[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i) {
			lblGuessLetters[i] = new JLabel("");
			lblGuessLetters[i].setBounds(40*i+375, 40, 40, 40);
		}
		
		return lblGuessLetters;
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
					boolean goToNextRound = false;
					for (String word : roundSixLetterWords) {
						if (gameWords.get(word)) {
							goToNextRound = true;
							break;
						}
					}
					if (goToNextRound) {
						newRound();
					}
					else {
						JOptionPane.showMessageDialog(contentPane, "Time's up.");
					}
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

	/**
	 * @author Michael Kamerath Khoi Nguyen
	 * @return Twist Button
	 */
	private JButton buttonTwist() {
		JButton btnTwist = new JButton("Twist");
		btnTwist.setBounds(327, 168, 71, 40);
		btnTwist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Collections.shuffle(gameWordChars);
				for (int i = 0; i < gameWordChars.size(); ++i) {
					possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
				}
				contentPane.requestFocusInWindow();
			}
		});
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
	
	/**
	 * @author Michael Kamerath
	 */
	private void newRound() {
		// Resetting variables
		gameWordChars.clear();
		
		
		int position = this.randNum.nextInt(this.allSixLetterWords.size());
		String gameWord = allSixLetterWords.get(position);
		System.out.println(gameWord);
		for (int i = 0; i < gameWord.length(); ++i) {
			gameWordChars.add(gameWord.charAt(i));
		}
		Collections.shuffle(gameWordChars);
		
		for (int i = 0; i < gameWordChars.size(); ++i) {
			possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
		}
		
	}
	
	/**
	 * @author Michael Kamerath
	 */
	private void updateCharacterLabels() {
		for (int i = 0; i < guessWordChars.size(); ++i) {
			guessLetters[i].setIcon(new ImageIcon("Resources/letters/" + guessWordChars.get(i) + ".png"));
		}
		
		for (int i = guessWordChars.size(); i < gameWordChars.size() + guessWordChars.size(); ++i) {
			guessLetters[i].setIcon(null);
		}
		
		for (int i = 0; i < gameWordChars.size(); ++i) {
			possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
		}
		
		for (int i = gameWordChars.size(); i < gameWordChars.size() + guessWordChars.size(); ++i) {
			possibleLetters[i].setIcon(null);
		}
	}
}

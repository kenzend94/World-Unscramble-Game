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
	private final int WORD_SIZE = 6;
	private static final int SCORE_THRESHOLD = 170; 	// [TAG] - Khoi Nguyen

	private JPanel contentPane;
	private JLabel[] guessLetters;
	private JLabel[] possibleLetters;
	private JLabel lblTimer;
	private String[] roundSixLetterWords;
	private List<String> allSixLetterWords;
	private List<String> correctAnswers;				// [TAG] - Khoi Nguyen
	private LinkedHashMap<String, Boolean> gameWords;
	private ArrayList<JLabel> guessedWords;
	private List<Character> gameWordChars;
	private List<Character> guessWordChars;
	private Random randNum;
	private String answer;	 							// [TAG] - Khoi Nguyen
	private int score;									// [TAG] - Khoi Nguyen
	private static int previousTime;					// [TAG] - Khoi Nguyen
	private List<Character> lastWord;					// [TAG] - Khoi Nguyen
	
	
	Timer timer;

    
	/**
	 * Create the frame.
	 */
	public TeamProject(List<String> sixLetterWords) {
		this.allSixLetterWords = sixLetterWords;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 583);
		
		randNum = new Random();
		
		JMenuBar menuBar = menuBar();
		setJMenuBar(menuBar);
		gameWordChars = new ArrayList<Character>();
		guessWordChars = new ArrayList<Character>();
		lastWord = new ArrayList<Character>(); 			// [TAG] - Khoi Nguyen
		correctAnswers = new ArrayList<String>();		// [TAG] - Khoi Nguyen
		
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
		
		timer.stop();
		String name = JOptionPane.showInputDialog("Please enter your name below:");
		label.setText(name);
		timer.start();
	}
	
	
	/**
	 * @author Michael Kamerath
	 * @return list of labels of the letters in the guess word
	 */
	private JLabel[] labelPossibleLetters() {
		JLabel[] lblPossibleLetters = new JLabel[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i) {
			lblPossibleLetters[i] = new JLabel("");
			lblPossibleLetters[i].setBounds(60*i+375, 220, 40, 40);
			//lblPossibleLetters[i].setBounds(40*i+375, 110, 40, 40);
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
			lblGuessLetters[i].setBounds(60*i+375, 40, 40, 40);
			//lblGuessLetters[i].setBounds(40*i+375, 40, 40, 40);
		}
		
		return lblGuessLetters;
	}

	private JLabel labelTimer() {
		JLabel lblTimer = new JLabel("03:00");
		lblTimer.setBounds(327, 473, 46, 14);
		
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
		lblMessage.setBounds(460, 308, 230, 81);
		return lblMessage;
	}

	private JLabel labelYourName() {		
		JLabel label = new JLabel("");
		label.setBounds(49, 11, 96, 14);
		return label;
	}

	private JLabel labelName() {
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 11, 46, 14);
		return lblName;
	}

	private JLabel labelTime() {
		JLabel lblTime = new JLabel("TIME");
		lblTime.setBounds(327, 448, 46, 14);
		return lblTime;
	}

	/**
	 * @author Khoi Nguyen
	 * @return the score 
	 */
	private JLabel labelScore() {
		JLabel lblScore = new JLabel("SCORE");
		lblScore.setBounds(327, 398, 100, 14);
		Timer t = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblScore.setText("SCORE   " + String.valueOf(score));	
			}
		});
		t.start();
		return lblScore;
	}

	/**
	 * @author Khoi Nguyen
	 * @return clear the word typed on the GUI.
	 */
	private JButton buttonClear() {
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBounds(641, 312, 87, 40);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < guessWordChars.size(); ++i) 
					guessLetters[i].setIcon(null);

				
				char[] chars = getString(guessWordChars).toCharArray();
				for(char c : chars)
					gameWordChars.add(c); 
				
				guessWordChars.clear();
				for (int i = 0; i < gameWordChars.size(); ++i) 
					possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
				
				contentPane.requestFocusInWindow();
			}
			
		});
		return btnClear;
	}

	/**
	 * @author Khoi Nguyen
	 * @return the last word typed
	 */
	private JButton buttonLastWord() {
		JButton btnLastWord = new JButton("Last Word");
		btnLastWord.setBounds(516, 312, 104, 40);
		btnLastWord.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!guessWordChars.isEmpty())
				{
					JOptionPane.showMessageDialog(contentPane, "Can't show the last word until the guessing area is clear!", "Warning:", JOptionPane.ERROR_MESSAGE);
					contentPane.requestFocusInWindow();
					return;
				}
				
				if(lastWord.isEmpty())
				{
					JOptionPane.showMessageDialog(contentPane, "You have not taken any guess!", "Warning:", JOptionPane.ERROR_MESSAGE);
					contentPane.requestFocusInWindow();
					return;
				}
				
				for (int i = 0; i < gameWordChars.size(); ++i) 
					possibleLetters[i].setIcon(null);

				
				char[] chars = getString(lastWord).toCharArray();
				for(char c : chars)
					guessWordChars.add(c); 
				
				gameWordChars.clear();
				
				for (int i = 0; i < guessWordChars.size(); ++i) 
					guessLetters[i].setIcon(new ImageIcon("Resources/letters/" + guessWordChars.get(i) + ".png"));
				
				contentPane.requestFocusInWindow();
			}
			
		});
		return btnLastWord;
	}

	/**
	 * @author Khoi Nguyen
	 * @return the right or wrong of the guess word.
	 */
	private JButton buttonEnter() {
		JButton btnEnter = new JButton("Enter");
		btnEnter.setBounds(421, 312, 71, 40);
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(guessWordChars.toString());
				if (guessWordChars.size() != WORD_SIZE)
				{
					JOptionPane.showMessageDialog(contentPane, "Guess again!", "Result:", JOptionPane.INFORMATION_MESSAGE);
					contentPane.requestFocusInWindow();
					return;
				}
				
				if(getString(guessWordChars).equals(answer))
				{
					// Output score
					score += calculateScoreBasedOnTime(lblTimer.getText());
					System.out.println(score);
					
					correctAnswers.add(answer);
					for(int i = 0; i < correctAnswers.size(); i++)
					{
						System.out.println(correctAnswers.get(i));
						String ans = correctAnswers.get(i);
						char[] chars = ans.toCharArray();
						for(int j = 0; j < chars.length; j++)
						{
							JLabel label = new JLabel("");
							label.setBounds(42*j + 20, 475 - 43*i, 40, 40);
							label.setIcon(new ImageIcon("Resources/letters/" + chars[j] + ".png"));
							contentPane.add(label);
							contentPane.repaint();
							System.out.println(chars[j] + "\n");
						}
					}
					
					JOptionPane.showMessageDialog(contentPane, "Correct word!", "Result:", JOptionPane.INFORMATION_MESSAGE);
					// Advance to the next word
					newRound();
				}
				else
				{
					lastWord.clear();
					for (int i = 0; i < guessWordChars.size(); ++i) 
						guessLetters[i].setIcon(null);
					
					char[] chars = getString(guessWordChars).toCharArray();
					for(char c : chars)
					{
						gameWordChars.add(c); 
						lastWord.add(c);
					}
					Collections.shuffle(gameWordChars);
					guessWordChars.clear();
					
					for (int i = 0; i < gameWordChars.size(); ++i) 
						possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
					
					
					JOptionPane.showMessageDialog(contentPane, "Guess again!", "Result:", JOptionPane.INFORMATION_MESSAGE);
				}
				contentPane.requestFocusInWindow();
			}
		});
		return btnEnter;
	}

	/**
	 * @author Michael Kamerath & Khoi Nguyen
	 * @return Twist Button
	 */
	private JButton buttonTwist() {
		JButton btnTwist = new JButton("Twist");
		btnTwist.setBounds(327, 312, 71, 40);
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
		lastWord.clear(); 				// [TAG] - Khoi Nguyen
		guessWordChars.clear(); 		// [TAG] - Khoi Nguyen
		
		int position = this.randNum.nextInt(this.allSixLetterWords.size());
		answer = allSixLetterWords.get(position);					// [TAG] - Khoi Nguyen
		System.out.println(answer);									// [TAG] - Khoi Nguyen
		for (int i = 0; i < answer.length(); ++i) {					// [TAG] - Khoi Nguyen
			gameWordChars.add(answer.charAt(i));					// [TAG] - Khoi Nguyen
		}
		Collections.shuffle(gameWordChars);
		
		for (int i = 0; i < gameWordChars.size(); ++i) {
			possibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
		}
		
		for (int i = 0; i < WORD_SIZE; ++i) {
			guessLetters[i].setIcon(null);
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
	
	/**
	 * @author Khoi Nguyen
	 * @param list
	 * @return
	 */
	private static String getString(List<Character> list)
	{    
	    StringBuilder builder = new StringBuilder(list.size());
	    for(Character ch: list)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}
	
	/**
	 * @author Khoi Nguyen
	 * @param time
	 * @return
	 */
	private static int convertTimeToSeconds(String time)
	{
		String[] comps = time.split(":");
		int min = Integer.parseInt(comps[0]);
		return min * 60 + Integer.parseInt(comps[1]);
	}
	
	/**
	 * @author Khoi Nguyen
	 * @param timeS
	 * @return
	 */
	private static int calculateScoreBasedOnTime(String timeS)
	{
		int time = convertTimeToSeconds(timeS);
		
		int timeSpentToSolve;
		if (previousTime == 0)
			timeSpentToSolve = SCORE_THRESHOLD - time;
		else
			timeSpentToSolve = previousTime - time;
		previousTime = time;
		
		if (timeSpentToSolve < 10)
			return 100;
		else if (timeSpentToSolve < 15)
			return 90;
		else if (timeSpentToSolve < 25)
			return 70;
		else if (timeSpentToSolve < 45)
			return 50;
		else if (timeSpentToSolve < 70)
			return 25;
		return 0;
	}
	
}
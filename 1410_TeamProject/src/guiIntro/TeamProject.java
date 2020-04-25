package guiIntro;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * GUI application
 * @author Khoi Nguyen Michael Kamerath
 *
 */
public class TeamProject extends JFrame{
	private final int WORD_SIZE = 6;
	private final int MAX_TIME = 45;

	private JPanel contentPane;
	private JLabel[] lblGuessLetters;
	private JLabel[] lblPossibleLetters;
	private JLabel lblTimer;
	private JLabel lblScore;
	private ArrayList<String> roundSixLetterWords;
	private List<String> allSixLetterWords;
	private LinkedHashMap<String, Boolean> gameWords;
	private ArrayList<JLabel> lblGuessedWords;
	private List<Character> gameWordChars;
	private List<Character> guessWordChars;
	private Random randNum;
	private int score;									// [TAG] - Khoi Nguyen
	private String lastWord;							// [TAG] - Khoi Nguyen
	
	
	Timer timer;

    
	/**
	 * Create the frame.
	 */
	public TeamProject(List<String> sixLetterWords) {
		this.allSixLetterWords = sixLetterWords;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 50, 754, 750);
		
		randNum = new Random();
		
		JMenuBar menuBar = menuBar();
		setJMenuBar(menuBar);
		lblGuessedWords = new ArrayList<JLabel>();
		roundSixLetterWords = new ArrayList<String>();
		gameWords = new LinkedHashMap<String, Boolean>();
		gameWordChars = new ArrayList<Character>();
		guessWordChars = new ArrayList<Character>();
		lastWord = new String("");
		
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
						updateLabels();
					}
				}
				// Enter either puts the last word up or guesses with the current letters
				else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (guessWordChars.isEmpty()) {
						lastWordAction();
					}
					else {
						checkWordGuess();
					}
				}
				// Space scrambles the letters for when someone needs a different order of letters.
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					twistLetters();
				}
			}
			private void moveLettersUp(KeyEvent e) {
				gameWordChars.remove((Character)e.getKeyChar());
				guessWordChars.add(e.getKeyChar());
				updateLabels();
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
		
		lblScore = labelScore();
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
				
		lblPossibleLetters = createPossibleLettersLbl();
		for (JLabel lbl : lblPossibleLetters) {
			contentPane.add(lbl);
		}
		
		lblGuessLetters = createGuessLettersLbl();
		for (JLabel lbl : lblGuessLetters) {
			contentPane.add(lbl);
		}
		
				
		String name = JOptionPane.showInputDialog("Please enter your name below:");
		label.setText(name);
		
		newRound();
	}
	
	
	/**
	 * @author Michael Kamerath
	 * @return list of labels of the letters in the guess word
	 */
	private JLabel[] createPossibleLettersLbl() {
		JLabel[] lbllblPossibleLetters = new JLabel[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i) {
			lbllblPossibleLetters[i] = new JLabel("");
			lbllblPossibleLetters[i].setBounds(60*i+375, 220, 40, 40);
		}
		return lbllblPossibleLetters;
	}
	
	/**
	 * @author Michael Kamerath
	 * @return list of possible guess letters
	 */
	private JLabel[] createGuessLettersLbl() {
		JLabel[] lblGuessLetters = new JLabel[WORD_SIZE];
		for (int i = 0; i < WORD_SIZE; ++i) {
			lblGuessLetters[i] = new JLabel("");
			lblGuessLetters[i].setBounds(60*i+375, 40, 40, 40);
			lblGuessLetters[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			lblGuessLetters[i].setBackground(Color.WHITE);
			lblGuessLetters[i].setOpaque(true);
			
		}
		
		return lblGuessLetters;
	}
	
	/**
	 * Creates labels for each of the blank words that are filled in as
	 * they are guessed. Called at the beginning of each new round.
	 * 
	 * @author Michael Kamerath
	 * @return labels for the blank spaces on the left side of the game.
	 */
	private ArrayList<JLabel> createGuessedWordsLbl() {
		ArrayList<JLabel> result = new ArrayList<JLabel>();
		int additionalX = 40;
		int additionalY = 0;
		for (int i = 0; i < gameWords.size(); ++i) {
			JLabel lbl = new JLabel();
			if (i % 2 == 0) {
				additionalY += 15;
				additionalX = 0;
			}
			else {
				additionalX = 100;
			}
			lbl.setBounds(20 + additionalX, 30 + additionalY, 60, 15);
			lbl.setText("Thing");
			result.add(lbl);
		}
		return result;
	}

	private JLabel labelTimer() {
		JLabel lblTimer = new JLabel("");
		lblTimer.setBounds(327, 473, 46, 14);		
		return lblTimer;
	}
	
	/**
	 * @author Michael Kamerath Khoi Nyugen
	 * @return A timer for the round.
	 */
	private Timer createRoundTimer() {
		Timer timer = new Timer(1000, new ActionListener() {
			int time = MAX_TIME;
			@Override
			public void actionPerformed(ActionEvent e) {
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
						JOptionPane.showMessageDialog(contentPane, "Game Over");
						newGame();
					}
				}
			}
			
		});
		return timer;
	}

	private JLabel labelMessage() {
		JLabel lblMessage = new JLabel("");
		lblMessage.setBounds(460, 308, 230, 81);
		return lblMessage;
	}

	private JLabel labelYourName() {		
		JLabel label = new JLabel("");
		label.setBounds(49, 11, 150, 14);
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
		return lblScore;
	}

	/**
	 * @author Khoi Nguyen Michael Kamerath
	 * @return clear the word typed on the GUI.
	 */
	private JButton buttonClear() {
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBounds(641, 312, 87, 40);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameWordChars.addAll(guessWordChars);
				guessWordChars.clear();
				updateLabels();
				
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
				lastWordAction();
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
				checkWordGuess();
				
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
				twistLetters();
			}
		});
		return btnTwist;
	}

	private JMenuBar menuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		
		JMenuItem File_NewGame = new JMenuItem("New Game");
		File_NewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
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
	 * Called every time a new round starts. Picks a random six letter word and starts a round
	 * based on that six letter word.
	 * 
	 * @author Michael Kamerath
	 */
	private void newRound() {
		for (JLabel lbl : lblGuessedWords) {
			lbl.getParent().remove(lbl);
		}
		timer = createRoundTimer();
		// Resetting variables
		roundSixLetterWords.clear();
		gameWordChars.clear();
		gameWords.clear();
		lastWord = "";
		guessWordChars.clear(); 		// [TAG] - Khoi Nguyen
		
		int position = this.randNum.nextInt(this.allSixLetterWords.size());
		String gameWord = allSixLetterWords.get(position);
		for (int i = 0; i < gameWord.length(); ++i) {
			gameWordChars.add(gameWord.charAt(i));
		}
		Collections.shuffle(gameWordChars);
		
		try (Scanner inFile = new Scanner(new FileReader("../.textfiles/" + gameWord + ".txt"))) {
			String line = null;
			while (inFile.hasNextLine()) {
				line = inFile.nextLine();
				gameWords.put(line, false);
				if (line.length() == WORD_SIZE) {
					roundSixLetterWords.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		lblGuessedWords = createGuessedWordsLbl();
		for (JLabel lbl : lblGuessedWords) {
			contentPane.add(lbl);
		}
		
		updateLabels();
		contentPane.validate();
		contentPane.repaint();
		timer.start();
		
	}
	
	private void newGame() {
		score = 0;
		newRound();
	}
	
	/**
	 * Updates labels with the newest information based on input happening in the game.
	 * Updated labels include lblGuessLetters, lblPossibleLetters, and score.
	 * 
	 * @author Michael Kamerath
	 */
	private void updateLabels() {
		for (int i = 0; i < guessWordChars.size(); ++i) {
			lblGuessLetters[i].setIcon(new ImageIcon("Resources/letters/" + guessWordChars.get(i) + ".png"));
		}
		
		for (int i = guessWordChars.size(); i < gameWordChars.size() + guessWordChars.size(); ++i) {
			lblGuessLetters[i].setIcon(null);
		}
		
		for (int i = 0; i < gameWordChars.size(); ++i) {
			lblPossibleLetters[i].setIcon(new ImageIcon("Resources/letters/" + gameWordChars.get(i) + ".png"));
		}
		
		for (int i = gameWordChars.size(); i < gameWordChars.size() + guessWordChars.size(); ++i) {
			lblPossibleLetters[i].setIcon(null);
		}
		
		lblScore.setText("SCORE: " + score);
		
		if (lblGuessedWords.size() == 0) {
			return;
		}
		int position = 0;
		for (String key : gameWords.keySet()) {
			if (gameWords.get(key)) {
				lblGuessedWords.get(position).setText(key);
			}
			else {
				StringBuilder result = new StringBuilder();
				for (int i = 0; i < key.length(); ++i) {
					result.append("_ ");
				}
				lblGuessedWords.get(position).setText(result.toString());
				
			}
			++position;
		}
	}
	
	/**
	 * Converts a String of time into an int.
	 * 
	 * @author Khoi Nguyen
	 * @param time a String representation of the time
	 * @return the time as an integer
	 */
	private static int convertTimeToSeconds(String time)
	{
		String[] comps = time.split(":");
		int min = Integer.parseInt(comps[0]);
		return min * 60 + Integer.parseInt(comps[1]);
	}
	
	/**
	 * Updates the score based on the length of the word guessed.
	 * 3 letter word is 90 points.
	 * 4 letter word is 160 points.
	 * 5 letter word is 250 points.
	 * 6 letter word is 360 points.
	 * @param word The correctly guessed word
	 */
	private void updateScoreBasedOnWordLength(String word) {
		score += word.length() * word.length() * 10;
	}
	
	/**
	 * @author Khoi Nguyen Michael Kamerath
	 * @param timeS
	 */
	private void calculateScoreBasedOnTime(String timeS)
	{
		int time = convertTimeToSeconds(timeS);
		
		int timeSpentToSolve = this.MAX_TIME - time;
		
		if (timeSpentToSolve < 60)
			score += 2500;
		else if (timeSpentToSolve < 90)
			score += 2000;
		else if (timeSpentToSolve < 120)
			score += 1500;
		else if (timeSpentToSolve < 150)
			score += 1000;
		else
			score += 500;
	}
	
	/**
	 * Checks whether the guessed word at top is in the list. Updates labels accordingly.
	 * @author Michael Kamerath
	 */
	private void checkWordGuess() {
		String guessedWord = guessWordChars.stream().map(String::valueOf).collect(Collectors.joining());
		if (gameWords.containsKey(guessedWord)) {
			if (!gameWords.get(guessedWord)) {
				gameWords.put(guessedWord, true);
				updateScoreBasedOnWordLength(guessedWord);
				lastWord = guessedWord;
			}
			else {
				lastWord = "";
			}
		}
		gameWordChars.addAll(guessWordChars);
		guessWordChars.clear();
		updateLabels();
		contentPane.requestFocusInWindow();
	}
	
	/**
	 * Mixes up the game letters to avoid getting stuck.
	 * @author Michael Kamerath
	 */
	private void twistLetters() {
		Collections.shuffle(gameWordChars);
		updateLabels();
		contentPane.requestFocusInWindow();
	}
	
	/**
	 * Moves the last word back up if it was a correct word.
	 * @author Michael Kamerath
	 */
	private void lastWordAction() {
		for (int i = 0; i < lastWord.length(); ++i) {
			guessWordChars.add((Character)lastWord.charAt(i));
			gameWordChars.remove((Character)lastWord.charAt(i));
		}
		updateLabels();
		contentPane.requestFocusInWindow();
		lastWord = "";
	}
	
}
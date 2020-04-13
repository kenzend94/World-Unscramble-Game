package guiIntro;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * @author Michael Kamerath
 *
 */
public class WordFiles {
	private List<String> words;
	private TreeSet<String> gameWords;
	private List<String> results;
	
	/**
	 * Constructor for WordFiles to create a lot of word file to be used in the game.
	 * 
	 * @param filename a filename to find all of the words from
	 */
	WordFiles(String filename) {
		words = new LinkedList<String>();
		gameWords = new TreeSet<String>();
		results = new LinkedList<String>();
		getGameWordsFromTextFile(filename);
		createWordFiles();
	}
	
	/**
	 * Gets all game words from the specified text file.
	 * 
	 * @param filename name of file to get all words from.
	 */
	public void getGameWordsFromTextFile(String filename) {
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.forEach(x -> {x.replaceAll("[^A-Za-z0-9]",""); if (x.length() <= 6) words.add(x.toLowerCase());});
		} catch (IOException e) {
			System.out.print(e);
		}
	}
	
	/**
	 * Creates word files for each 6 letter word in game list.
	 * Each file contains all words that can be created with the 6 letters.
	 */
	public void createWordFiles() {
		for (String word : words) {
			if (word.length() == 6) {
				permuteString(word);
				LinkedList<String> sortedGameWords = new LinkedList<String>();
				sortedGameWords.addAll(gameWords);
				Collections.sort(sortedGameWords, (string1, string2) -> {
					if (string1.toString().length() != string2.toString().length()) {
						return string1.toString().length() - string2.toString().length();
					}
					else {
						return string1.compareTo(string2);
					}
				});
				try {
					FileWriter txtFile = new FileWriter("../.textfiles/" + word + ".txt");
					for (String gameWord : sortedGameWords) {
						txtFile.write(gameWord + "\n");
					}
					txtFile.flush();
					txtFile.close();
				}
				catch (IOException e) {
					System.out.print(e);
				}
			}
			gameWords.clear();
			results.clear();
		}
	}
	
	private void permuteString(String word) {
		combine(new StringBuilder(word), new StringBuilder(), 0);
		for (String substringWord : results) {
			permutation("", substringWord);
			if (words.contains(substringWord)) {
				gameWords.add(substringWord);
			}
		}
	}
	
	/**
	 * Modified from: http://www.mytechinterviews.com/combinations-of-a-string
	 */
	private void combine(StringBuilder in, StringBuilder out, int index) {
		for (int i = index; i < in.length(); ++i) {
			out.append(in.charAt(i));
			if (out.length() >= 3) {
				results.add(out.toString());
			}
			combine(in, out, i + 1);
			out.deleteCharAt(out.length() - 1);
		}
	}
	
	/**
	 * Modified from: https://stackoverflow.com/questions/4240080/generating-all-permutations-of-a-given-string
	 */
	private void permutation(String prefix, String word) {
		if (word.length() == 0) {
			if (words.contains(prefix)) {
				gameWords.add(prefix);
			}
		}
		else {
			for (int i = 0; i < word.length(); ++i) {
				permutation(prefix + word.charAt(i), word.substring(0, i) + word.substring(i+1, word.length()));
			}
		}
	}
	
	/**
	 * Gets the list of words after they have been properly
	 * trimmed.
	 * 
	 * @return the list of all words
	 */
	public List<String> getWords() {
		return this.words;
	}

}

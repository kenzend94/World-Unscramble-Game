package guiIntro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Michael Kamerath
 *
 */
public class WordFiles {
	public List<String> words;
	
	WordFiles(String filename) {
		words = new LinkedList<String>();
		getGameWordsFromTextFile(filename);
	}
	
	/**
	 * Gets all game words from the specified text file.
	 * 
	 * @param filename name of file to get all words from.
	 */
	public void getGameWordsFromTextFile(String filename) {
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.forEach(x -> {x.replaceAll("[^A-Za-z0-9]",""); if (x.length() <= 6) words.add(x);});
		} catch (IOException e) {
			System.out.print(e);
		}
	}
	
	/**
	 * Creates word files for each 6 letter word in game list.
	 * Each file contains all words that can be created with the 6 letters.
	 */
	public void createWordFiles() {
		
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

package guiIntro;

import java.awt.EventQueue;
import java.util.List;

public class Driver {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		WordFiles files = new WordFiles("../.textfiles/2of12inf.txt", false);
		List<String> sixLetterWords = files.getSixLetterWords();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamProject frame = new TeamProject(sixLetterWords);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

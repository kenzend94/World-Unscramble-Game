package guiIntro;

import java.awt.EventQueue;

public class Driver {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		WordFiles files = new WordFiles("../.textfiles/2of12inf.txt");
		System.out.println(files.getWords());
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

}

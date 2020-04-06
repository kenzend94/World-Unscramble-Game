package guiIntro;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
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

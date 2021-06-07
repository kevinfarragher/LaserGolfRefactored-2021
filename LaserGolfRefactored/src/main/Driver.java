package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Initializes the program.
 */
public class Driver {
	
	//Driver's frame
	public JFrame frame;
	
	/**
	 * Used to create a driver instance and initialize the program.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Driver::new);
	}
	
	/**
	 * Constructs a driver that initializes the program's frame.
	 */
	public Driver() {
		frame=new JFrame(); //program's frame
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GameManager mgr = new GameManager(this);
		frame.setContentPane(mgr.mainMenu);
		
		frame.pack();
		frame.setVisible(true);
		frame.setTitle("Laser Golf");	
	}
}

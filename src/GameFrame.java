

import java.io.IOException;

import javax.swing.JFrame;


public class GameFrame extends JFrame {
	
	public GameFrame() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("AMONG FIGHTER - VENOX");
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1440, 900);
		setLocationRelativeTo(null); // frame will be in middle of the screen 
		
		Board board = new Board();
		add(board);
		setVisible(true);
	}
}

// import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;


public class SplashScreen extends JWindow{
	private JLabel label = new JLabel();
	
	public SplashScreen() {
		setSize(1350,900);
		String img = "Splash.png";
		ImageIcon icon = new ImageIcon(img);
		label.setIcon(icon);
		this.add(label);
		setLocationRelativeTo(null);
		setVisible(true);
		try {
			Thread.sleep(5000);
			setVisible(false);
			dispose();
			new GameFrame();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SplashScreen();
	}

}
import javax.swing.JFrame;

public class GameFrame extends JFrame{
	GameFrame() {
		this.add(new GamePanel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Snake Game");
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}

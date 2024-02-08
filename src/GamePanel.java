// add:
// start game button: DONE
// more music options: DONE
// snake color change every 10 apples: DONE
// difficulty modes: DONE
// sound effects
// restart button
// high scores

import java.awt.Color;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int x[] = new int[GAME_UNITS];
	static final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int DELAY;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	public boolean running = true;
	boolean modeChosen = false;
	Timer timer;
	Random rand;
	JButton playButton;
	JButton easyButton;
	JButton normalButton;
	JButton hardButton;
	String filePath;
	
	
	GamePanel() {
		filePath = "video-game-points-lost-retro-glitchedtones-1-00-01.wav";
		playButton = new JButton("Play");
		this.add(playButton);
		playButton.setVisible(false);
		
		easyButton = new JButton("Easy");
		this.add(easyButton);
		easyButton.setVisible(false);
		
		normalButton = new JButton("Normal");
		this.add(normalButton);
		normalButton.setVisible(false);
		
		hardButton = new JButton("Hard");
		this.add(hardButton);
		hardButton.setVisible(false);
		
		
		rand = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(37, 23, 36));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		
		playButton.setVisible(true);
		playButton.addActionListener(this);
		
		easyButton.setVisible(true);
		easyButton.addActionListener(this);
		
		normalButton.setVisible(true);
		normalButton.addActionListener(this);
		
		hardButton.setVisible(true);
		hardButton.addActionListener(this);
	}
	
	
	public void startGame() {
		newApple();
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		playButton.setBounds(140, 215, 320, 50); // playButton x, y, width, height
		playButton.setBackground(new Color(131, 170, 214)); // play button color
		playButton.setFont(new Font("Impact", Font.PLAIN, 25));
		
		easyButton.setBounds(140, 300, 100, 50); // easyButton x, y, width, height
		easyButton.setBackground(new Color(98, 152, 209)); // easy mode button color
		easyButton.setFont(new Font("Impact", Font.PLAIN, 20));
		
		normalButton.setBounds(250, 300, 100, 50); // normalButton x, y, width, height
		normalButton.setBackground(new Color(98, 152, 209)); // normal mode button color
		normalButton.setFont(new Font("Impact", Font.PLAIN, 20));
		
		hardButton.setBounds(360, 300, 100, 50); // hardButton x, y, width, height
		hardButton.setBackground(new Color(98, 152, 209)); // hard mode button color
		hardButton.setFont(new Font("Impact", Font.PLAIN, 20));
		
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
				//g.setColor(new Color(47, 38, 53));
				//g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				//g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			
			g.setColor(new Color(153, 15, 2)); //apple color
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			g.setColor(new Color(245, 243, 242)); // apple shine color
			g.fillOval(appleX + 4, appleY + 5, 5, 5);
			
			g.setColor(new Color(150, 75, 0)); // apple stem color
			g.fillRect(appleX + 11, appleY - 3, 3, 8);
			
			
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {
					g.setColor(new Color(211, 60, 50)); // snake head color
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					
					if(direction == 'R') {
						if(applesEaten >= 20) {
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 15, y[i] + 3, 5, 5);
							
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 15, y[i] + 15, 5, 5);						
						}
						
						else {
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 15, y[i] + 3, 5, 5);
							
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 15, y[i] + 15, 5, 5);
						}
					}
					
					if(direction == 'L') {
						if(applesEaten < 20) {
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 3, y[i] + 3, 5, 5);
							
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 3, y[i] + 15, 5, 5);
						}
						
						else if(applesEaten >= 20) {
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 3, y[i] + 3, 5, 5);
							
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 3, y[i] + 15, 5, 5);
						}
					}
					
					if(direction == 'D') {
						if(applesEaten < 20) {
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 15, y[i] + 15, 5, 5);
							
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 3, y[i] + 15, 5, 5);
						}
						
						else if(applesEaten >= 20) {
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 15, y[i] + 15, 5, 5);
							
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 3, y[i] + 15, 5, 5);
						}
						
					}
					
					if(direction == 'U') {
						if(applesEaten < 20) {
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 15, y[i] + 5, 5, 5);
							
							g.setColor(new Color(245, 243, 242)); //eye color
							g.fillOval(x[i] + 3, y[i] + 5, 5, 5);
						}
						
						else if(applesEaten >= 20) {
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 15, y[i] + 5, 5, 5);
							
							g.setColor(Color.BLUE); // blue eye color
							g.fillOval(x[i] + 3, y[i] + 5, 5, 5);
						}
					}
				}
				
				else {
					if(applesEaten < 5 && i % 2  != 0) { // level one snake colors
						g.setColor(new Color(220, 122, 33));
					}
					
					else if(applesEaten < 5 && i % 2  == 0) { // level one snake colors
						g.setColor(new Color(200, 102, 13));
					}
					
					else if(applesEaten >= 5 && applesEaten < 10 && i % 2  != 0) { // level two snake colors
						g.setColor(new Color(73, 81, 90));
					}
					
					else if(applesEaten >= 5 && applesEaten < 10 && i % 2  == 0) { // level two snake colors
						g.setColor(new Color(112, 174, 218));
					}
					
					else if(applesEaten >= 10 && applesEaten < 25 && i % 2  != 0) { // level three snake colors
						g.setColor(new Color(226, 99, 32));
					}
					
					else if(applesEaten >= 10 && applesEaten < 15 && i % 2  == 0) { // level three snake colors
						g.setColor(new Color(195, 45, 25));
					}
					
					else if(applesEaten >= 15 && applesEaten < 25 && i % 2  != 0) { // level four snake colors
						g.setColor(new Color(134, 190, 199));
					}
					
					else if(applesEaten >= 15 && applesEaten < 25 && i % 2  == 0) { // level four snake colors
						g.setColor(new Color(248,217,133));
					}
					
					else if (applesEaten >= 25) { // rainbow colored snake
						g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
					}
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			if(applesEaten > 0) {
				g.setColor(new Color(245, 243, 242)); // score color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Score: " + applesEaten, 10, g.getFont().getSize());
			}
			
			if(applesEaten > 0 && applesEaten < 5) {
				g.setColor(new Color(220, 122, 33)); // "level one" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Level 1", 500, g.getFont().getSize());
			} else if (applesEaten >= 5 && applesEaten < 10) {
				g.setColor(new Color(112, 174, 218)); // "level two" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Level 2", 500, g.getFont().getSize());
			} else if (applesEaten >= 10 && applesEaten < 15) {
				g.setColor(new Color(226, 99, 32)); // "level three" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Level 3", 500, g.getFont().getSize());
			} else if (applesEaten >= 15 && applesEaten < 20) {
				g.setColor(new Color(248,217,133)); // "level four" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Level 4", 500, g.getFont().getSize());
			} else if (applesEaten >= 20 && applesEaten < 25) {
				g.setColor(Color.BLUE); // "level five" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Level 5", 500, g.getFont().getSize());
			} else if (applesEaten >= 25) { 
				g.setColor(new Color(153, 15, 2)); // "godly level" color
				g.setFont(new Font("impact", Font.PLAIN, 30));
				g.drawString("Godly Level", 450, g.getFont().getSize());
			}
		}
		
		else if (!running) {
			gameOver(g);
		}
	}
	
	public void newApple() {
		appleX = UNIT_SIZE * getRandomNumber(1, UNIT_SIZE - 2);
		appleY = UNIT_SIZE * getRandomNumber(1, UNIT_SIZE - 2);
	}
	
	public void move() {
		if(running) {
			for(int i = bodyParts; i > 0; i--) { 
				x[i] = x[i - 1];
				y[i] = y[i - 1];
			}
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
			
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
			
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
			
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if(x[0] == appleX && y[0] == appleY) {
			PlayMusic("Untitled video - Made with Clipchamp.wav");
			applesEaten++;
			newApple(); // change location of apple
			bodyParts++;
		}
	}
	
	public void checkCollisions() {
		for (int i = bodyParts; i > 0; i--) {
			// checks for body collisions
			if(x[i] == x[0] && y[i] == y[0]) {
				PlayMusic(filePath);
				running = false;
			}
			
			// checks for left border collisions
			if(x[i] < 0) {
				PlayMusic(filePath);
				running = false;
			} 
			
			// checks for right border collisions
			if(x[i] > SCREEN_WIDTH) {
				PlayMusic(filePath);
				running = false;
			}
			
			// checks for top border collisions
			if(y[i] < 0) {
				PlayMusic(filePath);
				running = false;
			}
			
			// checks for bottom border collisions
			if(y[i] > SCREEN_HEIGHT) {
				PlayMusic(filePath);
				running = false;
			}
			
			if(!running) {
				timer.stop();
				
			}
		}
	}

	private void PlayMusic(String filePath) {
		try {
			File musicPath = new File(filePath);
			
			if(musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
			}
			
			else {
				System.out.println("Can't fins file");
			}
		}
		
		catch (Exception e) {
			System.out.println(e);
		}
		
	}


	public void gameOver(Graphics g) {
		g.setColor(new Color(244, 67, 54));
		g.setFont(new Font("impact", Font.BOLD, 75)); // game over color
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("Game Over!")) / 2, ((SCREEN_HEIGHT / 2) - 60));
		
		g.setColor(new Color(245, 243, 242)); // game over score color
		g.setFont(new Font("impact", Font.PLAIN, 55));
		metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, ((SCREEN_HEIGHT / 2) + 60));
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == playButton && modeChosen) { // begins the game
			playButton.setVisible(false);
			easyButton.setVisible(false);
			normalButton.setVisible(false);
			hardButton.setVisible(false);
			
			startGame();
		}
		
		if(e.getSource() == easyButton) { // makes snake slower
			modeChosen = true;
			DELAY = 150;
			easyButton.setVisible(false);
			normalButton.setVisible(false);
			hardButton.setVisible(false);
		}
		
		if(e.getSource() == normalButton) { // makes snake delay = 75
			modeChosen = true;
			DELAY = 75;
			easyButton.setVisible(false);
			normalButton.setVisible(false);
			hardButton.setVisible(false);
		}
		
		if(e.getSource() == hardButton) { // makes snake delay = 75
			modeChosen = true;
			DELAY = 50;
			easyButton.setVisible(false);
			normalButton.setVisible(false);
			hardButton.setVisible(false);
		}
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		
		repaint();
		
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
				
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}

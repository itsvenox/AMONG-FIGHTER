

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements GameConstants, PlayerConstants {
	BufferedImage imageBg;
	private RedPlayer redPlayer;
	private BluePlayer bluePlayer;
	private Timer timer;
	private Power redPower;
	private Power bluePower;
	private boolean isGameOver ; 
	
	public Board() throws IOException {
		loadBackgroundImage();
		redPlayer = new RedPlayer();
		bluePlayer = new BluePlayer();
		setFocusable(true);
		bindEvents();
		gameLoop();
		loadPower();
	}
	
	private void loadPower() {
		redPower = new Power(50, "RED".toUpperCase());
		bluePower = new Power(GWIDTH/2+150, "BLUE".toUpperCase());
	}
	
	private void paintPower(Graphics pen) {
		redPower.printBox(pen);
		bluePower.printBox(pen);
	}
	
	public void collision() {
		if(isCollide()) {
			if(redPlayer.isAttacking() && bluePlayer.isAttacking()) {
				bluePlayer.setCurrentMove(DAMAGE);
				bluePower.setHealth();
				redPlayer.setCurrentMove(DAMAGE);
				redPower.setHealth();
			}
			else if(redPlayer.isAttacking()) {
				bluePlayer.setCurrentMove(DAMAGE);
				bluePower.setHealth();
			}
			else if(bluePlayer.isAttacking()) {
				redPlayer.setCurrentMove(DAMAGE);
				redPower.setHealth();
			}
			if(bluePower.getHealth()<=0 || redPower.getHealth()<=0) {
				isGameOver = true;
			}
			redPlayer.setCollide(true);
			redPlayer.setSpeed(0);
			bluePlayer.setCollide(true);
			bluePlayer.setSpeed(0);
		}
		else {
			redPlayer.setSpeed(SPEED);
			bluePlayer.setSpeed(SPEED);
		}
	}
	
	private void printMessage(Graphics pen) {
		pen.setColor(Color.RED);
		pen.setFont(new Font("times",Font.BOLD, 50));
		pen.drawString("Game Over", GWIDTH/2-145, GHEIGHT/2-70);
	}
	
	private boolean isCollide() {
		int xDistance = Math.abs(redPlayer.getX() - bluePlayer.getX());
		int yDistance = Math.abs(redPlayer.getY() - bluePlayer.getY());
		int maxW = Math.max(redPlayer.getW(), bluePlayer.getW());
		int maxH = Math.max(redPlayer.getH(), bluePlayer.getH());
		return xDistance<=maxW-10 && yDistance<=maxH;
	}
	
	private void gameLoop() {
		// Thread Trigger
		timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				redPlayer.fall();
				bluePlayer.fall();
				collision();
				// TODO Auto-generated method stub
			}
		});
		timer.start();
	}
	
	private void bindEvents() {
		this.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				redPlayer.setSpeed(0);
				bluePlayer.setSpeed(0);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					redPlayer.setSpeed(-SPEED);
					redPlayer.setCurrentMove(WALK);
					redPlayer.move();
					redPlayer.setCollide(false);
					//repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if(redPlayer.isCollide()) {
						redPlayer.setSpeed(0);
					}
					else {
						redPlayer.setCollide(false);
						redPlayer.setSpeed(SPEED);
					}
					redPlayer.setCurrentMove(WALK);
					redPlayer.move();
					//repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_K) {
					redPlayer.setAttacking(true);
					redPlayer.setCurrentMove(KICK);
				}
				else if(e.getKeyCode() == KeyEvent.VK_L) {
					redPlayer.setAttacking(true);
					redPlayer.setCurrentMove(PUNCH);
				}
				else if(e.getKeyCode() == KeyEvent.VK_UP) {
					redPlayer.jump();
				}

				// BLUE PLAYER 
				else if (e.getKeyCode() == KeyEvent.VK_A) {
					if(bluePlayer.isCollide()) {
						bluePlayer.setSpeed(0);
					}
					else {
						bluePlayer.setCollide(false);
						bluePlayer.setSpeed(-SPEED);
					}
						bluePlayer.move();
						//repaint();
						bluePlayer.setCurrentMove(WALK);
				}
				else if (e.getKeyCode() == KeyEvent.VK_D) {
					bluePlayer.setCollide(false);
					bluePlayer.setSpeed(SPEED);
					bluePlayer.setCurrentMove(WALK);
					bluePlayer.move();
//					repaint();
				}
				else if(e.getKeyCode() == KeyEvent.VK_Q) {
					bluePlayer.setAttacking(true);
					bluePlayer.setCurrentMove(KICK);
				}
				else if(e.getKeyCode() == KeyEvent.VK_W) {
					bluePlayer.jump();
				}
				else if(e.getKeyCode() == KeyEvent.VK_E) {
					bluePlayer.setAttacking(true);
					bluePlayer.setCurrentMove(PUNCH);
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics pen) {
		// Rendering / Painting
		super.paintComponent(pen);
		printBackgroundImage(pen);
		redPlayer.printPlayer(pen);
		bluePlayer.printPlayer(pen);
		paintPower(pen);
		if(isGameOver) {
			printMessage(pen);
			timer.stop();
		}
	}

	private void printBackgroundImage(Graphics pen) {
		pen.drawImage(imageBg,0,0, GWIDTH,GHEIGHT, null);
	}
	
	private void loadBackgroundImage() {
		try {
			imageBg = ImageIO.read(Board.class.getResource("bg.jpeg"));
			}
			catch(Exception ex) {
				System.out.println("Background Image Loading Fail...");
				System.exit(0);
			
			}
		
	}
}
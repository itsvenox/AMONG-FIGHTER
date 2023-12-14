
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RedPlayer extends Sprite  {
	private BufferedImage [] damageImages = new BufferedImage[5];
	private BufferedImage punchImages [] = new BufferedImage[7];
	private BufferedImage walkImages [] = new BufferedImage[12];
	private BufferedImage standingImages [] = new BufferedImage[7];
	private BufferedImage kickImages [] = new BufferedImage[9];

	public RedPlayer() throws IOException {
		x = 100;
		y = FLOOR - h;
		speed = 0;
		currentMove = STANDING;
		image = ImageIO.read(RedPlayer.class.getResource(RED_IMAGE));
		
		loadDamageImage();
		loadWalkImages();
		loadStandingImages();
		loadKickImages();
		loadPunchImages();
	}
	

	public void jump() {
		if(!isJump) {
		force = DEFAULT_FORCE;
		y = y + force;
		isJump = true;
		}
	}
	
	public void fall() {
		if(y>= (FLOOR - h)) {
			isJump = false;
			return ;
		}
		force = force + GRAVITY;
		y = y + force;
	}

	private void loadDamageImage() {
		damageImages[0] = image.getSubimage(176, 176, 88, 88);
	}
	private void loadPunchImages() {
		punchImages[0] = image.getSubimage(88,0,88,88);
		punchImages[1] = image.getSubimage(88,88,88,88);
		punchImages[2] = image.getSubimage(176,88,88,88);
		punchImages[3] = image.getSubimage(0,176,88,88);
	}
	private void loadWalkImages() {
		walkImages[0] = image.getSubimage(176,0,88,88);
		walkImages[1] = image.getSubimage(88,0,88,88);
		walkImages[2] = image.getSubimage(0,88,88,88);
	}
	private void loadKickImages() {
		kickImages[0] = image.getSubimage(88,88,88,88);
		kickImages[1] = image.getSubimage(176,176,88,88);
		kickImages[2] = image.getSubimage(88,176,88,88);
		kickImages[3] = image.getSubimage(88,88,88,88);
	}
	private void loadStandingImages() {
		standingImages[0] = image.getSubimage(0, 0, 88, 88);
		standingImages[1] = image.getSubimage(88, 0, 88, 88);
	}
	
	public BufferedImage damageImage() {
		if(imageIndex>=1) {
			imageIndex=0;
			currentMove = STANDING;
		}
		BufferedImage img = damageImages[imageIndex];
		imageIndex++;
		return img;
	}
	private BufferedImage punchImage() {
		if(imageIndex>=4) {
			imageIndex=0;
			currentMove = STANDING;
			isAttacking = false;
		}
		BufferedImage img = punchImages[imageIndex];
		imageIndex++;
		return img;
	}
	private BufferedImage walkImage() {
		if(imageIndex>=3) {
			imageIndex=0;
			currentMove = STANDING;
		}
		BufferedImage img = walkImages[imageIndex];
		imageIndex++;
		return img;
	}
	private BufferedImage kickImage() {
		if(imageIndex>=4) {
			imageIndex=0;
			currentMove = STANDING;
			isAttacking = false;
		}
		BufferedImage img = kickImages[imageIndex];
		imageIndex++;
		return img;
	}
	private BufferedImage standingImage() {
		if(imageIndex>=2) {
			imageIndex=0;
		}
		BufferedImage img = standingImages[imageIndex];
		imageIndex++;
		return img;
	}
	
	@Override
	public BufferedImage defaultImage() {
		if(currentMove == DAMAGE) {
			return damageImage();
		}
		else if (currentMove == WALK) {
			return walkImage();
		}
		else if(currentMove == PUNCH) {
			return punchImage();
		}
		else if (currentMove == KICK) {
			return kickImage();
		}
		
			return standingImage();	
	}
	
}

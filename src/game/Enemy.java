package game;


import java.util.Random;

import javafx.scene.shape.Rectangle;

public class Enemy {

	private Rectangle enemy;
	private int speed;

	public Enemy(Rectangle enemy, int speed) {
		this.enemy = enemy;
		this.speed = speed;

	}

	public void move() {
		enemy.setTranslateY(enemy.getTranslateY()+speed);
	}
	public void setStartPosition(double width, Random randomSetPositionX) {
		enemy.setTranslateX(((width/10)+randomSetPositionX.nextDouble()*(8.8*width/10)));
	}
	
	public Rectangle getEnemyShape() {
		return enemy;
	}

	
}

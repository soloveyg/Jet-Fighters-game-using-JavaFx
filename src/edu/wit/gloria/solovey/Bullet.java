package edu.wit.gloria.solovey;

import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;


/**
 *  This class covers the movement of the bullet.
 * @author Gloria Solovey
 *
 */
public class Bullet extends Sprite {
	
	private JetFighter shooter;
	
	private double rotate;

	public JetFighter getShooter() {
		return shooter;
	}
	
	Bullet(int x, int y, Color color, JetFighter shooter) {
		super(x, y, 10, 10, color);
		rotate = shooter.getRotate();
		//super((double) x, (double) y, 5.0 , 5.0);
		setFill(color);
		
		setTranslateX(x);
		setTranslateY(y);
		
		this.shooter = shooter;
	}
	
	public void moveUp() {
		if (checkMinY()) {
			setTranslateY(getTranslateY() - 20);
		} else {
			die();
		}
	}

	private boolean checkMinY() {
		return getTranslateY() > getParent().getLayoutBounds().getMinY();
	}
	
	public void moveDown() {
		if (checkMaxY()) {
			setTranslateY(getTranslateY() + 20);
		} else {
			die();
		}
	}

	private boolean checkMaxY() {
		return getTranslateY() + getWidth() < getParent().getLayoutBounds().getMaxY();
	}
	
	public void moveLeft() {
		if (checkMinX()) {
			setTranslateX(getTranslateX() - 20);
		} else {
			die();
		}
	}

	private boolean checkMinX() {
		return getTranslateX() > getParent().getLayoutBounds().getMinX();
	}
	
	public void moveRight() {
		if (checkMaxX()) {
			setTranslateX(getTranslateX() + 20);
		} else {
			die();
		}
	}

	private boolean checkMaxX() {
		return getTranslateX() + getHeight() < getParent().getLayoutBounds().getMaxX();
	}

	
	public void move() {
		if (checkMaxX() && checkMaxY() && checkMinX() && checkMinY()) {
			double deltaX = 20 * Math.cos((90 - rotate)*Math.PI/180);
			double deltaY = 20 * Math.sin((90 - rotate)*Math.PI/180);
			setTranslateX(getTranslateX() + deltaX);
			setTranslateY(getTranslateY() - deltaY);
		} else {
			die();
		}		
		
	}
	/**
	 * Removes bullet from screen
	 */
	public void die() {
		((Pane) getParent()).getChildren().remove(this);
	}
	
}

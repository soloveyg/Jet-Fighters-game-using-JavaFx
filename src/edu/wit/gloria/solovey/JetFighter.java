package edu.wit.gloria.solovey;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class JetFighter extends Polygon {
	
	private int gunX;
	private int gunY;
	private boolean moving;
	private int score;
	private JetFighter enemy;
	private Label scoreCard;

	public JetFighter(int gunX, int gunY, Color color, double initialRotate) {
		super(gunX, gunY, gunX - 20, gunY + 40, gunX, gunY + 20, gunX + 20, gunY + 40);
		this.gunX = gunX;
		this.gunY = gunY;
		setFill(color);
		setRotate(initialRotate);
	}
	
	
	public int hit() {
		return ++score;
	}
	
	public boolean isMoving() {
		return moving;
	}


	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public Point2D getGunPoint() {
		return localToParent(gunX, gunY);
	}

	public void rotateRight() {
		setRotate(getRotate() + 10);
		
	}
	
	public void rotateLeft() {
		setRotate(getRotate() - 20);
	}
	
	public void moveLeft() {
		if (checkMinX()) {
			int n = gunX - 10;
			setLayoutX(getLayoutX() - 20);
			gunX = n;
		}
	}

	private boolean checkMinX() {
		return getGunPoint().getX() - 40 > getParent().getLayoutBounds().getMinX();
	}
	
	public void moveRight() {
		if (checkMaxX()) {
			int n = gunX + 10;
			setLayoutX(getLayoutX() + 10);
			gunX = n;
		}
	}

	private boolean checkMaxX() {
		return getGunPoint().getX() + 40 < getParent().getLayoutBounds().getMaxX();
	}

	public void moveUp() {
		if (checkMinY()) {
			int n = gunY - 10;
			setLayoutY(getLayoutY() - 10);
			gunY = n;
		}
	}

	private boolean checkMinY() {
		return getGunPoint().getY() - 40 > getParent().getLayoutBounds().getMinY();
	}
	
	public void moveDown() {
		if (checkMaxY()) {
			int n = gunY + 10;
			setLayoutY(getLayoutY() + 10);
			gunY = n;
		}
	}

	private boolean checkMaxY() {
		return getGunPoint().getY() + 40 < getParent().getLayoutBounds().getMaxY();
	}
	
	public void move() {
		if (checkMaxX() && checkMaxY() && checkMinX() && checkMinY()) {
			double deltaX = 2 * Math.cos((90 - getRotate())*Math.PI/180);
			double deltaY = 2 * Math.sin((90 - getRotate())*Math.PI/180);
			setTranslateX(getTranslateX() + deltaX);
			setTranslateY(getTranslateY() - deltaY);
		}	
	}

	public void die() {
		((Pane) getParent()).getChildren().remove(this);
		
	}
	
	public JetFighter getEnemy() {
		return enemy;
	}


	public void setEnemy(JetFighter enemy) {
		this.enemy = enemy;
	}


	public Label getScoreCard() {
		return scoreCard;
	}


	public void setScoreCard(Label scoreCard) {
		this.scoreCard = scoreCard;
	}
}
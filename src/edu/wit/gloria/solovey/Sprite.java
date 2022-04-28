package edu.wit.gloria.solovey;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle{
	
	Sprite(int x, int y, int w, int h, Color color) {
		super(w, h);
		setFill(color);
		
		setTranslateX(x);
		setTranslateY(y);
	}
	
	public void moveUp() {
		if (getTranslateY() > getParent().getLayoutBounds().getMinY()) {
			setTranslateY(getTranslateY() - 20);
		}
	}
	
	public void moveDown() {
		if (getTranslateY() + getHeight() < getParent().getLayoutBounds().getMaxY()) {
			setTranslateY(getTranslateY() + 20);
		}
	}
	
	public void moveLeft() {
		if (getTranslateX() > getParent().getLayoutBounds().getMinX()) {
		setTranslateX(getTranslateX() - 20);
		}
	}
	
	public void moveRight() {
		if (getTranslateX() + getWidth() < getParent().getLayoutBounds().getMaxX()) {
		setTranslateX(getTranslateX() + 20);
		}
	}
}

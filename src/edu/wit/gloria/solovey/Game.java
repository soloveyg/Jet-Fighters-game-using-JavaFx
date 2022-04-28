package edu.wit.gloria.solovey;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Game extends Application {
	
	private static final int MAX_SCORE = 15;
	
	private static final int SCREEN_SIZE = 800;
	
	private Pane root = new Pane();
	
	//private Sprite jetBlack = new Jet(300, 750, Color.BLACK);
	
	private JetFighter jetRed = new JetFighter(300, 50 , Color.RED, 180);
	
	private JetFighter jetGreen = new JetFighter(300, 750 , Color.GREEN, 0);
	
	private Label textRed = new Label();
	
	private Label textGreen = new Label();
	
	private Label help = new Label();
	
	/**
	 * The primary stage of the application.
	 */
	private Stage primaryStage;
	
	/**
	 * The scene containing the boards' border pane.
	 */    
	private Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		scene = new Scene(root, 800, 800);
		
		root.setBackground(new Background(
				
                new BackgroundFill(
                        new LinearGradient(0, 0, 0, 1, true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#57b6ff")),
                                new Stop(1, Color.web("#B06AB3"))
                        ), CornerRadii.EMPTY, Insets.EMPTY
                )));
		
		jetRed.setEnemy(jetGreen);
		jetRed.setScoreCard(textRed);
		
		jetGreen.setEnemy(jetRed);
		jetGreen.setScoreCard(textGreen);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				if(jetGreen != null) {
					jetGreen.rotateLeft();
				}
				hideHelp();
				break;
			case D:
				if(jetGreen != null) {
					jetGreen.rotateRight();
				}
				hideHelp();
				break;
			case W:
				if(jetGreen != null) {
					jetGreen.setMoving(true);
				}
				hideHelp();
				break;
			case SPACE:
				if(jetGreen != null) {
					shoot(jetGreen);
				}
				hideHelp();
				break;
			case DOWN:
				if(jetRed != null) {
					jetRed.setMoving(true);
				}
				hideHelp();
				break;
			case LEFT:
				if(jetRed != null) {
					jetRed.rotateLeft();
				}
				hideHelp();
				break;
			case RIGHT:
				if(jetRed != null) {
					jetRed.rotateRight();
				}
				hideHelp();
				break;
			case ENTER:
				if(jetRed != null) {
					shoot(jetRed);
				}
				hideHelp();
				break;
			}
		});

		root.getChildren().add(jetRed);
		root.getChildren().add(jetGreen);
		
		textRed.setText("0");
		textRed.setFont(new Font(32));
		textGreen.setText("0");
		textGreen.setFont(new Font(32));
		
		//textRed.setAlignment(Pos.TOP_LEFT);
		textRed.setLayoutX(0);
		textRed.setLayoutY(0);
		//textGreen.setAlignment(Pos.CENTER);
		textRed.setLayoutX(750);
		textRed.setLayoutY(0);
		
		textRed.setTextFill(Color.RED);
		textGreen.setTextFill(Color.GREEN);
		
		help.setFont(new Font(16));
		help.setText("Green Jet:\n"+
				"A - rotate right\n" +
				"D - rotate left\n" +
				"W - start moving\n" +
				"SPACE - shoot\n" +
				"Red Jet:\n"+
				"Right Arrow - rotate right\n" +
				"Left Arrow - rotate left\n" +
				"Down Arrow - start moving\n" +
				"Enter - shoot");
		help.setLayoutX(SCREEN_SIZE/2 - 80);
		help.setLayoutY(SCREEN_SIZE/2 - 80);
		help.setTextFill(Color.WHITE);
		
		root.getChildren().add(textRed);
		root.getChildren().add(textGreen);
		root.getChildren().add(help);
		
		primaryStage.setMinWidth(SCREEN_SIZE);
		primaryStage.setMinHeight(SCREEN_SIZE);
		primaryStage.setTitle("Jet Fighters");
		primaryStage.setScene(scene);
		timer.start();
		primaryStage.show();
	}
	
	/**
	 * Hides the help label if it is visible.
	 */
	private void hideHelp() {
		if (help.isVisible()) {
			help.setVisible(false);
		}
	}
	
	AnimationTimer timer = new AnimationTimer() {
		
		@Override
		public void handle(long now) {
			update();
			
		}

	};
	
	private void update() {
		Node[] arr = new Node[root.getChildren().size()];
		root.getChildren().toArray(arr);
		for (Node node : arr) {
			if (node instanceof Bullet) {
				Bullet b = ((Bullet) node);
				b.move();
				JetFighter shooter = b.getShooter();
				if (shooter.getEnemy() != null && b.getBoundsInParent().intersects(shooter.getEnemy().getBoundsInParent())) {
					int score = shooter.hit();
					if (score == MAX_SCORE) {
						kill(shooter.getEnemy());
					}
					shooter.getScoreCard().setText(String.valueOf(score));
					b.die();
				}
			}   
			if (node instanceof JetFighter) {
				JetFighter m = ((JetFighter) node);
				if(m.isMoving()) {
					m.move();
				}
			}
		}
	}

	private void kill(JetFighter jet) {
		jet.getEnemy().setEnemy(null);
		jet.die();
		if (jet == jetGreen) {
			jetGreen = null;
		} else {
			jetRed = null;
		}
	}
	
	/**
	 * Creates a bullet object and associates it with the shooter.
	 * 
	 *
	 * @param shooter	the jet fighter that shot the bullet.
	 */
	private void shoot(JetFighter shooter) {
		if (shooter != null) {
			Bullet b = new Bullet((int)shooter.getGunPoint().getX(),
					(int)shooter.getGunPoint().getY(),
					Color.HOTPINK, shooter);
			root.getChildren().add(b);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
	

}

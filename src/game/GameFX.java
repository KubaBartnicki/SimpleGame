package game;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameFX extends Application {
	private ArrayList<Enemy> enemyList = new ArrayList<>();
	
	private Group root;
	private VBox hud;
	
    private AnimationTimer timer;
    Thread scoreCount;
    
    private Integer lives =3;
    private Integer myScore = 0;
    private String name;
    
    private double width;
    private double height;
    
	private Label showLives = new Label();
	private Label showScore = new Label();
	private Label showName = new Label();
	private Button stopButton = new Button("Koniec");
	private Button exitButton = new Button("Wyjscie");
	
	private ScoreList listScores = null;

	
	public void setName(String name) {
		if(name.length()>19) {			
			this.name = name.substring(0, 19);
		}else {
			this.name = name;
		}
	}
	


	public Scene getScene() { 
		root = new Group();
		Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
		Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight(), Color.BLACK);
		width = visualBounds.getWidth();
		height = visualBounds.getHeight();
		
		hud = new VBox();
		showLives.setFont(Font.font(30));
		showLives.setTextFill(Color.ORANGE);
		showScore.setFont(Font.font(30));
		showScore.setTextFill(Color.ORANGE);
		showName.setFont(Font.font(30));
		showName.setTextFill(Color.ORANGE);
		stopButton.setMaxSize(100, 500);
		hud.setSpacing(20);
		hud.setPadding(new Insets(20));
		hud.getChildren().addAll(showLives, showScore, showName, stopButton);
		root.getChildren().add(hud);
        return scene;
	}
	
	
	
	
    public GameFX () {
        timer = new AnimationTimer() {
            @Override
            public void handle(long w) {
                move();
            }
        };
        timer.start();
    }
    
    public void enemy() {
		Enemy enemy;
		double howMany = 0;
		int group = 0;
		if (myScore <=100) {
			group=5;
			howMany = 0.005;
			enemy = new Enemy(new Rectangle(70, 70, Color.AQUA), 2);
		}
		else if(myScore>100 && myScore<500) {
			group=10;
			howMany = 0.01;
	    	enemy = new Enemy(new Rectangle(60, 60, Color.YELLOW), 4);
		}
		else {
			group=20;
			howMany = 0.02;
	    	enemy = new Enemy(new Rectangle(50, 50, Color.RED), 5);
		}


		if(Math.random()<howMany) { 
			if(enemyList.size()<group){

		    	enemy.setStartPosition(width, new Random());
				root.getChildren().add(enemy.getEnemyShape());
				enemyList.add(enemy); 
			}else {
				for(int i =0;i<enemyList.size();i++) {
					if(enemyList.get(i).getEnemyShape().getTranslateY()>height) {
						enemyList.remove(i);
					}
				}
					
			}
		}
		
    }
    
    
	public void move() {
		
		enemy();
		for(Enemy enemy : enemyList) {
			enemy.move();
			enemy.getEnemyShape().setOnMouseClicked(new EventHandler<MouseEvent>() {
			
				@Override
				public void handle(MouseEvent event) {
					enemy.getEnemyShape().setTranslateY(height*2);       	
				}
			});


			
			if(enemy.getEnemyShape().getTranslateY()==height) {
				lives--;
				if (lives == 0) {
					gameOver();
				}
			}
			checkState();
		}

 
	}
	public void gameOver() {
		timer.stop();
		scoreCount.interrupt();
		stopButton.setDisable(true);
		Label endGame = new Label("GAME OVER SCORE: " + myScore.toString());
		hud.getChildren().add(endGame);


              
		endGame.setTranslateX(2*width/6);
		endGame.setTranslateY(1*height/6);
		endGame.setFont(Font.font(50));
		endGame.setTextFill(Color.WHITE);
		
		Button scores = new Button("Scores");
		hud.getChildren().addAll(scores, exitButton);
		
		scores.setPrefSize(300, 100);
		scores.setFont(Font.font(30));
		scores.setTranslateX(4*width/10);
		scores.setTranslateY(1*height/6);
		scores.setTextFill(Color.BLACK);
		
		exitButton.setPrefSize(300, 100);
		exitButton.setFont(Font.font(30));
		exitButton.setTranslateX(4*width/10);
		exitButton.setTranslateY(1*height/6);
		exitButton.setTextFill(Color.BLACK);

		GlobalScoreList gsl = new GlobalScoreList();
		gsl.addScore(name, myScore);
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("list"))) {


			listScores = (ScoreList) ois.readObject();
		} catch (FileNotFoundException e1) {
			listScores = new ScoreList();
			
		} catch (ClassNotFoundException e1) {		
			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		} finally {
			listScores.addScore(name, myScore);	
		}
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("list"))) {
			oos.writeObject(listScores);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		scores.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				VievScores sv = new VievScores();
				try {
					sv.start(new Stage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	public void checkState(){
		showLives.setText("zycia: "+lives.toString());
		showScore.setText("wynik: "+myScore.toString());
		showName.setText(name);
	}

	public void scoreCount() {
		scoreCount = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					while(lives != 0) {                 
						Thread.sleep(100);
						myScore ++;
					}
				} catch (InterruptedException e) {		
					return;
				}

				
			}
		});
		scoreCount.start();

	}
	
	
	@Override
	public void start(Stage stage){
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(getScene());
		stage.setMaximized(true);
		stage.setMaximized(true);
		
		stopButton.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				gameOver();
				stopButton.setDisable(true);
				
			}
		});
		exitButton.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				stage.close();				
			}
		});
		
		scoreCount();
		stage.show();
	}
	

}

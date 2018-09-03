package game;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Menu extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setPrefSize(200, 200);
		root.setSpacing(20);
		root.setPadding(new Insets(10));
		primaryStage.setScene(new Scene(root, Color.BLACK));
		primaryStage.centerOnScreen();
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setTitle("MyGame");
		primaryStage.show();


		
		TextField name = new TextField();
		name.setText("NoName");
		Button score = new Button("Wyniki");
		Button play = new Button("Graj");
		root.getChildren().addAll(name,play, score);

		play.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				GameFX playStart = new GameFX();
				playStart.start(new Stage());
				playStart.setName(name.getText());
				primaryStage.close();
			}
		});
		
		name.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)){
					GameFX playStart = new GameFX();
					playStart.start(new Stage());
					playStart.setName(name.getText());
					primaryStage.close();
				}
			}
		});
		
		score.setOnMouseClicked(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			VievScores sv = new VievScores();
			try {
				sv.start(new Stage());
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	});
		
		
	}

}

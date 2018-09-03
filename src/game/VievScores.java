package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class VievScores extends Application {
	private ScoreList scoreList;
	private GlobalScoreList gsl;
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		stage.setScene(new Scene(root, 300, 500));
		stage.setTitle("Wyniki");
		stage.setResizable(false);
		stage.initStyle(StageStyle.UTILITY);
		



        TabPane tabPane = new TabPane();

       
            Tab localTab = new Tab();
            localTab.setText("Wyniki lokalne");
            VBox localvBox = new VBox();
            localvBox.getChildren().add(getLocalScoresTable());
            localvBox.setAlignment(Pos.CENTER);
            localTab.setContent(localvBox);
            
            Tab globalTab = new Tab();
            globalTab.setText("Wyniki globalne");
            VBox globalvBox = new VBox();
 //         globalvBox.getChildren().add(new Label("work in progress :D"));
            globalvBox.getChildren().add(getGlobalScoresTable());
            globalvBox.setAlignment(Pos.CENTER);
            globalTab.setContent(globalvBox);
            tabPane.getTabs().addAll(localTab, globalTab);
          
            root.getChildren().add(tabPane);         

        
        
		stage.show();
	}
	
	public TableView getGlobalScoresTable() { 
		gsl = new GlobalScoreList();

        final ObservableList<ArraysMenager> player = FXCollections.observableArrayList();
        final ObservableList<ArraysMenager> score = FXCollections.observableArrayList();

        TableView<ArraysMenager> show = new TableView<>(FXCollections.observableArrayList());
 
        TableColumn playerShow = new TableColumn<ArraysMenager, String>("Gracz");
        playerShow.setCellValueFactory(new PropertyValueFactory<ArraysMenager, String>("Player"));
        TableColumn scoreShow = new TableColumn<ArraysMenager, Integer>("Wynik");
        scoreShow.setCellValueFactory(new PropertyValueFactory<ArraysMenager, Integer>("Score"));

        playerShow.setPrefWidth(149);
        scoreShow.setPrefWidth(149);
        
        show.setItems(player);
        show.setItems(score);
        show.getColumns().addAll(playerShow, scoreShow);
        
        for(int i = 0;i<gsl.getPlayersArray().length;i++) {
        	player.add(new ArraysMenager(gsl.getPlayersArray(), gsl.getScoresArray(), i));
        	score.add(new ArraysMenager(gsl.getPlayersArray(), gsl.getScoresArray(), i));
        } 
		
        scoreShow.setSortType(SortType.DESCENDING);
		
		return show;
	}
	
	public TableView getLocalScoresTable() {
		
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("list"))) {
			scoreList = (ScoreList) ois.readObject();

		} catch (FileNotFoundException e1) {
			scoreList=null;
			System.out.println("blad");
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		} 
        final ObservableList<ArraysMenager> player = FXCollections.observableArrayList();
        final ObservableList<ArraysMenager> score = FXCollections.observableArrayList();

        TableView<ArraysMenager> show = new TableView<>(FXCollections.observableArrayList());
 
        TableColumn playerShow = new TableColumn<ArraysMenager, String>("Gracz");
        playerShow.setCellValueFactory(new PropertyValueFactory<ArraysMenager, String>("Player"));
        TableColumn scoreShow = new TableColumn<ArraysMenager, Integer>("Wynik");
        scoreShow.setCellValueFactory(new PropertyValueFactory<ArraysMenager, Integer>("Score"));

        playerShow.setPrefWidth(149);
        scoreShow.setPrefWidth(149);
        
        show.setItems(player);
        show.setItems(score);
        show.getColumns().addAll(playerShow, scoreShow);
        
        for(int i = 0;i<scoreList.getPlayersArray().length;i++) {
        	player.add(new ArraysMenager(scoreList.getPlayersArray(), scoreList.getScoresArray(), i));
        	score.add(new ArraysMenager(scoreList.getPlayersArray(), scoreList.getScoresArray(), i));
        } 
		
        scoreShow.setSortType(SortType.DESCENDING);
		
		return show;
	}

}

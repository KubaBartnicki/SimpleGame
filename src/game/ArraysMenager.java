package game;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ArraysMenager {
	private SimpleStringProperty playersArray;
	private SimpleIntegerProperty scoresArray;
	private String [] players;
	private Integer[] scores;
	
	public ArraysMenager(String[] players, Integer[] scores, int i) {
		this.players = players;
		this.scores = scores;
        this.playersArray = new SimpleStringProperty(this.players[i]);
        this.scoresArray = new SimpleIntegerProperty(this.scores[i]);
	}
	
	public String getPlayer() {
		return playersArray.get();
	}
	
	public Integer getScore() {
		return scoresArray.get();
	}
}

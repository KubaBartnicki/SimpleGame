package game;

public interface Save {

	public String[] getPlayersArray();
	public Integer[] getScoresArray();
	public void addScore(String player, Integer score); 

}

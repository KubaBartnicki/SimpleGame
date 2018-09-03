package game;

import java.io.Serializable;
import java.util.HashMap;

public class ScoreList implements Serializable, Save {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> scores = new HashMap<String, Integer>();
	private String[] playersArray;
	private Integer[] scoresArray;

	
	public String[] getPlayersArray() {
		int i = 0;
		playersArray = new String[scores.size()];
		for (String a : scores.keySet()) {
			playersArray[i] = a;
			i++;
		}	
		return playersArray;
	}
	public Integer[] getScoresArray() {
		int i = 0;
		scoresArray = new Integer[scores.size()];
		for (String a : scores.keySet()) {
			scoresArray[i] = scores.get(a);
			i++;
		}	
		return scoresArray;
	}
	 public HashMap<String, Integer> getList(){
		 return scores;
	 }
	
	public void addScore(String player, Integer score) {

			if(scores.size() == 0) {
				scores.put(player, score);
			} else {
		        for(String a: scores.keySet()) {
		        	if(a.equals(player)) {
		        		if(scores.get(player)<score) {
		        			scores.put(player, score);
			        		return;
		        		}
		        
		        		return;
		        	}

		        }
    			scores.put(player, score); 
			}
			


		
	}

}



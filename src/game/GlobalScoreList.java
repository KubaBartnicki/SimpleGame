package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class GlobalScoreList implements Save {
	private HashMap<String, Integer> scores = new HashMap<String, Integer>();
	private String[] playersArray;
	private Integer[] scoresArray;
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public GlobalScoreList(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Properties sqlConnect = new Properties();
		try {
			sqlConnect.load(new FileInputStream("sql.log"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/game", sqlConnect.getProperty("user"), sqlConnect.getProperty("password"));
			Statement statement = (Statement) connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM scores");

			while(resultSet.next()) {
				scores.put(resultSet.getString("player"), resultSet.getInt("score"));
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String[] getPlayersArray() {
		int i = 0;
		playersArray = new String[scores.size()];
		for (String a : scores.keySet()) {
			playersArray[i] = a;
			i++;
		}	
		return playersArray;
	}

	@Override
	public Integer[] getScoresArray() {
		int i = 0;
		scoresArray = new Integer[scores.size()];
		for (String a : scores.keySet()) {
			scoresArray[i] = scores.get(a);
			i++;
		}	
		return scoresArray;
	}


	@Override
	public void addScore(String player, Integer score) {

		try {
			if(scores.size() == 0) {


				preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO scores(id, player, score) VALUES(null, ? , ?)");
				preparedStatement.setString(1, player);
				preparedStatement.setInt(2, score);
				preparedStatement.executeUpdate();
			} else {
		        for(String a: scores.keySet()) {
		        	if(a.equals(player)) {
		        		if(scores.get(player)<score) {
		    				preparedStatement = (PreparedStatement) connection.prepareStatement("UPDATE scores SET score =? WHERE player = ?");
		    				preparedStatement.setInt(1, score);
		    				preparedStatement.setString(2, player);
		    				preparedStatement.executeUpdate();
		    				// pstmt.close();
			        		return;
		        		}
		        	   	return;
		        	}

		        }
				preparedStatement = (PreparedStatement) connection.prepareStatement("INSERT INTO scores(id, player, score) VALUES(null, ? , ?)");
				preparedStatement.setString(1, player);
				preparedStatement.setInt(2, score);
				preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}


	}

}

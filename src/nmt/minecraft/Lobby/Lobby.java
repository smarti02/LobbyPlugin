package nmt.minecraft.Lobby;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nmt.minecraft.Lobby.Game.LobbyGame;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Lobby{
	private List<LPlayer> playerList;
	private Location exitLocation;
	private Location lobbyLocation;
	private String gameType;
	private String gameMap;
	private ArrayList<File> mapList;
	private String lobbyName;
	
	Lobby(){
		this.playerList = new ArrayList<LPlayer>();
		this.exitLocation = null;
		this.lobbyLocation = null;
		this.gameType = "";
		this.gameMap = "";
	}
	
	/**
	 * add player to the lobby
	 * @param player
	 */
	public void addPlayer(Player player){
		if(getLPlayer(player) == null){
			return;
		}
		this.playerList.add(new LPlayer(player));
	}
	
	/**
	 * removes a player from the lobby
	 * @param player the payer to remove
	 */
	public void removePlayer(LPlayer player){
		if(player == null || !playerList.contains(player)){
			return;
		}
		this.playerList.remove(player);
	}
	
	/**
	 * removes a player from the lobby
	 * @param player the player to remove
	 */
	public void removePlayer(Player player){
		removePlayer(getLPlayer(player));
	}
	
	/**
	 * gets a player from the player list
	 * @param player the player to search for
	 * @return the LPlayer if they have been found, null otherwise
	 */
	public LPlayer getLPlayer(Player player){
		for(LPlayer lplay : playerList){
			if(lplay.getPlayer().equals(player)){
				return lplay;
			}
		}
		return null;
	}
	
	/**
	 * sets the exit location
	 * @param exit the exit location
	 */
	public void setExitLocation(Location exit){
		this.exitLocation = exit;
	}
	
	/**
	 * sets the lobby location
	 * @param lobby the lobby location
	 */
	public void setLobbyLocation(Location lobby){
		this.lobbyLocation = lobby;
	}
	
	public void setLobbyName(String name){
		this.lobbyName = name;
	}
	
	public Location getLobbyLocation(){
		return this.lobbyLocation;
	}
	
	public Location getExitLocation(){
		return this.exitLocation;
	}
	
	public String getName(){
		return this.lobbyName;
	}
	
	/**
	 * calculates the gametype that the players voted for and sets that as the game type
	 */
	public void setGameType(){
		//init the votes
		int[] gameTypeVotes = new int[LobbyGame.gameType.length];
		for(int i=0; i<gameTypeVotes.length; i++){
			gameTypeVotes[i]=0;
		}
		
		//calculate the votes
		for(LPlayer player : this.playerList){
			String tmp = player.getGameTypeVote();
			for(int i=0; i<gameTypeVotes.length; i++){
				if(tmp.equalsIgnoreCase(LobbyGame.gameType[i])){
					gameTypeVotes[i]++;
				}
			}
		}
		
		//sets the top vote to the gameType or a random one if there is more than one
		ArrayList<Integer> tops= new ArrayList<Integer>();
		int max=gameTypeVotes[0];
		for(int i=0; i<gameTypeVotes.length; i++){
			if(gameTypeVotes[i] == max){
				tops.add(new Integer(i));
			}else if(gameTypeVotes[i] > max){
				tops.clear();
				max = gameTypeVotes[i];
				tops.add(new Integer(i));
			}
		}
		
		//get the random of the top choices
		Random rand = new Random();
		int top = tops.get(rand.nextInt(tops.size()));
		setGameType(LobbyGame.gameType[top]);
		
	}
	
	/**
	 * sets the given game type
	 * @param gameType
	 */
	public void setGameType(String gameType){
		this.gameType = gameType;
	}
	
	/**
	 * gets a list of maps for the already set type of game
	 * @return
	 */
	public boolean initMapList(){
		if(this.gameType.equals("")){
			LobbyPlugin.lobbyPlugin.getLogger().info("Game Type has not yet been set");
			return false;
		}
		
		this.mapList = LobbyPlugin.lobbyPlugin.getMapList(this.gameType);
		
		return true;
	}
}

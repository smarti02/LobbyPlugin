package nmt.minecraft.Lobby;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class Lobby{
	private List<LPlayer> playerList;
	private Location exitLocation;
	private Location lobbyLocation;
	
	Lobby(){
		this.playerList = new ArrayList<LPlayer>();
		this.exitLocation = null;
		this.lobbyLocation = null;
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
	 * gets the list of players currently waiting in the lobby
	 * @return A list of players waiting in the lobby
	 */
	public ArrayList<Player> getPlayerList(){
		ArrayList<Player> list = new ArrayList<Player>();
		for(LPlayer p : this.playerList){
			list.add(p.getPlayer());
		}
		
		return list;
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
	
	public Location getLobbyLocation(){
		return this.lobbyLocation;
	}
	
	public Location getExitLocation(){
		return this.exitLocation;
	}
}

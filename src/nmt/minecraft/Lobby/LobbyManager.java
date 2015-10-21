package nmt.minecraft.Lobby;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import nmt.minecraft.Lobby.IO.ChatFormat;

public class LobbyManager {
	private static Collection<Lobby> lobbies = new LinkedList<Lobby>();
	
	/**
	 * finds the lobby with the given name
	 * @param lobbyName
	 * @return
	 */
	public static Lobby getLobby(String lobbyName) {
		Iterator<Lobby> it = lobbies.iterator();
		
		while(it.hasNext()){
			Lobby curr = it.next();
			if(curr.getName().equalsIgnoreCase(lobbyName)){
				return curr; 
			}
		}
		return null;
	}
	
	/**
	 * finds the lobby with the player in it
	 * @param lobbyName
	 * @return
	 */
	public static Lobby getLobby(Player player) {
		Iterator<Lobby> it = lobbies.iterator();
		
		while(it.hasNext()){
			Lobby curr = it.next();
			if(curr.getPlayer(player)!=null){
				return curr;
			}
		}
		return null;
	}

	public static Collection<Lobby> getLobbies(){
		return LobbyManager.lobbies;
	}

	/**
	 * Creates a new lobby and adds it to the lobby list
	 * @param lobbyName
	 * @return null if it could not be created
	 */
	public static Lobby newLobby(String lobbyName) {
		if(getLobby(lobbyName) != null){
			return null;
		}
		
		Lobby tmp = new Lobby(lobbyName);
		
		lobbies.add(tmp);
		return tmp;
	}

	/**
	 * a tool method to print out locations
	 * @param location
	 * @return
	 */
	public static String locationToString(Location location) {
		if(location == null){
			return ChatFormat.ERROR.wrap("no location");
		}
		String loc = location.getBlockX()+", ";
		loc += location.getBlockY()+", ";
		loc += location.getBlockZ();
		return loc;
		
	}
}

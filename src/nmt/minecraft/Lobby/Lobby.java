package nmt.minecraft.Lobby;

import java.util.Collection;
import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import nmt.minecraft.Lobby.IO.ChatFormat;

public class Lobby{
	private String name;
	private Collection<LPlayer> players;
	private Location buttonLocation;
	private Location exitButtonLocation;
	private Location lobbyLocation;
	private boolean isOpen;
	
	Lobby(String name){
		this.name = name;
		players = new LinkedList<LPlayer>();
		this.isOpen = false;
	}
	
	@Override
	public boolean equals(Object l){
		if(!(l instanceof Lobby)){
			return false;
		}
		
		return name.equalsIgnoreCase(((Lobby)l).getName());
	}
	
	public String getName() {
		return name;
	}

	public Location getButtonLocation() {
		return this.buttonLocation;
	}
	
	public Location getExitButtonLocation() {
		return this.exitButtonLocation;
	}
	
	public Location getLocation() {
		return this.lobbyLocation;
	}

	public boolean getIsOpen(){
		return this.isOpen;
	}
	
	public String getInfo(){
		String str = ChatFormat.INFO.wrap("Name: ")+this.name+"\n";
		
		str += ChatFormat.INFO.wrap("Is Valid: ");
		if(this.isValid()){
			str += ChatFormat.SUCCESS.wrap("true\n");
		}else{
			str += ChatFormat.ERROR.wrap("false\n");
		}
		
		str += ChatFormat.INFO.wrap("Button Location: ")+LobbyManager.locationToString(buttonLocation)+ "\n";
		str += ChatFormat.INFO.wrap("Lobby Location: ")+LobbyManager.locationToString(lobbyLocation)+ "\n";
		str += ChatFormat.INFO.wrap("Exit Button Location: ")+LobbyManager.locationToString(exitButtonLocation)+ "\n";
		
		str += ChatFormat.INFO.wrap("Status: ");
		if(this.isOpen==false){
			str+= "CLOSED\n";
		}else{
			str+= "OPEN\n";
		}
		
		if(players.isEmpty()){
			str += ChatFormat.WARNING.wrap("No Players in Lobby!\n");
		}else{
			str += ChatFormat.INFO.wrap("Player List:\n");
			for(LPlayer p : players){
				str += p.getName()+ "\n";
			}
		}
		return str;
	}
	
	private boolean isValid() {
		if(buttonLocation == null || exitButtonLocation == null || lobbyLocation == null)
			return false;
		return true;
	}

	/**
	 * This is a boolean in case we want to add any restrictions later
	 * @param location 
	 * @return
	 */
	public boolean setButtonLocation(Location location) {
		this.buttonLocation = location;
		return true;
	}
	
	/**
	 * This is a boolean in case we want to add any restrictions later
	 * @param location 
	 * @return
	 */
	public boolean setExitButtonLocation(Location location) {
		this.exitButtonLocation = location;
		return true;
	}
	
	/**
	 * This is a boolean in case we want to add any restrictions later
	 * @param location 
	 * @return
	 */
	public boolean setLocation(Location location) {
		this.lobbyLocation = location;
		return true;
	}

	/**
	 * 
	 * @return true if it was successful in opening the lobby
	 */
	public boolean open(){
		if(this.isValid() == false){
			return false;
		}
		
		this.isOpen = true;
		return true;
	}
	
	/**
	 * 
	 * @return true if it was sucessful in closing the lobby
	 */
	public boolean close(){
		if(this.isOpen == false){
			return false;
		}
		
		//kick all current players
		this.kickAll();
		
		this.isOpen = false;
		return true;
	}
	
	public boolean addPlayer(LPlayer player){
		if(this.isOpen == false){
			player.getPlayer().sendMessage(ChatFormat.ERROR.wrap("Lobby is not yet open!"));
			return false;
		}
				
		players.add(player);
		player.moveTo(lobbyLocation);
		return true;
	}
	
	public boolean removePlayer(Player player){
		LPlayer play = this.getPlayer(player);
		if(play == null)
			return false;
		
		play.restore();
		return players.remove(play);
	}
	
	public LPlayer getPlayer(Player player){
		if(players.isEmpty()){
			return null;
		}
		
		for(LPlayer p : players){
			if(p.getPlayer().equals(player)){
				return p;
			}
		}
		
		return null;
	}
	
	public void kick(LPlayer player){
		if(player == null)
			return;
		//only restore the player if they were actually in the list
		if(players.remove(player))
			player.restore();
		
	}

	public void kickAll() {
		for(LPlayer player : this.players){
			kick(player);
		}
	}

}

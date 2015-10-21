package nmt.minecraft.Lobby;

import java.util.Collection;
import java.util.LinkedList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import nmt.minecraft.Lobby.IO.ChatFormat;

public class Lobby {
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
		
		//TODO kick all current players
		
		this.isOpen = false;
		return true;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled() || e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) {
			return;
		}
		
		Location clickLocation =e.getClickedBlock().getLocation(); 
		if (clickLocation.equals(this.buttonLocation)){
			e.setCancelled(true);
			//check if a player is already in another lobby
			if(LobbyManager.getLobby(e.getPlayer())!=null){
				e.getPlayer().sendMessage(ChatFormat.ERROR.wrap("You are already in a lobby"));
				return;
			}
			
			//add them to this lobby
			LPlayer player = new LPlayer(e.getPlayer());
			this.players.add(player);
			
			//teleport them to the lobby location
			player.moveTo(lobbyLocation);
			e.getPlayer().sendMessage(ChatFormat.SUCCESS.wrap("You have been added to the lobby: "+this.name));
			
		}else if(clickLocation.equals(this.exitButtonLocation)){
			//remove this player from the lobby
			LPlayer player = getPlayer(e.getPlayer());
			if(player == null){
				return;
			}
			player.restore();
			players.remove(player);
			e.getPlayer().sendMessage(ChatFormat.SUCCESS.wrap("You have been removed from the lobby: "+this.name));
		}
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

}

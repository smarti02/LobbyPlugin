package nmt.minecraft.Lobby.Events;

import java.util.ArrayList;
import java.util.HashMap;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class LobbyEventHandler implements Listener {
	private HashMap<Lobby, Location> registrationLocations;
	
	public LobbyEventHandler(){
		Bukkit.getPluginManager().registerEvents(this, LobbyPlugin.lobbyPlugin);
		this.registrationLocations = new HashMap<Lobby,Location>();
	}
	
	/**
	 * prevent people from getting killed
	 * @param e
	 */
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			//find the player in one of the lobbies
			ArrayList<Lobby> lobbies = LobbyPlugin.lobbyPlugin.getLobbies();
			Player player = (Player)e.getEntity();
			//if the player is gonna die, check an see if they are in a lobby
			if(player.getHealth() <= e.getDamage()){
				for(Lobby lobby : lobbies){
					if(lobby.getLPlayer(player) != null){
						//telport them back to the lobby with full stats
						player.teleport(lobby.getLobbyLocation());
						player.setMaxHealth(player.getMaxHealth());
						player.setExhaustion(20);
					}
				}
			}
		}
	}
	
	public void addRegistration(Location location, Lobby lobby){
		if(registrationLocations.get(lobby)!=null){
			registrationLocations.remove(lobby);
		}

		registrationLocations.put(lobby, location);
		System.out.println("Added button to registration location list");
	}
	
	
	@EventHandler
	public void blockActivated(PlayerInteractEvent e) {	
		
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			return;
		}
		System.out.println("AHHHHHHHHH YOU CLICKED SOMETHING");
		System.out.println("getting location from event");
		double XCor = e.getClickedBlock().getLocation().getX();
		double YCor = e.getClickedBlock().getLocation().getY();
		System.out.println("got clicked Block location");
		//look for the location which was clicked in the lobby sets
		for(Lobby lobby : this.registrationLocations.keySet()){
			Location loc = this.registrationLocations.get(lobby);
			System.out.println("getting location of "+lobby.getName());
			double ButtonXCor = Math.floor(loc.getX());
			double ButtonYCor = Math.floor(loc.getY());
			if (XCor == ButtonXCor && YCor == ButtonYCor) {
				System.out.println("FOUND THE BUTTON");
				LobbyPlugin.lobbyPlugin.getLogger().info("Player: " + e.getPlayer().getName() + " requesting registration...");
				//Add player to game and teleport to correct lobby
				lobby.addPlayer(e.getPlayer());
				return;
			}
		}
		
	}
	
}

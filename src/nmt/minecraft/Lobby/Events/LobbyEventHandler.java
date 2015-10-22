package nmt.minecraft.Lobby.Events;

import java.util.LinkedList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import nmt.minecraft.Lobby.LPlayer;
import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyManager;
import nmt.minecraft.Lobby.LobbyPlugin;
import nmt.minecraft.Lobby.IO.ChatFormat;

public class LobbyEventHandler implements Listener {
	
	private static LinkedList<Player> playerList; 
	
	public LobbyEventHandler(){
		if(playerList == null)
			playerList = new LinkedList<Player>();
		
		Bukkit.getPluginManager().registerEvents(this, LobbyPlugin.plugin);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled() || e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR) {
			return;
		}
		
		Block clickLocation =e.getClickedBlock().getLocation().getBlock();
		Player player = e.getPlayer();
		
		//if the player is already in a lobby and they are trying to enter a bed, stop them
		if(playerList.contains(player) && e.getClickedBlock().getType() == Material.BED){
			e.setCancelled(true);
			return;
		}
		
		//find the lobby the player is trying to join
		for(Lobby lobby : LobbyManager.getLobbies()){
			if(lobby.getButtonLocation().getBlock().equals(clickLocation)){
				e.setCancelled(true);
				//double check the player list
				if(playerList.contains(player)){
					player.sendMessage(ChatFormat.ERROR.wrap("You are already in a lobby"));
					return;
				}
				
				//add them to the lobby
				if(lobby.addPlayer(new LPlayer(player))){
					playerList.add(player);
					e.getPlayer().sendMessage(ChatFormat.SUCCESS.wrap("You have been added to the lobby: "+lobby.getName()));
					return;
				}
			}else if(lobby.getExitButtonLocation().getBlock().equals(clickLocation)){
				e.setCancelled(true);
				//check to see if the player is in any lobby
				if(!playerList.contains(player)){
					player.sendMessage(ChatFormat.ERROR.wrap("You are not in a lobby"));
					return;
				}
				
				//check to see if the player is in THIS lobby
				if(lobby.getPlayer(player)==null){
					player.sendMessage(ChatFormat.ERROR.wrap("You are not in this lobby "+lobby.getName()));
					return;
				}
				
				//remove them from the lobby
				if(lobby.removePlayer(player)){
					playerList.remove(player);
					player.sendMessage(ChatFormat.SUCCESS.wrap("You have been removed from the lobby: "+lobby.getName()));
					return;
				}else{
					player.sendMessage(ChatFormat.ERROR.wrap("There was an error removing you from the lobby "+lobby.getName()));
					return;
				}
			}
		}
	}
	
	
	
	/**
	 * make sure players don't get hurt //unless// they fall out of the world or use the /kill command
	 * @param e
	 */
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e){
		//check if it is a player
		if(!(e.getEntity() instanceof Player)){
			return;
		}
		
		Player player = (Player) e.getEntity();
		
		//check if the player is in our list
		if(!playerList.contains(player)){
			return;
		}
		
		//let them die if they really want to.
		if(e.getCause().equals(DamageCause.VOID) || e.getCause().equals(DamageCause.SUICIDE)){
			return;
		}
		
		e.setCancelled(true);
	}
	
	//prevent the players hunger from going down TODO
	
	//prevent the players from talking/trading with villagers
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e){
		if(playerList.contains(e.getPlayer()) && e.getRightClicked().getType().equals(EntityType.VILLAGER)){
			e.setCancelled(true);
		}
	}
	
	
	//prevent players from picking up items
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent e){
		if(playerList.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	//prevent players from breaking blocks
	
	//prevent players from opening chests
	@EventHandler
	public void onPlayerOpen(InventoryOpenEvent e){
		if(playerList.contains(e.getPlayer()))
			e.setCancelled(true);
	}
	
	//catch players logging out and remove them from the list.
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e){
		Player player = e.getPlayer();
		if(playerList.contains(player)){
			//find the lobby they are in and remove them
			for(Lobby lobby : LobbyManager.getLobbies()){
				if(lobby.getPlayer(player) != null){
					lobby.removePlayer(player);
					playerList.remove(player);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Player player = e.getPlayer();
		if(playerList.contains(player)){
			//find the lobby they are in and remove them
			for(Lobby lobby : LobbyManager.getLobbies()){
				if(lobby.getPlayer(player) != null){
					lobby.removePlayer(player);
					playerList.remove(player);
				}
			}
		}
	}
}

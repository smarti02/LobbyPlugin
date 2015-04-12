package nmt.minecraft.Lobby.Events;

import java.util.ArrayList;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyPlugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class LobbyEventHandler implements Listener {
	public LobbyEventHandler(){
		Bukkit.getPluginManager().registerEvents(this, LobbyPlugin.lobbyPlugin);
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
	
	
}

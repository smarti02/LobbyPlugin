package nmt.minecraft.Lobby;

import org.bukkit.plugin.java.JavaPlugin;

import nmt.minecraft.Lobby.IO.LobbyCommands;
import nmt.minecraft.Lobby.IO.LobbyTabCompleter;

public class LobbyPlugin extends JavaPlugin{
	public static LobbyPlugin plugin;
	
	@Override
	public void onEnable() {
		this.getCommand("lobby").setExecutor(new LobbyCommands());
		this.getCommand("lobby").setTabCompleter(new LobbyTabCompleter());
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public void onLoad() {
		plugin = this;
	}
}

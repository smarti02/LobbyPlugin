package nmt.minecraft.Lobby;

import java.io.File;
import java.util.ArrayList;

import nmt.minecraft.Lobby.Events.LobbyEventHandler;
import nmt.minecraft.Lobby.IO.LobbyCommands;
import nmt.minecraft.Lobby.IO.LobbyTabCompleter;

import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin{
	private ArrayList<Lobby> lobbyList;
	public static LobbyPlugin lobbyPlugin;
	private LobbyEventHandler eventHandler;
	LobbyPlugin(){
		this.getLogger().info("Loading command listener..");
		this.getCommand("lobby").setExecutor(new LobbyCommands());
		this.getCommand("lobby").setTabCompleter(new LobbyTabCompleter());
		this.getCommand("leaveLobby").setExecutor(new LobbyCommands());
		
		LobbyPlugin.lobbyPlugin = this;
		
		this.getLogger().info("Creating Empty Lobby list...");
		this.lobbyList = new ArrayList<Lobby>();
		eventHandler = new LobbyEventHandler();
	}

	public ArrayList<Lobby> getLobbies() {
		// TODO Auto-generated method stub
		return lobbyList;
	}
	
	public ArrayList<File> getMapList(String gameType){		
		File current = LobbyPlugin.lobbyPlugin.getDataFolder();
		File[] tmp = current.listFiles();
		File gameTypeFolder=null;
		ArrayList<File> mapList = new ArrayList<File>();
		
		for(int i=0; i<tmp.length;  i++){
			if(tmp[i].getName().equalsIgnoreCase(gameType)){
				gameTypeFolder = tmp[i];
			}
		}
		
		if(gameTypeFolder == null || !gameTypeFolder.isDirectory()){
			LobbyPlugin.lobbyPlugin.getLogger().info("Could not find the given gametype folder");
			return null;
		}
		
		File[] tmp2 = gameTypeFolder.listFiles();
		for(int i=0; i<tmp2.length; i++){
			mapList.add(tmp2[i]);
		}
		
		return mapList;
	}
}

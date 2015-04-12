package nmt.minecraft.Lobby;

import java.io.File;
import java.util.ArrayList;

import nmt.minecraft.Lobby.Events.LobbyEventHandler;
import nmt.minecraft.Lobby.Game.LobbyGame;
import nmt.minecraft.Lobby.IO.LobbyCommands;
import nmt.minecraft.Lobby.IO.LobbyTabCompleter;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyPlugin extends JavaPlugin{
	private ArrayList<Lobby> lobbyList;
	public static LobbyPlugin lobbyPlugin;
	private LobbyEventHandler eventHandler;
	
	@Override
	public void onEnable() {
		this.getLogger().info("Loading command listener..");
		this.getCommand("lobby").setExecutor(new LobbyCommands());
		this.getCommand("lobby").setTabCompleter(new LobbyTabCompleter());
		
		LobbyPlugin.lobbyPlugin = this;
		
		this.getLogger().info("Creating Empty Lobby list...");
		this.lobbyList = new ArrayList<Lobby>();
		eventHandler = new LobbyEventHandler();
	}
	
	@Override
	public void onLoad() {
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		
		//generate folders for the gameTypes if there isn't one
		this.getLogger().info("Checking game configurations...");
		for(String type : LobbyGame.gameType){
			File configDir = new File(getDataFolder(), type);
			if (!configDir.exists()) {
				this.getLogger().info("Missing "+ type+". Creating new directory...");
				configDir.mkdir();
			}
		}
	}

	public ArrayList<Lobby> getLobbies() {
		// TODO Auto-generated method stub
		return lobbyList;
	}
	
	public ArrayList<File> getMapList(String gameType){		
		ArrayList<File> mapList = new ArrayList<File>();
		
		File gameTypeFolder = new File(getDataFolder(), gameType);
		if(!gameTypeFolder.exists() || !gameTypeFolder.isDirectory()){
			LobbyPlugin.lobbyPlugin.getLogger().info("Could not find the given gametype folder");
			return null;
		}
		
		File[] tmp2 = gameTypeFolder.listFiles();
		for(int i=0; i<tmp2.length; i++){
			mapList.add(tmp2[i]);
		}
		
		return mapList;
	}
	
	public Lobby getLobby(String lobbyName){
		for(Lobby lobby : this.lobbyList){
			if(lobby.getName().equalsIgnoreCase(lobbyName)){
				return lobby;
			}
		}
		
		return null;
	}
	
	public void newLobby(String lobbyName, Location lobbyLocation){
		Lobby tmp = new Lobby();
		tmp.setLobbyName(lobbyName);
		this.lobbyList.add(tmp);
		this.eventHandler.addRegistration(lobbyLocation, tmp);
	}
}

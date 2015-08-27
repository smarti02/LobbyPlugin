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
	
	public void addLobby(String name){
		this.lobbyList.add(new Lobby(name));
	}
	
	public void removeLobby(String name)
	{
		Lobby l = this.getLobby(name);
		l.kickAll();
		this.lobbyList.remove(l);
	}
	
	public Lobby getLobby(String lobbyName){
		for(Lobby lobby : this.lobbyList){
			if(lobby.getName().equals(lobbyName)){
				return lobby;
			}
		}
		
		return null;
	}
}

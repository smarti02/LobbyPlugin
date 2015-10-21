package nmt.minecraft.Lobby.IO;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyManager;

public class LobbyCommands implements CommandExecutor{
	private static String[] commandList = {"create", "setButton", "setLocation","open","close","info","help"}; //if you change this remember to add it to the help function
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
	
	public static String commandsToString(){
		String tmp = "";
		
		for(int i=0; i<commandList.length; i++){
			tmp += commandList[i];
			if(i!= commandList.length-1){
				tmp+=" ,";
			}
		}
		
		return tmp;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		Lobby lobby;
		if(args.length<2){
			return false;
		}
		args[0] = args[0].toLowerCase();
		switch(args[0]){
		case "create":
			// /lobby create [lobbyName]
			if(LobbyManager.getLobby(args[1]) != null){
				sender.sendMessage(ChatFormat.ERROR.wrap("There is already a lobby with that name!"));
				return true;
			}
			
			if(LobbyManager.newLobby(args[1]) == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not create new lobby "+args[1]));
				return true;
			}
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Created new lobby "+args[1]));
			Command.broadcastCommandMessage(sender,ChatFormat.SUCCESS.wrap("Created new lobby "+args[1]));
			break;
		case "setbutton":
			// /lobby set [lobbyName]
			
			if(! (sender instanceof Player)){
				ChatFormat.ERROR.wrap("You must be a player to run this command");
				return true;
			}
			
			lobby = LobbyManager.getLobby(args[1]); 
			if( lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
			}
			
			if(!lobby.setButtonLocation(((Player)sender).getLocation())){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not set button location"));
				return true;
			}
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Set button location for "+args[1]+" at: "+LobbyManager.locationToString(lobby.getButtonLocation())));
			
			
			break;
		case "setexitbutton":
			// /lobby setExitButton [lobbyName]
			
			if(! (sender instanceof Player)){
				ChatFormat.ERROR.wrap("You must be a player to run this command");
				return true;
			}
			
			lobby = LobbyManager.getLobby(args[1]); 
			if( lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
			}
			
			if(!lobby.setExitButtonLocation(((Player)sender).getLocation())){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not set exit button location"));
				return true;
			}
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Set exit button location for "+lobby.getName()+" at: "+LobbyManager.locationToString(lobby.getButtonLocation())));
			
			
			break;
		case "setlocation":
			// /lobby setLocation [lobbyName]
			
			if(! (sender instanceof Player)){
				ChatFormat.ERROR.wrap("You must be a player to run this command");
				return true;
			}
			
			lobby = LobbyManager.getLobby(args[1]); 
			if( lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
			}
			
			if(!lobby.setLocation(((Player)sender).getLocation())){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not set lobby location"));
				return true;
			}
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Set lobby location for "+lobby.getName()+" at: "+LobbyManager.locationToString(lobby.getButtonLocation())));
			
			
			break;	
			
		case "open":
			// /lobby open [lobbyName]
			lobby = LobbyManager.getLobby(args[1]);
			if(lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
				return true;
			}
			
			if(lobby.getIsOpen()){
				sender.sendMessage(ChatFormat.ERROR.wrap("Lobby "+lobby.getName()+ " is already open."));
				return true;
			}
			
			if(!lobby.open()){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not open lobby "+lobby.getName()+" is not valid"));
			}
			
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Lobby "+lobby.getName()+" is now OPEN"));
			
			break;
		case "close":
			// /lobby open [lobbyName]
			lobby = LobbyManager.getLobby(args[1]);
			if(lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
				return true;
			}
			
			if(!lobby.getIsOpen()){
				sender.sendMessage(ChatFormat.ERROR.wrap("Lobby "+lobby.getName()+ " is already closed."));
				return true;
			}
						
			lobby.close();
			sender.sendMessage(ChatFormat.SUCCESS.wrap("Lobby "+lobby.getName()+" is now CLOSED"));
						
			break;
		
		case "info":
			//lobby info [lobbyName]
			lobby = LobbyManager.getLobby(args[1]);
			if(lobby == null){
				sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+args[1]));
				return true;
			}
			sender.sendMessage(lobby.getInfo());
			break;
		default:
			return false;
		}
		
		return true;
	}
	
	void sendToOps(String message){
		
	}
}

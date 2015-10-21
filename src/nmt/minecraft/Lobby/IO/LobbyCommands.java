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
	private static String[] commandList = {"create", "setButton","setExitButton", "setLocation","open","close","info","help"}; //if you change this remember to add it to the help function
	
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
	
	/**
	 * Puts all commands together into one string
	 * @return comma separated command list
	 */
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
		if(args.length<2){
			return false;
		}
		args[0] = args[0].toLowerCase();
		switch(args[0]){
		case "create":
			// /lobby create [lobbyName]
			onCreateCommand(sender,args[1]);
			break;
			
		case "setbutton":
			// /lobby set [lobbyName]
			onSetButton(sender, args[1]);
			break;
			
		case "setexitbutton":
			// /lobby setExitButton [lobbyName]
			
			onSetExitButton(sender, args[1]);
			
			break;
		case "setlocation":
			// /lobby setLocation [lobbyName]
			onSetLocation(sender, args[1]);
			break;	
			
		case "open":
			// /lobby open [lobbyName]
			onOpen(sender, args[1]);
			break;
		case "close":
			// /lobby close [lobbyName]
			onClose(sender,args[1]);	
			break;
		case "info":
			//lobby info [lobbyName]
			onInfo(sender,args[1]);
			break;
		case "help":
			onHelp(sender);
		default:
			return false;
		}
		
		return true;
	}
	
	/**
	 * outputs the help message to the sender
	 * @param sender
	 */
	private void onHelp(CommandSender sender) {
		String str = ChatFormat.IMPORTANT.wrap("Command List: \n");
		str += commandsToString();
		
		str += "\nAll commands require a lobby name\n";
		
		sender.sendMessage(str);
	}

	/**
	 * outputs info about the given lobby to the sender
	 * @param sender
	 * @param lobbyName
	 */
	private void onInfo(CommandSender sender, String lobbyName) {
		Lobby lobby = LobbyManager.getLobby(lobbyName);
		if(lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		sender.sendMessage(lobby.getInfo());
	}

	/**
	 * Closes the given lobby if possible
	 * @param sender
	 * @param lobbyName
	 */
	private void onClose(CommandSender sender, String lobbyName) {
		Lobby lobby = LobbyManager.getLobby(lobbyName);
		if(lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		
		if(!lobby.getIsOpen()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Lobby "+lobby.getName()+ " is already closed."));
			return;
		}
					
		lobby.close();
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Lobby "+lobby.getName()+" is now CLOSED"));
				
		
	}

	/**
	 * Opens the given lobby if possible
	 * @param sender
	 * @param lobbyName
	 */
	private void onOpen(CommandSender sender, String lobbyName) {
		Lobby lobby = LobbyManager.getLobby(lobbyName);
		if(lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		
		if(lobby.getIsOpen()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Lobby "+lobby.getName()+ " is already open."));
			return;
		}
		
		if(!lobby.open()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not open lobby "+lobby.getName()+" is not valid"));
			return;
		}
		
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Lobby "+lobby.getName()+" is now OPEN"));
		
	}

	/**
	 * sets the lobby Location for the given lobby 
	 * @param sender
	 * @param lobbyName
	 */
	private void onSetLocation(CommandSender sender, String lobbyName) {
		if(! (sender instanceof Player)){
			ChatFormat.ERROR.wrap("You must be a player to run this command");
			return;
		}
		
		Lobby lobby = LobbyManager.getLobby(lobbyName); 
		if( lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		
		if(lobby.getIsOpen()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Lobby is open, cannot set a new lobby button!"));
			return;
		}
		
		if(!lobby.setLocation(((Player)sender).getLocation())){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not set lobby location"));
			return;
		}
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Set lobby location for "+lobby.getName()+" at: "+LobbyManager.locationToString(lobby.getLocation())));
		
		
	}

	private void onSetExitButton(CommandSender sender, String lobbyName) {
		if(! (sender instanceof Player)){
			ChatFormat.ERROR.wrap("You must be a player to run this command");
			return;
		}
		
		Lobby lobby = LobbyManager.getLobby(lobbyName); 
		if( lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		
		if(lobby.getIsOpen()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Lobby is open, cannot set a new lobby button!"));
			return;
		}
		
		if(!lobby.setExitButtonLocation(((Player)sender).getLocation())){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not set exit button location"));
			return;
		}
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Set exit button location for "+lobby.getName()+" at: "+LobbyManager.locationToString(lobby.getExitButtonLocation())));
		
	}

	private void onSetButton(CommandSender sender, String lobbyName) {
		if(! (sender instanceof Player)){
			ChatFormat.ERROR.wrap("You must be a player to run this command");
			return;
		}
		
		Lobby lobby = LobbyManager.getLobby(lobbyName); 
		if( lobby == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not find lobby: "+lobbyName));
			return;
		}
		
		if(lobby.getIsOpen()){
			sender.sendMessage(ChatFormat.ERROR.wrap("Lobby is open, cannot set a new lobby button!"));
			return;
		}
		
		if(!lobby.setButtonLocation(((Player)sender).getLocation())){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not set button location"));
			return;
		}
		
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Set button location for "+lobbyName+" at: "+LobbyManager.locationToString(lobby.getButtonLocation())));
		
	}

	private void onCreateCommand(CommandSender sender, String lobbyName){
		if(LobbyManager.getLobby(lobbyName) != null){
			sender.sendMessage(ChatFormat.ERROR.wrap("There is already a lobby with that name!"));
			return;
		}
		
		if(LobbyManager.newLobby(lobbyName) == null){
			sender.sendMessage(ChatFormat.ERROR.wrap("Could not create new lobby "+lobbyName));
			return;
		}
		
		sendToOps(sender,ChatFormat.SUCCESS.wrap("Created new lobby "+lobbyName));
	}

	void sendToOps(CommandSender sender, String message){
		Command.broadcastCommandMessage(sender,ChatFormat.SESSION.wrap("Lobby ")+ChatFormat.TEAM.wrap(sender.getName()+": ")+message);
	}
	
}

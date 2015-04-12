package nmt.minecraft.Lobby.IO;

import java.util.Arrays;
import java.util.List;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommands implements CommandExecutor{
	private static String[] commandList = {"register","help"}; //if you change this remember to add it to the help function
	
	private String aquaChat = ChatColor.AQUA+"";
	private String blueChat = ChatColor.BLUE+"";
	private String goldChat = ChatColor.GOLD+"";
	private String greenChat = ChatColor.GREEN+"";
	private String redChat = ChatColor.RED+"";
	private String boldChat = ChatColor.BOLD+"";
	private String resetChat = ChatColor.RESET+"";
	
	
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
		
		//All commands must be sent by a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("register")){
			//lobby register [name]
			if(args.length != 2){
				sender.sendMessage("Wrong number of arguments: /lobby register [name]");
				return false;
			}
			
			registerLobby(sender,args[1]);
		}
		
		return true;
	}
	
	private void registerLobby(CommandSender sender, String lobbyName){
		if(!(sender instanceof Player)){
			return;
		}
		Player player = (Player) sender;
		Lobby lobby = LobbyPlugin.lobbyPlugin.getLobby(lobbyName);
		if(lobby != null){
			sender.sendMessage(lobbyName+" is already a lobby");
			return;
		}
		
		LobbyPlugin.lobbyPlugin.getLogger().info("Creating new lobby: "+lobbyName);
		LobbyPlugin.lobbyPlugin.newLobby(lobbyName,player.getLocation());
		sender.sendMessage("Created new lobby called "+lobbyName);
	}
}

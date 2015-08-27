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
	private static String[] commandList = {"setEntrance", "setExit", "setentranceLocation", 
			"setExitLocation", "open", "close", "newLobby", "removeLobby", "help"}; //if you change this remember to add it to the help function	
	
	private String aquaChat = ChatColor.AQUA+"";
	private String blueChat = ChatColor.BLUE+"";
	private String goldChat = ChatColor.GOLD+"";
	private String greenChat = ChatColor.GREEN+"";
	private String redChat = ChatColor.RED+"";
	private String boldChat = ChatColor.BOLD+"";
	private String resetChat = ChatColor.RESET+"";
	
	/**
	 * @return the command list
	 */
	public static List<String> getCommandList(){
		return Arrays.asList(commandList);
	}
	
	/**
	 * Turns the entire command list into a string separated by commas
	 * @return a string with all commands
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
		
		//All commands must be sent by a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		}

		if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help")== false)){
			sender.sendMessage("Something went wrong... We need more arguments");
			sender.sendMessage("Valid commands are "+commandsToString()+"\n");
		}
		
		//The help menu
		if (args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(greenChat + boldChat + "===Help===" + resetChat);
			sender.sendMessage("Usage: " + redChat + "/lobby " + blueChat + "[command] " + goldChat + "[command arguments]" + resetChat);
			sender.sendMessage(greenChat + boldChat + "===Commands===" + resetChat);
			sender.sendMessage(blueChat + "setExitLocation " + goldChat + " [lobby name]" + resetChat + " Sets the location the user will teleport to when they exit the lobby");
			sender.sendMessage(blueChat + "setEntranceLocation " + goldChat + " [lobby name]" + resetChat + " Sets the location the user will teleport to when they enter the lobby");
			sender.sendMessage(blueChat + "setExit " + goldChat + " [lobby name]" + resetChat + " Sets the location of the entrance button");
			sender.sendMessage(blueChat + "setEntrance " + goldChat + " [lobby name]" + resetChat + " Sets the location the exit button");
			sender.sendMessage(blueChat + "start " + goldChat + " [lobby name]" + resetChat + " allows users to begin entering the lobby");
			sender.sendMessage(blueChat + "stop " + goldChat + " [lobby name]" + resetChat + " kicks all current lobby players and prevents more from joining");
			sender.sendMessage(blueChat + "newLobby " + goldChat + " [lobby name]" + resetChat + " creates a new lobby");
			sender.sendMessage(blueChat + "removeLobby " + goldChat + " [lobby name]" + resetChat + " removes the given lobby");
		}
		
		else if(args[0].equalsIgnoreCase("setExitLocation")){
			
		}
		
		else if(args[0].equalsIgnoreCase("setEntranceLocation")){
			
		}
		
		else if(args[0].equalsIgnoreCase("setExit")){
			
		}
		
		else if(args[0].equalsIgnoreCase("setEntrance")){
			
		}
		
		else if(args[0].equalsIgnoreCase("start")){
			
		}
		
		else if(args[0].equalsIgnoreCase("stop")){
			
		}
		
		else if(args[0].equalsIgnoreCase("newLobby")){
			
		}
		
		else if(args[0].equalsIgnoreCase("removeLobby")){
			
		}else{
			sender.sendMessage("Something went wrong...");
			sender.sendMessage("Valid commands are "+commandsToString()+"\n");
		}
		
		return true;
	}
}

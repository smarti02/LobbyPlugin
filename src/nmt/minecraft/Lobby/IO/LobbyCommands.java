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
	private static String[] commandList = {"open", "close", "help"}; //if you change this remember to add it to the help function
	
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
		return true;
	}
}

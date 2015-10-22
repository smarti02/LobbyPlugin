package nmt.minecraft.Lobby.IO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyManager;

public class LobbyTabCompleter implements TabCompleter{
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		switch(args.length){
		case 1:
			return getBeginList(args[0], LobbyCommands.getCommandList());
		
		case 2:
			return getBeginList(args[1], getNames(LobbyManager.getLobbies()));
		default:
			return null;
		}
	}
	/**
	 * gets a list of lobby names as strings
	 * @param lobbies The lobbies to look through
	 * @return
	 */
	private List<String> getNames(Collection<Lobby> lobbies){
		List<String> list = new LinkedList<String>();
		
		if(lobbies == null || lobbies.isEmpty())
			return list;
		
		for(Lobby l : lobbies){
			if(l != null)
				list.add(l.getName());
		}
		
		return list;
	}
	
	/**
	 * Gets a list of strings from the totalList that begin with the key
	 * @param key
	 * @param totalList
	 * @return
	 */
	private List<String> getBeginList(String key, List<String> totalList){
		List<String> list = new ArrayList<String>();
		
		if(totalList == null || totalList.isEmpty())
			return list;
		
		for(String s : totalList){
			if(startsWithIgnoreCase(s,key)){
				list.add(s);
			}
		}
		
		return list;
	}
	
	/**
	 * Checks to see if the small string is at the beginning of the big string
	 * @param bigString
	 * @param smallString
	 * @return true if it is, false otherwise
	 */
	private static boolean startsWithIgnoreCase(String bigString, String smallString){
		bigString = bigString.toLowerCase();
		smallString = smallString.toLowerCase();
		return bigString.startsWith(smallString);
	}
}

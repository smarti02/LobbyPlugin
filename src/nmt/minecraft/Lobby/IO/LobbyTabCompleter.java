package nmt.minecraft.Lobby.IO;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyPlugin;

public class LobbyTabCompleter implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lobby")){
			List<String> list=new ArrayList<String>();
			if(args.length == 1){
				//lobby [command]
				List<String> tmpList;
				tmpList = LobbyCommands.getCommandList();//get the list of commands
				 
				if(args[0].isEmpty()){
					return tmpList;
				}
				//only put the ones that start with the given
				for(String tmpString : tmpList){
					String incomplete = args[0].toLowerCase();
					if(startsWithIgnoreCase(tmpString,incomplete)){
						list.add(tmpString);
					}
				}
			}else if(args.length == 2 && !args[0].equalsIgnoreCase("register")){
				list = new ArrayList<String>();
				for(Lobby lobby : LobbyPlugin.lobbyPlugin.getLobbies()){
					//should only match lobbies started with what's already been typed in					
					if(args[1].isEmpty() || startsWithIgnoreCase(lobby.getName(),args[1])){
						list.add(lobby.getName());
					}
				}
			}
		}
		return null;
	}
	
	private static boolean startsWithIgnoreCase(String string1, String string2){
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.startsWith(string2);
	}
}

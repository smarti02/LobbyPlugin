package nmt.minecraft.Lobby.IO;

import java.util.ArrayList;
import java.util.List;

import nmt.minecraft.Lobby.Lobby;
import nmt.minecraft.Lobby.LobbyPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class LobbyTabCompleter implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		return null;
	}
	
	private static boolean startsWithIgnoreCase(String string1, String string2){
		string1 = string1.toLowerCase();
		string2 = string2.toLowerCase();
		return string1.startsWith(string2);
	}
}

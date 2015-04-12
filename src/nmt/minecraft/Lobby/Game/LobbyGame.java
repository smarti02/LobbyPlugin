/**
 * All new games need to extend this class and be added to the array below
 * all games need to be saved with configfiles with the format MapName.yml inside of the folder with the gametype
 * ie configs/Regicide/netherRelm.yml or configs/HungerGames/
 */
package nmt.minecraft.Lobby.Game;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Stephanie Martinez
 *
 */
public abstract class LobbyGame {
	private ArrayList<Player> playerList;
	private Location exitLocation;
	public static String[] gameType={"Regicide","HungerGames", "Adventure"};
	
	public abstract void startGame();
	
	public abstract void endGame();
	
	public abstract void leaveGame();
	
	public abstract void addPlayer(Player player);
}

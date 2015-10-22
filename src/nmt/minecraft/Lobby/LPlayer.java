package nmt.minecraft.Lobby;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class LPlayer {

	private Player player;
	private Location lastLocation;
	private int savedExp;
	private double savedHealth;
	private ItemStack[] saveInventory;
	private ItemStack[] saveArmor;
	private GameMode saveGamemode;
	/*
	 *Stuff I need to save:
	 *location 
	 *experience
	 *health
	 *items
	 *armor
	 *gamemode
	 */
	
	/*
	 * Preserving items is not currently working TODO 
	 */
	public LPlayer(Player player){
		this.player = player;
		//save all the information
		
		this.lastLocation = player.getLocation();
		this.savedExp = player.getTotalExperience();
		this.savedHealth = player.getHealth();
		this.saveGamemode = player.getGameMode();
		this.saveItems();
	}	
	
	/**
	 * Takes the player's current status and saves it in this object
	 * THIS SHOULD NEVER BE CALLED OUTSIDE CONSTRUCTOR
	 */
	private void saveItems(){
		PlayerInventory inv = this.player.getInventory();
		ItemStack[] origional = inv.getContents();
		ItemStack[] armor = inv.getArmorContents();
		
		saveInventory = new ItemStack[origional.length];
		saveArmor = new ItemStack[armor.length];
		
		//save stuff in inventory
		for(int i=0; i<origional.length ;i++){
			if(origional[i]!=null){
				saveInventory[i] = new ItemStack(origional[i]);
			}
		}
		
		//dont forget the armor
		for(int i=0; i<armor.length ;i++){
			if(armor[i]!=null){
				saveArmor[i] = new ItemStack(armor[i]);
			}
		}
	}
	
	/**
	 * loads the saved items back into the player's inventory
	 */
	private void loadItems(){
		this.player.getInventory().clear();
		this.player.getInventory().setContents(saveInventory);
		this.player.getInventory().setArmorContents(saveArmor);
	}
	
	/**
	 * moves the player to the given location
	 */
	public void moveTo(Location location){
		if(player!=null)
			player.teleport(location);
	}
	
	public String getName(){
		if(player != null)
			return player.getName();
		return "";
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void restore(){
		moveTo(lastLocation);
		player.setTotalExperience(savedExp);
		player.setHealth(savedHealth);
		player.setGameMode(saveGamemode);
		loadItems();
	}
	
}

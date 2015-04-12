package nmt.minecraft.Lobby;

import org.bukkit.entity.Player;

public class LPlayer {
	private Player player;
	private String gameTypeVote;
	private String gameMapVote;
	LPlayer(Player player){
		super();
		this.player = player;
		this.gameTypeVote="";
		this.gameMapVote="";
	}
	
	public Player getPlayer(){
		return this.player;
	}

	public String getGameTypeVote() {
		return gameTypeVote;
	}

	public void setGameTypeVote(String gameTypeVote) {
		this.gameTypeVote = gameTypeVote;
	}

	public String getGameMapVote() {
		return gameMapVote;
	}

	public void setGameMapVote(String gameMapVote) {
		this.gameMapVote = gameMapVote;
	}
}

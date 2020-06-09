package main.com.amazon.ask.colorpicker;

import java.util.ArrayList;
import java.util.Collections;

public class GameMatch {
	private ArrayList<Player> players;

	public GameMatch() {
		players = new ArrayList<>();
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public ArrayList<Player> getRawList() {
		return players;
	}

	public int numberOfPlayers() {
		return players.size();
	}

	public Player findNamedPlayer(Player player) {
		for (Player p : players) {
			if(p.getName().equals(player.getName())) {
				return p;
			}
		}
		return null;
	}

	public Player getCurrentWinner() {
		return Collections.max(this.players);
	}
}
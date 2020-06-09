package main.com.amazon.ask.colorpicker;

public class Player implements Comparable<Player> {
	private String name;
	private int points;

	public Player(String name, int points) {
		this.name = name;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int compareTo(Player st) {
		return Integer.compare(this.getPoints(), st.getPoints());
	}
}
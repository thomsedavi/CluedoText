package cluedo;

import java.util.ArrayList;
import java.util.List;

/**
 * Rooms need to know about: - the suspects they contain. - the weapons they
 * contain. - the exits that they have.
 *
 * @author David Thomsen & Pauline Kelly
 *
 */
public class Room extends Card {

	public final String name;
	private List<Suspect> suspects;
	private List<Weapon> weapons;
	private List<Exit> exits;

	public Room(String name) {
		this.name = name;
		this.suspects = new ArrayList<Suspect>();
		this.weapons = new ArrayList<Weapon>();
		this.exits = new ArrayList<Exit>();
	}

	public void addExit(Exit exit) {
		exits.add(exit);
	}

	public void addSuspect(Suspect suspect) {
		suspects.add(suspect);
	}

	public void removeSuspect(Suspect suspect) {
		suspects.remove(suspect);
	}

	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}

	public void removeWeapon(Weapon weapon) {
		weapons.remove(weapon);
	}

	/**
	 * When a RoomTile is drawn it will ask the Room for what to draw based on
	 * the position integer or that tile:
	 *
	 * position = 0: displays standard Room tile.
	 *
	 * position = 1 to 6: displays Suspect in this room from List in associated
	 * Room, standard room tile otherwise.
	 *
	 * position = -1 to -6: displays Weapons in this room from List in
	 * associated Room, standard room tile otherwise.
	 *
	 * @return the code for displaying the room on the board.
	 */
	public String getCode(int position) {
		if (position == 0) {
			return "\u2591\u2591";
		} else if (position > 0) {
			if (suspects.size() >= position)
				return suspects.get(position - 1).getCode();
			else
				return "\u2591\u2591";
		} else {
			if (weapons.size() >= Math.abs(position))
				return weapons.get(Math.abs(position) - 1).getCode();
			else
				return "\u2591\u2591";
		}
	}

	public String getName() {
		return name;
	}

	/**
	 * Shows all the exits out of the room.
	 */
	public void showExits() {
		for (Exit x : exits) {
			x.activate();
		}
	}

	/**
	 * Stops showing all the exits out of the room.
	 */
	public void hideExits() {
		for (Exit x : exits) {
			x.deactivate();
		}
	}

	/**
	 * Get all the exits for a particular room.
	 *
	 * @return The list of all possible exits from a room.
	 */
	public List<Exit> getExits() {
		return exits;
	}
}
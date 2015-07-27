import java.util.ArrayList;
import java.util.List;

public class Room {

	final String name;
	private List<Suspect> suspects;
	private List<Weapon> weapons;
	private List<Exit> exits;

	public Room(String name) {
		this.name = name;
		suspects = new ArrayList<Suspect>();
		weapons = new ArrayList<Weapon>();
		exits = new ArrayList<Exit>();
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

	public String getCode(int position) {
		if (position == 0) {
			return "\u2591\u2591";
		}
		else if (position > 0) {
			if (suspects.size() >= position)
				return suspects.get(position - 1).getCode();
			else
				return "\u2591\u2591";
		}
		else {
			if (weapons.size() >= Math.abs(position))
				return weapons.get(Math.abs(position) - 1).getCode();
			else
				return "\u2591\u2591";
		}
	}

	public String getName() {
		return name;
	}

	public void showExits() {
		for (Exit x : exits) {
			x.activate();
		}
	}

	public void hideExits() {
		for (Exit x : exits) {
			x.deactivate();
		}
	}

	public List<Exit> getExits() {
		return exits;
	}
}
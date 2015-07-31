package cluedo;

public class Hud {

	Suspect[] suspects;

	public Hud(Suspect[] suspects) {
		this.suspects = suspects;
	}

	public String display(int y, Suspect suspect, boolean teleport) {
		if (y < suspects.length)
			if (suspect.equals(suspects[y]))
				return suspects[y].getName() + " *";
			else
				return suspects[y].getName();
		else if (suspects.length + 1 == y)
			return "Enter 'C' to see your Cards";

		if (!teleport)
			if (suspects.length + 2 == y)
				return "Enter 'D' to roll the Dice";
			else if (suspects.length + 3 == y)
				return "Enter 'A' to make an Accusation";
			else
				return "";
		else
			if (suspects.length + 2 == y)
				return "Enter 'D' to roll the Dice";
			else if (suspects.length + 3 == y)
				return "Enter 'T' to Teleport";
			else if (suspects.length + 4 == y)
				return "Enter 'A' to make an Accusation";
			else
				return "";
	}

}

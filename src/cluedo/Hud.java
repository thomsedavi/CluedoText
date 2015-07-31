package cluedo;

public class Hud {

    public enum STATUS {
        START_TURN, SHOW_CARDS, MOVE_PIECE, MAKE_ACCUSATION, MAKE_SUGGESTION, REVEAL_CARD, AWAIT_PLAYER;
    }

    Suspect[] suspects;

    public Hud(Suspect[] suspects) {
        this.suspects = suspects;
    }

    public String display(int y, Player player, STATUS status,
            boolean teleport) {
        switch (status) {
        case START_TURN:
            return startTurn(y, player, teleport);
        case SHOW_CARDS:
            return showCards(y, player);
        }
        return "";
    }

    private String showCards(int y, Player player) {
        if (y == 0)
            return player.getName() + "'s Cards:";
        else

            return "";
    }

    private String startTurn(int y, Player player, boolean teleport) {
        Suspect suspect = player.getSuspect();

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
        else if (suspects.length + 2 == y)
            return "Enter 'D' to roll the Dice";
        else if (suspects.length + 3 == y)
            return "Enter 'T' to Teleport";
        else if (suspects.length + 4 == y)
            return "Enter 'A' to make an Accusation";
        else
            return "";
    }

}
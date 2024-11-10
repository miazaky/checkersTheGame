
public class Player {
    private char piece;
    private String playerName;

    public Player(char piece, String name) {
        this.piece = piece;
        this.playerName = name;
    }

    public char getPiece() {
        return piece;
    }

    public String getPlayerName() {
        return playerName;
    }
}

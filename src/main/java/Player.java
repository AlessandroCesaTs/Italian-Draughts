import java.util.List;

public class Player {
    private final String name;
    public final Team team;

    public Player(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    //List<Piece> getPieces() {}
    public String getName(){
        return name;
    }
    public Team getTeam(){
        return team;
    }
}

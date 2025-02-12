package Models;

import java.util.List;

public class GameCompleteDto {
    public int totalTicks;
    public List<PlayerResult> players;
    public List<Integer> worldSeeds;
    public GameObject winningBot;
}

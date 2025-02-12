package Cadevue;

import Models.GameObject;
import Models.GameState;

/** Store Context of the Game */
public class GameContext {
    private static GameState gameState;
    private static GameObject player;

    public static void setContext(GameState gameState, GameObject player) {
        GameContext.gameState = gameState;
        GameContext.player = player;

        BotUtils.resetCache();
    }

    public static GameState getGameState() {
        return GameContext.gameState;
    }

    public static GameObject getPlayer() {
        return GameContext.player;
    }
}

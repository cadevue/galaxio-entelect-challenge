package Cadevue;

import Models.GameObject;
import Models.GameState;

/** Store Context of the Game */
public class GameContext {
    private static GameState gameState;
    private static GameObject player;
    private static boolean isSupernovaFired = false;

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

    public static boolean isSupernovaFired() {
        return isSupernovaFired;
    }

    public static void setSupernovaFired(boolean isSupernovaFired) {
        GameContext.isSupernovaFired = isSupernovaFired;
    }
}

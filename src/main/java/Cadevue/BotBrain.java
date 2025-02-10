package Cadevue;

import Models.GameObject;
import Models.GameState;
import Models.PlayerAction;

/* Class that decide which action should be done based on the state scores */
public class BotBrain {
    // Initialization of States
    private final BotState[] BOT_STATES = {};

    public PlayerAction GetBotAction(GameState gameState, GameObject player) {
        BotState bestState = GetBestAction(gameState, player);
        PlayerAction action = bestState.GetAction();

        return action;
    }

    private BotState GetBestAction(GameState gameState, GameObject player) {
        return null;
    }
}
package Cadevue;

import Models.GameObject;
import Models.GameState;
import Models.PlayerAction;

/* Class that decide which action should be done based on the state scores */
public class BotBrain {
    // Initialization of States
    private final IBotState[] BOT_STATES = {};

    // Return what acttion should the bot do
    public PlayerAction getBotAction(GameState gameState, GameObject player) {
        GameContext.setContext(gameState, player);

        IBotState bestState = calculateBestState();
        PlayerAction action = bestState.GetAction();

        return action;
    }

    // Calculate the best state based on each state scores
    private IBotState calculateBestState() {
        float bestScore = Integer.MIN_VALUE;
        IBotState bestState = null;

        for (IBotState botState : BOT_STATES) {
            int score = botState.GetStateScore();
            if (score > bestScore) {
                bestScore = score;
                bestState = botState;
            }
        }

        return bestState;
    }
}
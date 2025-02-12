package Cadevue;

import Cadevue.States.*;

import Models.GameObject;
import Models.GameState;
import Models.PlayerAction;

/* Class that decide which action should be done based on the state scores */
public class BotBrain {
    // Initialization of States
    private final IBotState STATE_GATHERFOOD = new GatherFood();
    private final IBotState STATE_GETSUPERFOOD = new GetSuperFood();

    private final IBotState[] BOT_STATES = { 
        STATE_GATHERFOOD,
        STATE_GETSUPERFOOD
    };

    // Return what acttion should the bot do
    public PlayerAction getBotAction(GameState gameState, GameObject player) {
        GameContext.setContext(gameState, player);

        IBotState bestState = calculateBestState();
        PlayerAction action = bestState.getAction();

        System.out.println("State: " + bestState.toString());

        return action;
    }

    // Calculate the best state based on each state scores
    private IBotState calculateBestState() {
        float bestScore = Integer.MIN_VALUE;
        IBotState bestState = null;

        for (IBotState botState : BOT_STATES) {
            float score = botState.getStateScore();
            if (score > bestScore) {
                bestScore = score;
                bestState = botState;
            }
        }

        return bestState;
    }
}
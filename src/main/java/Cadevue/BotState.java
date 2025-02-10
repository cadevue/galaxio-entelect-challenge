package Cadevue;

import Models.GameObject;
import Models.GameState;
import Models.PlayerAction;

public abstract class BotState {
    /* Will be used to rank the state among others */
    abstract public int GetStateScore(GameState gameState, GameObject player);

    /* If selected, the bot will perform the action */
    abstract public PlayerAction GetAction();
}
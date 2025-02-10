package Cadevue;

import Models.PlayerAction;

public interface IBotState {
    /* Will be used to rank the state among others */
    int GetStateScore();

    /* If selected, the bot will perform the action */
    PlayerAction GetAction();
}
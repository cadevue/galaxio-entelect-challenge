package Cadevue;

import Models.PlayerAction;

public interface IBotState {
    /* Will be used to rank the state among others */
    float getStateScore();

    /* If selected, the bot will perform the action */
    PlayerAction getAction();
}
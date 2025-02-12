package Cadevue.States;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

/** The default state of the bot, gather food */
public class GatherFood implements IBotState {
    private PlayerAction action;

    public GatherFood() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        // Default state, the score is constant 25
        return 25;
    }

    @Override
    public PlayerAction getAction() {
        return goToClosestFood();
    }

    private PlayerAction goToClosestFood() {
        GameObject closestFood = BotUtils.getClosestGameObjectOfType(ObjectTypes.Food);
        if (closestFood == null) {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(), 
                GameContext.getGameState().getWorld().getCenterPoint()
            );

            action.setHeading(heading);
        } else {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(GameContext.getPlayer(), closestFood);
            action.setHeading(heading);
        }

        return action;
    }
}

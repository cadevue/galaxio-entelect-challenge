package Cadevue.States;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class PickUpSupernova implements IBotState {
    private final float PICKUP_RADIUS = 250;

    private PlayerAction action;

    public PickUpSupernova() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        GameObject closestSupernova = BotUtils.getClosestGameObjectOfType(ObjectTypes.SupernovaPickup);
        if (closestSupernova == null) {
            return 0;
        }

        float distanceToClosestSupernova = BotUtils.getDistanceTo(closestSupernova);
        distanceToClosestSupernova -= closestSupernova.getSize();
        distanceToClosestSupernova -= GameContext.getPlayer().getSize();

        if (distanceToClosestSupernova < PICKUP_RADIUS) {
            return 180;
        }

        return 0;
    }

    @Override
    public PlayerAction getAction() {
        return getSupernova();
    }

    private PlayerAction getSupernova() {
        action.setAction(PlayerActions.Forward);

        GameObject closestSupernova = BotUtils.getClosestGameObjectOfType(ObjectTypes.SupernovaPickup);
        if (closestSupernova == null) {
            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(), 
                GameContext.getGameState().getWorld().getCenterPoint()
            );

            action.setHeading(heading);
        } else {
            int heading = BotUtils.getHeading(GameContext.getPlayer(), closestSupernova);
            action.setHeading(heading);
        }

        return action;
    }
}

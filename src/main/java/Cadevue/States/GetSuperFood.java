package Cadevue.States;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class GetSuperFood implements IBotState {
    private PlayerAction action;

    public GetSuperFood() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        // Will be prioritized over GatherFood if the superfood is not too far
        GameObject closestFood = BotUtils.getClosestGameObjectOfType(ObjectTypes.Food);
        GameObject closestSuperFood = BotUtils.getClosestGameObjectOfType(ObjectTypes.SuperFood);

        if (closestFood == null || closestSuperFood == null) {
            return 0;
        }

        double distanceToClosestFood = BotUtils.getDistanceTo(closestFood);
        double distanceToClosestSuperFood = BotUtils.getDistanceTo(closestSuperFood);

        double ratio = distanceToClosestSuperFood / distanceToClosestFood;

        return (float) (130 - (ratio * 100));
    }

    @Override
    public PlayerAction getAction() {
        return goToClosestSuperFood();
    }

    private PlayerAction goToClosestSuperFood() {
        GameObject closestSuperFood = BotUtils.getClosestGameObjectOfType(ObjectTypes.SuperFood);
        if (closestSuperFood == null) {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(), 
                GameContext.getGameState().getWorld().getCenterPoint()
            );

            action.setHeading(heading);
        } else {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(GameContext.getPlayer(), closestSuperFood);
            action.setHeading(heading);
        }

        return action;
    }
}

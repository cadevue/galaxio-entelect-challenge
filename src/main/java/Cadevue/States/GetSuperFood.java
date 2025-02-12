package Cadevue.States;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

/** Will be activated if there is a superfood in the game that's worth getting */
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

        double distanceToClosestFood = BotUtils.getDistanceBetween(GameContext.getPlayer(), closestFood);
        double distanceToClosestSuperFood = BotUtils.getDistanceBetween(GameContext.getPlayer(), closestSuperFood);

        double ratio = distanceToClosestSuperFood / distanceToClosestFood;

        return (float) (325 - (ratio * 100));
    }

    @Override
    public PlayerAction getAction() {
        goToClosestSuperFood();
        return action;
    }

    private void goToClosestSuperFood() {
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
    }
}

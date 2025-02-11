package Cadevue.States;

import java.util.List;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

/** The very default state of the bot, gather food */
public class GetSuperFood implements IBotState {
    private PlayerAction action;

    public GetSuperFood() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        // Will be prioritized over GatherFood if the superfood is not too far
        GameObject closestFood = BotUtils.getClosestGameObject(
            GameContext.getPlayer(), 
            GameContext.getGameObjectsOfType(ObjectTypes.SuperFood)
        );

        GameObject closestSuperFood = BotUtils.getClosestGameObject(
            GameContext.getPlayer(), 
            GameContext.getGameObjectsOfType(ObjectTypes.SuperFood)
        );

        if (closestFood == null || closestSuperFood == null) {
            return 0;
        }

        double distanceToClosestFood = BotUtils.getDistanceBetween(GameContext.getPlayer(), closestFood);
        double distanceToClosestSuperFood = BotUtils.getDistanceBetween(GameContext.getPlayer(), closestSuperFood);

        double ratio = distanceToClosestSuperFood / distanceToClosestFood;

        return (float)ratio * 25;
    }

    @Override
    public PlayerAction getAction() {
        goToClosestSuperFood();
        return action;
    }

    private void goToClosestSuperFood() {
        List<GameObject> superFoods = GameContext.getGameObjectsOfType(ObjectTypes.SuperFood);
        if (superFoods == null || superFoods.isEmpty()) {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(), 
                GameContext.getGameState().getWorld().getCenterPoint()
            );

            action.setHeading(heading);
        } else {
            action.setAction(PlayerActions.Forward);

            GameObject closestFood = BotUtils.getClosestGameObject( GameContext.getPlayer(), superFoods );
            int heading = BotUtils.getHeading(GameContext.getPlayer(), closestFood);

            action.setHeading(heading);
        }
    }
}

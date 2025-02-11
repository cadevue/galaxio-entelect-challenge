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
        goToClosestFood();
        return action;
    }

    private void goToClosestFood() {
        List<GameObject> foods = GameContext.getGameObjectsOfType(ObjectTypes.Food);
        if (foods == null || foods.isEmpty()) {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(), 
                GameContext.getGameState().getWorld().getCenterPoint()
            );

            action.setHeading(heading);
        } else {
            action.setAction(PlayerActions.Forward);

            GameObject closestFood = BotUtils.getClosestGameObject( GameContext.getPlayer(), foods );
            int heading = BotUtils.getHeading(GameContext.getPlayer(), closestFood);

            action.setHeading(heading);
        }
    }
}

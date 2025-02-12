package Cadevue.States;

import java.util.List;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class FireSupernova implements IBotState {
    private PlayerAction action;

    public FireSupernova() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        if (GameContext.getPlayer().getSupernovaCount() == 0) {
            return 0;
        }

        return 1000;
    }

    @Override
    public PlayerAction getAction() {
        GameContext.setSupernovaFired(true);
        return fireSupernova();
    }

    private PlayerAction fireSupernova() {
        action.setAction(PlayerActions.FireSupernova);

        // Heuristic: Target biggest enemy
        List<GameObject> enemies = BotUtils.getEnemyList();
        if (enemies != null && !enemies.isEmpty()) {
            GameObject biggestEnemy = null;
            for (GameObject enemy : enemies) {
                if (biggestEnemy == null || enemy.getSize() > biggestEnemy.getSize()) {
                    biggestEnemy = enemy;
                }
            }

            int heading = BotUtils.getHeading(GameContext.getPlayer(), biggestEnemy);
            action.setHeading(heading);
        }

        return action;
    }
}

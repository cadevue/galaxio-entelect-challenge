package Cadevue.States;

import java.util.List;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class DetonateSupernova implements IBotState {
    private final float SAFE_DISTANCE_FROM_PLAYER = 170;
    private final float DETONATE_DISTANCE_FROM_ENEMY = 70;

    private PlayerAction action;

    public DetonateSupernova() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        if (!GameContext.isSupernovaFired() || !isReadyToDetonate()) {
            return 0;
        }

        return 1000;
    }

    @Override
    public PlayerAction getAction() {
        return detonateSupernova();
    }

    private boolean isReadyToDetonate() {
        GameObject supernova = BotUtils.getClosestGameObjectOfType(ObjectTypes.SupernovaBomb);
        if (supernova == null) {
            return false;
        }

        List<GameObject> enemies = BotUtils.getEnemyList();
        GameObject biggestEnemy = null;
        for (GameObject enemy : enemies) {
            if (biggestEnemy == null || enemy.getSize() > biggestEnemy.getSize()) {
                biggestEnemy = enemy;
            }
        }

        float distanceSupernovaToPlayer = BotUtils.getDistanceTo(supernova);
        distanceSupernovaToPlayer -= GameContext.getPlayer().getSize();

        float distanceSupernovaToEnemy = BotUtils.getDistance(supernova.getPosition(), biggestEnemy.getPosition());
        distanceSupernovaToEnemy -= biggestEnemy.getSize();

        if (distanceSupernovaToPlayer >= SAFE_DISTANCE_FROM_PLAYER && distanceSupernovaToEnemy <= DETONATE_DISTANCE_FROM_ENEMY) {
            return true;
        }

        float supernovaDistanceFromCenter = BotUtils.getDistance(
            supernova.getPosition(), GameContext.getGameState().getWorld().getCenterPoint()
        );

        if (supernovaDistanceFromCenter >= GameContext.getGameState().getWorld().getRadius() - 10
            && distanceSupernovaToPlayer >= SAFE_DISTANCE_FROM_PLAYER) 
        {
            return true;
        }

        return false;
    }

    private PlayerAction detonateSupernova() {
        action.setAction(PlayerActions.DetonateSupernova);
        action.setHeading(GameContext.getPlayer().getCurrHeading());

        GameContext.setSupernovaFired(false);
        return action;
    }
}

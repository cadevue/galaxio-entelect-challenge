package Cadevue.States;

import java.util.ArrayList;
import java.util.List;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;
import Models.Position;

/**  Stay Safe basically : avoid bigger player, avoid boundaries, avoid gas clouds */
public class StaySafe implements IBotState {
    private final float ZONE_TOLERANCE_BIGGER_PLAYER = 10;
    private final float ZONE_TOLERANCE_BOUNDARY = 10;
    private final float ZONE_TOLERANCE_GAS_CLOUD = 5;

    private final float DANGER_STATE_SCORE = 200;

    private PlayerAction action;

    public StaySafe() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        int playerSize = GameContext.getPlayer().getSize();

        // Bigger players around ?
        List<GameObject> enemies = BotUtils.getEnemyList();
        if (enemies != null && !enemies.isEmpty()) {
            for (GameObject enemy : enemies) {
                if (enemy.getSize() <= playerSize) {
                    continue;
                }

                float distanceToEnemy = BotUtils.getDistanceTo(enemy);
                distanceToEnemy -= enemy.getSize();
                distanceToEnemy -= playerSize;

                if (distanceToEnemy < ZONE_TOLERANCE_BIGGER_PLAYER) {
                    return DANGER_STATE_SCORE;
                }
            }
        }

        // Gas clouds around ?
        List<GameObject> gasClouds = BotUtils.getGameObjectsOfType(ObjectTypes.GasCloud);
        if (gasClouds != null && !gasClouds.isEmpty()) {
            List<GameObject> gasCloudsInArea = BotUtils.getObjectsWithinRadius(
                playerSize + ZONE_TOLERANCE_GAS_CLOUD,
                gasClouds
            );

            if (gasCloudsInArea.size() > 0) {
                return DANGER_STATE_SCORE;
            }
        }

        // Avoid boundaries
        float playerToCenterDistance = BotUtils.getDistanceBetween(
            GameContext.getPlayer().getPosition(),
            GameContext.getGameState().getWorld().getCenterPoint()
        );

        float playerToBoundaryDistance = GameContext.getGameState().getWorld().getRadius() - playerToCenterDistance;
        playerToBoundaryDistance -= playerSize;

        if (playerToBoundaryDistance < ZONE_TOLERANCE_BOUNDARY) {
            return DANGER_STATE_SCORE;
        }

        return 0;
    }

    @Override
    public PlayerAction getAction() {
        return stayInSafeZone();
    }

    private PlayerAction stayInSafeZone() {
        int playerSize = GameContext.getPlayer().getSize();
        List<Position> positionsToAvoid = new ArrayList<>();

        // Avoid bigger players
        List<GameObject> enemies = BotUtils.getEnemyList();
        if (enemies != null && !enemies.isEmpty()) {
            for (GameObject enemy : enemies) {
                if (enemy.getSize() <= playerSize) {
                    continue;
                }

                float distanceToEnemy = BotUtils.getDistanceTo(enemy);
                distanceToEnemy -= enemy.getSize();
                distanceToEnemy -= playerSize;

                if (distanceToEnemy < ZONE_TOLERANCE_BIGGER_PLAYER) {
                    positionsToAvoid.add(enemy.getPosition());
                }
            }
        }

        // Avoid gas clouds
        List<GameObject> gasClouds = BotUtils.getGameObjectsOfType(ObjectTypes.GasCloud);
        if (!(gasClouds == null) && !gasClouds.isEmpty()) {
            List<GameObject> gasCloudsInArea = BotUtils.getObjectsWithinRadius(
                playerSize + ZONE_TOLERANCE_GAS_CLOUD,
                gasClouds
            );

            for (GameObject gasCloud : gasCloudsInArea) {
                positionsToAvoid.add(gasCloud.getPosition());
            }
        }

        // Avoid boundaries
        float playerToCenterDistance = BotUtils.getDistanceBetween(
            GameContext.getPlayer().getPosition(),
            GameContext.getGameState().getWorld().getCenterPoint()
        );
        float radiusRatio = GameContext.getGameState().getWorld().getRadius() / playerToCenterDistance;
        Position closestBoundary = new Position(
            (int) (GameContext.getPlayer().getPosition().getX() * radiusRatio),
            (int) (GameContext.getPlayer().getPosition().getY() * radiusRatio)
        );

        if (BotUtils.getDistanceBetween(GameContext.getPlayer().getPosition(), closestBoundary) < ZONE_TOLERANCE_BOUNDARY) {
            positionsToAvoid.add(closestBoundary);
        }

        // Get Center of all to avoid
        if (positionsToAvoid.isEmpty()) {
            action.setAction(PlayerActions.Forward);

            int heading = BotUtils.getHeading(
                GameContext.getPlayer().getPosition(),
                GameContext.getGameState().getWorld().getCenterPoint()
            );
            action.setHeading(heading);

            return action;
        }

        Position centerToAvoid = BotUtils.getCenterOf(positionsToAvoid);
        int heading = BotUtils.getHeadingTo(centerToAvoid);
        heading = BotUtils.getOppositeHeading(heading);

        action.setAction(PlayerActions.Forward);
        action.setHeading(heading);

        return action;
    }
}
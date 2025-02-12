package Cadevue.States;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class TorpedoAttack implements IBotState {
    private final float ATTACK_WITHIN_RADIUS = 16;
    private final int SELF_MINIMUM_SIZE_TO_ATTACK = 25;
    private final int IDEAL_ENEMY_SIZE_TO_ATTACK = 60;

    private PlayerAction action;

    public TorpedoAttack() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        // Will be prioritized if size there is enemy in range and size is save to attack
        if (
            GameContext.getPlayer().getTorpedoSalvoCount() == 0 || 
            GameContext.getPlayer().getSize() < SELF_MINIMUM_SIZE_TO_ATTACK
        ) {
            return 0;
        }

        GameObject closestEnemy = BotUtils.getClosestEnemy();
        if (closestEnemy == null) {
            return 0;
        }

        float relativeAttackRange = ATTACK_WITHIN_RADIUS + GameContext.getPlayer().getSize();
        float enemySizeFactor = closestEnemy.getSize() / IDEAL_ENEMY_SIZE_TO_ATTACK;
        float distanceToClosestEnemy = BotUtils.getDistanceTo(closestEnemy) - closestEnemy.getSize();
        return (relativeAttackRange * enemySizeFactor / distanceToClosestEnemy) * 200;
    }

    @Override
    public PlayerAction getAction() {
        return attackClosestEnemy();
    }

    private PlayerAction attackClosestEnemy() {
        action.setAction(PlayerActions.FireTorpedoes);

        int heading = BotUtils.getHeadingTo(BotUtils.getClosestEnemy());
        action.setHeading(heading);

        return action;
    }
}
package Cadevue.States;

import java.util.List;
import java.util.stream.Collectors;

import Cadevue.BotUtils;
import Cadevue.GameContext;
import Cadevue.IBotState;
import Enums.ObjectTypes;
import Enums.PlayerActions;
import Models.GameObject;
import Models.PlayerAction;

public class UseShield implements IBotState {
    private final int TORPEDO_DETECT_RADIUS = 150;

    private PlayerAction action;

    public UseShield() {
        this.action = new PlayerAction();
    }

    @Override
    public float getStateScore() {
        // Will be prioritized if several torpedoes are around the player
        if (isShieldActivated() || GameContext.getPlayer().getSize() <= 30) {
            return 0;
        }

        List<GameObject> torpedoes = BotUtils.getGameObjectsOfType(ObjectTypes.TorpedoSalvo);
        if (torpedoes == null || torpedoes.isEmpty()) {
            return 0;
        }

        List<GameObject> dangerousTorpedoes = torpedoes.stream()
            .filter(torpedo -> BotUtils.getDistanceTo(torpedo) < TORPEDO_DETECT_RADIUS + GameContext.getPlayer().getSize())
            .filter(torpedo -> BotUtils.isObjectHeadingToPlayer(torpedo, (int) (GameContext.getPlayer().getSize() / 2.5f)))
            .collect(Collectors.toList());
        
        return GameContext.getPlayer().getShieldCount() * dangerousTorpedoes.size() * 60;
    }

    @Override
    public PlayerAction getAction() {
        return useShield();
    }

    private boolean isShieldActivated() {
        return GameContext.getPlayer().getEffect() >= 16;
    }

    private PlayerAction useShield() {
        action.setAction(PlayerActions.ActivateShield);
        action.setHeading(GameContext.getPlayer().getCurrHeading());

        return action;
    }
}
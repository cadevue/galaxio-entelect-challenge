package Cadevue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Enums.ObjectTypes;
import Models.GameObject;
import Models.GameState;

/** Store Context of the Game */
public class GameContext {
    private static GameState gameState;
    private static GameObject player;

    private static Map<ObjectTypes, List<GameObject>> gameObjects = new HashMap<>();

    public static void setContext(GameState gameState, GameObject player) {
        GameContext.gameState = gameState;
        GameContext.player = player;

        gameObjects.clear();
    }

    public static GameState getGameState() {
        return GameContext.gameState;
    }

    public static GameObject getPlayer() {
        return GameContext.player;
    }

    private static void calculateGameObjects() {
        for (GameObject gameObject : gameState.getGameObjects()) {
            if (!gameObjects.containsKey(gameObject.getGameObjectType())) {
                gameObjects.put(gameObject.getGameObjectType(), List.of(gameObject));
            } else {
                gameObjects.get(gameObject.getGameObjectType()).add(gameObject);
            }
        }
    }

    public static List<GameObject> getGameObjectsOfType(ObjectTypes type) {
        if (gameObjects.isEmpty()) {
            calculateGameObjects();
        }

        return gameObjects.get(type);
    }
}

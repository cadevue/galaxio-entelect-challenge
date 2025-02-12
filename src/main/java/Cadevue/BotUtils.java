package Cadevue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Enums.ObjectTypes;
import Models.GameObject;
import Models.Position;

/* Static class with collection of utility functions for the bot */
public class BotUtils {
    /** Answer Caching */
    private static Map<ObjectTypes, List<GameObject>> objectListOfType = new HashMap<>();
    private static Map<ObjectTypes, GameObject> closestObjectOfType = new HashMap<>();

    public static void resetCache() {
        objectListOfType.clear();
        closestObjectOfType.clear();
    }

    private static void calculateObjectListOfTypes() {
        for (GameObject gameObject : GameContext.getGameState().getGameObjects()) {
            if (!objectListOfType.containsKey(gameObject.getGameObjectType())) {
                objectListOfType.put(gameObject.getGameObjectType(), new ArrayList<>());
            }

            objectListOfType.get(gameObject.getGameObjectType()).add(gameObject);
        }
    }

    /** Utilities */
    public static double getDistance(Position pos1, Position pos2) {
        var xDist = Math.abs(pos1.x - pos2.x);
        var yDist = Math.abs(pos1.y - pos2.y);
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public static double getDistanceBetween(Position pos1, Position pos2) {
        return getDistance(pos1, pos2);
    }

    public static double getDistanceBetween(GameObject obj1, GameObject obj2) {
        return getDistance(obj1.getPosition(), obj2.getPosition());
    }

    public static int getHeading(Position from, Position to) {
        var direction = radToDeg(Math.atan2(
            to.y - from.y, 
            to.x - from.x
        ));

        return (direction + 360) % 360;
    }

    public static int getHeading(GameObject from, GameObject to) {
        return getHeading(from.getPosition(), to.getPosition());
    }

    public static int radToDeg(double v) {
        return (int) (v * (180 / Math.PI));
    }

    public static List<GameObject> getGameObjectsOfType(ObjectTypes type) {
        if (objectListOfType.isEmpty()) {
            calculateObjectListOfTypes();
        }

        return objectListOfType.get(type);
    }

    public static GameObject getClosestGameObjectOfType(ObjectTypes type) {
        if (closestObjectOfType.containsKey(type)) {
            return closestObjectOfType.get(type);
        }

        List<GameObject> gameObjects = getGameObjectsOfType(type);
        if (
            gameObjects == null || 
            gameObjects.isEmpty() || 
            GameContext.getPlayer() == null
        ) {
            return null;
        }

        GameObject closest = null;
        double closestDistance = Double.MAX_VALUE;
        for (GameObject gameObject : gameObjects) {
            double distance = getDistanceBetween(GameContext.getPlayer(), gameObject);
            if (distance < closestDistance) {
                closest = gameObject;
                closestDistance = distance;
            }
        }

        return closest;
    }
}

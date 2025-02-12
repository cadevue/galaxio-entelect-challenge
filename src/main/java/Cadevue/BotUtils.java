package Cadevue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Enums.ObjectTypes;
import Models.GameObject;
import Models.Position;

/* Static class with collection of utility functions for the bot */
public class BotUtils {
    /** Answer Caching */
    private static Map<ObjectTypes, List<GameObject>> objectListOfType = new HashMap<>();
    private static Map<ObjectTypes, GameObject> closestObjectOfType = new HashMap<>();

    private static List<GameObject> enemies = new ArrayList<>();
    private static GameObject closestEnemy = null;

    public static void resetCache() {
        objectListOfType.clear();
        closestObjectOfType.clear();

        enemies.clear();
        closestEnemy = null;
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
    public static float getDistance(Position pos1, Position pos2) {
        return (float) Math.sqrt(
            Math.pow(pos1.x - pos2.x, 2) + 
            Math.pow(pos1.y - pos2.y, 2)
        );
    }

    public static float getDistanceBetween(Position pos1, Position pos2) {
        return getDistance(pos1, pos2);
    }

    public static float getDistanceTo(GameObject obj) {
        return getDistance(GameContext.getPlayer().getPosition(), obj.getPosition());
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

    public static int getHeadingTo(Position to) {
        return getHeading(GameContext.getPlayer().getPosition(), to);
    }

    public static int getHeadingTo(GameObject to) {
        return getHeading(GameContext.getPlayer(), to);
    }

    public static int getOppositeHeading(int heading) {
        return (heading + 180) % 360;
    }

    public static int radToDeg(double v) {
        return (int) (v * (180 / Math.PI));
    }

    public static double degToRad(int v) {
        return v * (Math.PI / 180);
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
        if (gameObjects == null || gameObjects.isEmpty() || GameContext.getPlayer() == null) {
            return null;
        }

        GameObject closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (GameObject gameObject : gameObjects) {
            float distance = getDistanceTo(gameObject) - gameObject.getSize();
            if (distance < closestDistance) {
                closest = gameObject;
                closestDistance = distance;
            }
        }

        return closest;
    }

    public static List<GameObject> getObjectsWithinRadius(double radius) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : GameContext.getGameState().getGameObjects()) {
            if (getDistanceTo(gameObject) < radius) {
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }

    public static List<GameObject> getObjectsWithinRadius(double radius, List<GameObject> src) {
        return src.stream()
            .filter(gameObject -> (getDistanceTo(gameObject) - gameObject.getSize()) < radius)
            .collect(Collectors.toList());
    }

    public static List<GameObject> getObjectsWithinRadiusOfType(double radius, ObjectTypes type) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : getGameObjectsOfType(type)) {
            if (getDistanceTo(gameObject) < radius) {
                gameObjects.add(gameObject);
            }
        }

        return gameObjects;
    }


    public static List<GameObject> getEnemyList() {
        if (enemies == null || enemies.isEmpty()) {
            enemies = GameContext.getGameState().getPlayerGameObjects().stream()
                .filter(gameObject -> gameObject.getId() != GameContext.getPlayer().getId())
                .collect(Collectors.toList());
        }

        return enemies;
    }

    public static GameObject getClosestEnemy() {
        if (closestEnemy != null) {
            return closestEnemy;
        }

        List<GameObject> enemies = getEnemyList();
        if (enemies == null || enemies.isEmpty() || GameContext.getPlayer() == null) {
            return null;
        }

        GameObject closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (GameObject gameObject : enemies) {
            float distance = getDistanceTo(gameObject) - gameObject.getSize();
            if (distance < closestDistance) {
                closest = gameObject;
                closestDistance = distance;
            }
        }

        return closest;
    }

    public static boolean isObjectHeadingTo(GameObject obj, Position position, int degreeTolerance) {
        return Math.abs((obj.getCurrHeading() - getHeading(obj.getPosition(), position)) % 360) <= degreeTolerance;
    }

    public static boolean isObjectHeadingToPlayer(GameObject obj, int degreeTolerance) {
        return isObjectHeadingTo(obj, GameContext.getPlayer().getPosition(), degreeTolerance);
    }

    public static Position getCenterOf(List<Position> positions) {
        int x = 0;
        int y = 0;
        for (Position pos : positions) {
            x += pos.x;
            y += pos.y;
        }

        return new Position(x / positions.size(), y / positions.size());
    }
}

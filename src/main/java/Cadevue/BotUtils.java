package Cadevue;

import java.util.List;

import Enums.ObjectTypes;
import Models.GameObject;
import Models.Position;

/* Static class with collection of utility functions for the bot */
public class BotUtils {
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
        return GameContext.getGameObjectsOfType(type);
    }
}

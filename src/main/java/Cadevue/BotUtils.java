package Cadevue;

import Models.GameObject;
import Models.Position;

/* Static class with collection of utility functions for the bot */
public class BotUtils {
    public double getDistance(Position pos1, Position pos2) {
        var xDist = Math.abs(pos1.x - pos2.x);
        var yDist = Math.abs(pos1.y - pos2.y);
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public double getDistanceBetween(GameObject obj1, GameObject obj2) {
        return getDistance(obj1.getPosition(), obj2.getPosition());
    }

    public int getHeading(Position from, Position to) {
        var direction = radToDeg(Math.atan2(
            to.y - from.y, 
            to.x - from.x
        ));

        return (direction + 360) % 360;
    }

    public int radToDeg(double v) {
        return (int) (v * (180 / Math.PI));
    }
}

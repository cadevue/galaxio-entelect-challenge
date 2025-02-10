package Models;

import java.util.*;

public class GameStateDto {

    public World world;
    public Map<String, List<Integer>> gameObjects;
    public Map<String, List<Integer>> playerObjects;

    public Models.World getWorld() {
        return world;
    }

    public void setWorld(Models.World world) {
        this.world = world;
    }

    public Map<String, List<Integer>> getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(Map<String, List<Integer>> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Map<String, List<Integer>> getPlayerObjects() {
        return playerObjects;
    }

    public void setPlayerObjects(Map<String, List<Integer>> playerObjects) {
        playerObjects = playerObjects;
    }
}

package Models;

import Enums.*;
import java.util.*;

public class GameObject {

	public UUID id;

	public int size;
	public int speed;
	public int currentHeading;
	public Position position;

	public ObjectTypes gameObjectType;

	public int effect;
	public int torpedoSalvoCount;
	public int supernovaAvailable;
	public int teleporterCount;
	public int shieldCount;

	public GameObject(
		UUID id, int size, int speed, int currentHeading, 
		Position position, ObjectTypes gameObjectType, int effect, 
		int torpedoSalvoCount, int supernovaAvailable, int teleporterCount, 
		int shieldCount
	) {
		this.id = id;
		this.size = size;
		this.speed = speed;
		this.currentHeading = currentHeading;
		this.position = position;
		this.gameObjectType = gameObjectType;
		this.effect = effect;
		this.torpedoSalvoCount = torpedoSalvoCount;
		this.supernovaAvailable = supernovaAvailable;
		this.teleporterCount = teleporterCount;
		this.shieldCount = shieldCount;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getEffect(){
		return effect;
	}

	public ObjectTypes getGameObjectType() {
		return gameObjectType;
	}

	public void setGameObjectType(ObjectTypes gameObjectType) {
		this.gameObjectType = gameObjectType;
	}

	public int getTorpedoSalvoCount(){
		return this.torpedoSalvoCount;
	}

	public int getSupernovaCount(){
		return this.supernovaAvailable;
	}

	public int getTeleporterCount(){
		return this.teleporterCount;
	}

	public int getCurrHeading(){
		return this.currentHeading;
	}

	public static GameObject FromStateList(UUID id, List<Integer> stateList)
	{
		Position position = new Position(stateList.get(4), stateList.get(5));
		if (ObjectTypes.valueOf(stateList.get(3)) == ObjectTypes.Player) {
			return new GameObject(
				id, stateList.get(0), stateList.get(1), 
				stateList.get(2), position, ObjectTypes.valueOf(stateList.get(3)), 
				stateList.get(6), stateList.get(7), stateList.get(8), 
				stateList.get(9), stateList.get(10)
			);
		}

		// Else, not player
		return new GameObject(
			id, stateList.get(0), stateList.get(1), 
			stateList.get(2), position, ObjectTypes.valueOf(stateList.get(3)), 
			0, 0, 0, 0, 0
		);
  }
}
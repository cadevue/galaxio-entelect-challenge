package Models;

import Enums.*;
import java.util.*;

public class GameObject {

	private UUID id;
	private Integer size;
	private Integer speed;
	private Integer currentHeading;
	private Position position;
	private ObjectTypes gameObjectType;
	private Integer effect;
	private Integer torpedoSalvoCount;
	private Integer supernovaAvailable;
	private Integer teleporterCount;
	private Integer shieldCount;

	public GameObject(
		UUID id, Integer size, Integer speed, Integer currentHeading, 
		Position position, ObjectTypes gameObjectType,
		Integer effect = 0, Integer torpedoSalvoCount = 0, 
		Integer supernovaAvailable = 0, Integer teleporterCount = 0, 
		Integer shieldCount = 0
	) 

	{
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

	public Integer getEffect(){
		return effect;
	}

	public ObjectTypes getGameObjectType() {
		return gameObjectType;
	}

	public void setGameObjectType(ObjectTypes gameObjectType) {
		this.gameObjectType = gameObjectType;
	}

	public Integer getTorpedoSalvoCount(){
		return this.torpedoSalvoCount;
	}

	public Integer getSupernovaCount(){
		return this.supernovaAvailable;
	}

	public Integer getTeleporterCount(){
		return this.teleporterCount;
	}

	public Integer getCurrHeading(){
		return this.currentHeading;
	}

	public static GameObject FromStateList(UUID id, List<Integer> stateList)
	{
		Position position = new Position(stateList.get(4), stateList.get(5));
		return new GameObject(
			id, stateList.get(0), stateList.get(1), stateList.get(2), 
			position, ObjectTypes.valueOf(stateList.get(3)), 
			stateList.get(6), stateList.get(7), 
			stateList.get(8), stateList.get(9), 
			stateList.get(10)
		);
	}
}
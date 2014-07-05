package com.nightmaresseller.devtests.textquest.model;

import java.util.List;

public class GameStep {

	private String id;
	private String title;
	private String description;
	private List<GameAction> actions;
	
	public GameStep(String id) throws NullPointerException {
		setId(id);
	}
	
	public boolean addAction(GameAction action) throws NullPointerException {
		if (action == null) {
			throw new NullPointerException("null value for argument \"action\" is not expected");
		}
		return actions.add(action);
	}
	public boolean removeAction(GameAction action) {
		return actions.remove(action);
	}
	public GameAction getAction(int index) throws IndexOutOfBoundsException {
		return actions.get(index);
	}
	public int getActionsCount() {
		return actions.size();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		if (id == null) {
			throw new NullPointerException();
		}
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}

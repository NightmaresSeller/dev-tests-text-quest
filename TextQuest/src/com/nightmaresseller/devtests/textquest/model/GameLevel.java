package com.nightmaresseller.devtests.textquest.model;

public class GameLevel {

	private String id;
	private String title;
	private String description;
	private String initStepId;
	
	public GameLevel(String id) throws NullPointerException  {
		setId(id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) throws NullPointerException {
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
	public String getIntro() {
		return description;
	}
	public void setIntro(String intro) {
		this.description = intro;
	}
	public String getInitStepId() {
		return initStepId;
	}
	public void setInitStepId(String initStepId) {
		this.initStepId = initStepId;
	}
	
}

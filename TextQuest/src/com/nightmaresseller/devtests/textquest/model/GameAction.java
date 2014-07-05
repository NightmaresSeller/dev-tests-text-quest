package com.nightmaresseller.devtests.textquest.model;

public class GameAction {
	
	private String title;
	private String result;
	/**
	 * Ідентифікатор кроку, до якого перейде гра
	 * після виконання цієї дії.
	 * Можливий перехід лише в межах локації
	 */
	private String jumpStepId;
	/**
	 * Ідентифікатор локації, до якої перейде гра
	 * після виконання цієї дії.
	 * При зміні локації jumpStepId ігнорується,
	 * гра переходить до початкового кроку заданої локації
	 */
	private String jumpLevelId;
	/**
	 * Ознака того, що дія веде до смерті персонажа.
	 * Якщо true, jumpStepId і jupmLevelId ігноруються
	 */
	private boolean isDeath;
	/**
	 * Ознака того, що дія веде до виграшу.
	 * Якщо true, jumpStepId і jupmLevelId ігноруються
	 */
	private boolean isVictory;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getJumpStepId() {
		return jumpStepId;
	}
	public void setJumpStepId(String jumpStepId) {
		this.jumpStepId = jumpStepId;
	}
	public String getJumpLevelId() {
		return jumpLevelId;
	}
	public void setJumpLevelId(String jumpLevelId) {
		this.jumpLevelId = jumpLevelId;
	}
	public boolean isDeath() {
		return isDeath;
	}
	public void setDeath(boolean isDeath) {
		this.isDeath = isDeath;
	}
	public boolean isVictory() {
		return isVictory;
	}
	public void setVictory(boolean isVictory) {
		this.isVictory = isVictory;
	}
	
}

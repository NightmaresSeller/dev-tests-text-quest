package com.nightmaresseller.devtests.textquest.model;

import com.nightmaresseller.devtests.textquest.GameModelEventSource;
import com.nightmaresseller.devtests.textquest.GameModelEventSourceSupport;
import com.nightmaresseller.devtests.textquest.GameModelListener;
import com.nightmaresseller.devtests.textquest.dao.GameDAO;
import com.nightmaresseller.devtests.textquest.exceptions.GameDAOException;
import com.nightmaresseller.devtests.textquest.exceptions.GameModelException;

public class GameModel implements GameModelEventSource {
	
	private GameDAO gameData;
	private GameLevel currentLevel;
	private GameStep currentStep;
	private int decission;
	private GameModelEventSourceSupport eventSource;
	
	public GameModel(GameDAO gameData) {
		eventSource = new GameModelEventSourceSupport(this);
		this.gameData = gameData;
	}
	
	@Override
	public void addGameModelListener(GameModelListener listener) {
		eventSource.addGameModelListener(listener);
	}
	@Override
	public void removeGameModelListener(GameModelListener listener) {
		eventSource.removeGameModelListener(listener);
	}
	@Override
	public void fireGameStarted() {
		eventSource.fireGameStarted();
	}
	@Override
	public void fireLevelChanged() {
		eventSource.fireLevelChanged();
	}
	@Override
	public void fireStepChanged() {
		eventSource.fireStepChanged();
	}
	@Override
	public void fireDecissionChanged() {
		eventSource.fireDecissionChanged();
	}
	@Override
	public void fireActionAccepted(GameAction action) {
		eventSource.fireActionAccepted(action);
	}
	
	public int getDecission() {
		return decission;
	}

	public void setDecission(int decission) {
		this.decission = decission;
		fireDecissionChanged();
	}

	public GameLevel getCurrentLevel() {
		return currentLevel;
	}
	
	public void setCurrentLevel(String levelId) throws GameModelException {
		try {
			currentLevel = gameData.getLevel(levelId);
		} catch (GameDAOException ex) {
			throw new GameModelException("Error loading level with id\"" + levelId + "\"", ex);
		}
		if (currentLevel == null) {
			throw new GameModelException("Can't load level with id \"" + levelId + "\"");
		}
		currentStep = null;
		fireLevelChanged();
	}

	public GameStep getCurrentStep() {
		return currentStep;
	}
	
	public void setCurrentStep(String stepId) throws GameModelException {
		try {
			currentStep = gameData.getStep(currentLevel, stepId);
		} catch (GameDAOException ex) {
			throw new GameModelException("Error loading step with id\"" + stepId
					+ "\" of level with id \"" + currentLevel.getId() + "\"", ex);
		}
		fireStepChanged();
		decission = -1;
		fireDecissionChanged();
	}
	
	public String getGameInitLevelId() throws GameModelException {
		String levelId = null;
		try {
			levelId = gameData.getEntry().getInitLevelId();
		} catch (GameDAOException ex) {
			throw new GameModelException("Error retrieving game initial level id", ex);
		}
		return levelId;
	}
	
	public String getGameTitle() throws GameModelException {
		String title = null;
		try {
			title = gameData.getEntry().getTitle();
		} catch (GameDAOException ex) {
			throw new GameModelException("Error retrieving game title", ex);
		}
		return title;
	}
	
	public String getGameIntro() throws GameModelException {
		String intro = null;
		try {
			intro = gameData.getEntry().getIntro();
		} catch (GameDAOException ex) {
			throw new GameModelException("Error retrieving game intro", ex);
		}
		return intro;
	}
	
}

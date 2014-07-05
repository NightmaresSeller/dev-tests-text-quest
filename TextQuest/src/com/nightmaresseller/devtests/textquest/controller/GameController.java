package com.nightmaresseller.devtests.textquest.controller;

import com.nightmaresseller.devtests.textquest.exceptions.GameModelException;
import com.nightmaresseller.devtests.textquest.model.GameAction;
import com.nightmaresseller.devtests.textquest.model.GameModel;

public class GameController {
	
	private GameModel model;

	public GameController(GameModel model) {
		this.model = model;
	}
	
	public GameModel getModel() {
		return model;
	}

	public void setModel(GameModel model) {
		this.model = model;
	}

	public void startGame() throws GameModelException {
		model.fireGameStarted();
		this.loadLevel(model.getGameInitLevelId());
	}
	
	public void loadLevel(String levelId) throws GameModelException {
		model.setCurrentLevel(levelId);
		model.setCurrentStep(model.getCurrentLevel().getInitStepId());
		model.setDecission(-1);
	}
	
	public void changeDecission(int decission) {
		model.setDecission(decission);
	}
	
	public void makeDecission() throws GameModelException {
		if (model.getDecission() >= 0) {
			GameAction action = model.getCurrentStep().getAction(model.getDecission());
			model.fireActionAccepted(action);
			executeAction(action);
		}
	}
	
	public void death() {
		
	}
	
	public void victory() {
		
	}
	
	private void executeAction(GameAction action) throws GameModelException {
		if (action.isVictory()) {
			victory();
		} else {
			if (action.isDeath()) {
				death();
			} else {
				if (action.getJumpLevelId() != null) {
					loadLevel(action.getJumpLevelId());
				} else {
					if (action.getJumpStepId() != null) {
						model.setCurrentStep(action.getJumpStepId());
					} else {
						throw new GameModelException("Action \"" + action.getTitle() + "\" doesn't define any behaviour");
					}
				}
			}
		}
	}
	
}

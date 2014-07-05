package com.nightmaresseller.devtests.textquest.view;

import com.nightmaresseller.devtests.textquest.GameModelListener;
import com.nightmaresseller.devtests.textquest.controller.GameController;
import com.nightmaresseller.devtests.textquest.exceptions.GameModelException;
import com.nightmaresseller.devtests.textquest.model.GameAction;
import com.nightmaresseller.devtests.textquest.model.GameModel;

public class GameConsoleView implements GameModelListener {
	
	private GameController controller;
	private Object interactionLock;
	private boolean readyForInteraction;
	
	public GameConsoleView(GameController controller) {
		this.controller = controller;
		this.controller.getModel().addGameModelListener(this);
		interactionLock = new Object();
		synchronized (interactionLock) {
			readyForInteraction = false;
		}
	}
	
	public void interact() {
		synchronized (interactionLock) {
			if (readyForInteraction) {
				// TODO Implement interaction logic
			}
		}
	}

	@Override
	public void levelChanged(GameModel model) {
		System.out.println("Level " + model.getCurrentLevel().getTitle() );
		System.out.println(model.getCurrentLevel().getIntro());
	}

	@Override
	public void stepChanged(GameModel model) {
		System.out.println("Step " + model.getCurrentStep().getTitle());
		System.out.println(model.getCurrentStep().getDescription());
		for (int i = 0; i < model.getCurrentStep().getActionsCount(); i++) {
			System.out.println(model.getCurrentStep().getAction(i).getTitle());
		}
		readyForInteraction = true;
	}

	@Override
	public void decissionChanged(GameModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(GameModel model, GameAction action) {
		System.out.println(action.getResult());
		readyForInteraction = false;
	}

	@Override
	public void gameStarted(GameModel model) {
		System.out.println("Game started");
		try {
			System.out.println(model.getGameTitle());
			System.out.println(model.getGameIntro());
		} catch (GameModelException e) {
			System.out.println("Something went wrong. " + e.getMessage());
		}
	}

}

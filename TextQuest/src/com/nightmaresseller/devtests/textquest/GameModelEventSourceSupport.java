package com.nightmaresseller.devtests.textquest;

import java.util.HashSet;
import java.util.Set;

import com.nightmaresseller.devtests.textquest.model.GameAction;
import com.nightmaresseller.devtests.textquest.model.GameModel;

public class GameModelEventSourceSupport implements GameModelEventSource {
	
	private GameModel model;
	private Set<GameModelListener> listeners;
	
	public GameModelEventSourceSupport(GameModel model) {
		listeners = new HashSet<GameModelListener>();
	}

	@Override
	public void fireGameStarted() {
		for (GameModelListener listener : listeners) {
			listener.gameStarted(model);
		}
	}
	
	@Override
	public void fireLevelChanged() {
		for (GameModelListener listener : listeners) {
			listener.levelChanged(model);
		}
	}

	@Override
	public void fireStepChanged() {
		for (GameModelListener listener : listeners) {
			listener.stepChanged(model);
		}
	}

	@Override
	public void fireDecissionChanged() {
		for (GameModelListener listener : listeners) {
			listener.decissionChanged(model);
		}
	}

	@Override
	public void fireActionAccepted(GameAction action) {
		for (GameModelListener listener : listeners) {
			listener.actionPerformed(model, action);
		}
	}

	@Override
	public void addGameModelListener(GameModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeGameModelListener(GameModelListener listener) {
		listeners.remove(listener);
	}

}

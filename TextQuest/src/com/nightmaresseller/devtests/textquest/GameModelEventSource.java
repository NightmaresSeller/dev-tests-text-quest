package com.nightmaresseller.devtests.textquest;

import com.nightmaresseller.devtests.textquest.model.GameAction;

public interface GameModelEventSource {
	
	public void addGameModelListener(GameModelListener listener);
	public void removeGameModelListener(GameModelListener listener);
	
	public void fireGameStarted();
	public void fireLevelChanged();
	public void fireStepChanged();
	public void fireDecissionChanged();
	public void fireActionAccepted(GameAction action);
	
}

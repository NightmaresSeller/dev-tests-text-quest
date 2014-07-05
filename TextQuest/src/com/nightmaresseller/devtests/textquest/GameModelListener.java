package com.nightmaresseller.devtests.textquest;

import com.nightmaresseller.devtests.textquest.model.GameAction;
import com.nightmaresseller.devtests.textquest.model.GameModel;

public interface GameModelListener {

	public void gameStarted(GameModel model);
	public void levelChanged(GameModel model);
	public void stepChanged(GameModel model);
	public void decissionChanged(GameModel model);
	public void actionPerformed(GameModel model, GameAction action);
	
}

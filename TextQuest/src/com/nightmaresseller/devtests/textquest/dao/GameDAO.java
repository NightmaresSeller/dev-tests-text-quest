package com.nightmaresseller.devtests.textquest.dao;

import com.nightmaresseller.devtests.textquest.exceptions.GameDAOException;
import com.nightmaresseller.devtests.textquest.model.GameEntry;
import com.nightmaresseller.devtests.textquest.model.GameLevel;
import com.nightmaresseller.devtests.textquest.model.GameStep;

public interface GameDAO {

	public GameEntry getEntry() throws GameDAOException;
	public GameLevel getLevel(String levelId) throws GameDAOException;
	public GameStep getStep(GameLevel level, String stepId) throws GameDAOException;
	
}

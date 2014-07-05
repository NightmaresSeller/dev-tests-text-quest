package com.nightmaresseller.devtests.textquest.dao;

import java.io.File;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.nightmaresseller.devtests.textquest.exceptions.GameDAOException;
import com.nightmaresseller.devtests.textquest.model.GameAction;
import com.nightmaresseller.devtests.textquest.model.GameEntry;
import com.nightmaresseller.devtests.textquest.model.GameLevel;
import com.nightmaresseller.devtests.textquest.model.GameStep;

public class GameXMLDAO implements GameDAO {
	
	private static class TerminateParsingException extends SAXException {
		
		private static final long serialVersionUID = 1L;
		
	}
	
	private static class GameDataHandler extends DefaultHandler {
		
		private String levelId;
		private String stepId;
		private GameEntry entry;
		private GameLevel level;
		private GameStep step;
		private GameAction currentAction;
		
		private static int STAGE_INITIAL = 0;
		private static int STAGE_QUEST = 1;
		private static int STAGE_LEVEL = 2;
		private static int STAGE_STEP = 3;
		private static int STAGE_ACTION = 4;
		private int stage;
		
		public GameDataHandler() {
			reset();
		}
		
		public GameLevel getLevel() {
			return level;
		}

		public GameStep getStep() {
			return step;
		}

		public GameEntry getEntry() {
			return entry;
		}

		public void setQuery(String levelId, String stepId) {
			reset();
			this.levelId = levelId;
			this.stepId = stepId;
		}
		
		public void reset() {
			this.stage = STAGE_INITIAL;
			this.levelId = null;
			this.stepId = null;
			this.entry = null;
			this.level = null;
			this.step = null;
			this.currentAction = null;
		}
		
		private void terminateParser() throws TerminateParsingException {
			throw new TerminateParsingException();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			switch (localName) {
				case "quest": {
					if (stage != STAGE_INITIAL) {
						throw new SAXException();
					}
					stage = STAGE_QUEST;
					entry = new GameEntry();
					entry.setInitLevelId(atts.getValue("initlevel"));
					entry.setTitle(atts.getValue("title"));
					entry.setIntro(atts.getValue("intro"));
					if (levelId == null && stepId == null) {
						terminateParser();
					}
					break;
				}
				case "level": {
					if (stage != STAGE_QUEST) {
						throw new SAXException();
					}
					stage = STAGE_LEVEL;
					String lId = atts.getValue("id");
					if (lId == levelId) {
						level = new GameLevel(lId);
						level.setTitle(atts.getValue("title"));
						level.setIntro(atts.getValue("intro"));
						level.setInitStepId(atts.getValue("initstep"));
						if (stepId == null) {
							terminateParser();
						}
					}
					break;
				}
				case "step": {
					if (stage != STAGE_LEVEL) {
						throw new SAXException();
					}
					stage = STAGE_STEP;
					if (level != null) {
						String sId = atts.getValue("id");
						if (sId == stepId) {
							step = new GameStep(sId);
							step.setTitle(atts.getValue("title"));
							step.setDescription(atts.getValue("description"));
						}
					}
					break;
				}
				case "action": {
					if (stage != STAGE_STEP) {
						throw new SAXException();
					}
					stage = STAGE_ACTION;
					if (step != null) {
						currentAction = new GameAction();
						currentAction.setTitle(atts.getValue("title"));
						currentAction.setResult(atts.getValue("result"));
					}
					break;
				}
				case "jumptolevel": {
					if (stage != STAGE_ACTION) {
						throw new SAXException();
					}
					if (currentAction != null) {
						currentAction.setJumpLevelId(atts.getValue("id"));
					}
					break;
				}
				case "jumptostep": {
					if (stage != STAGE_ACTION) {
						throw new SAXException();
					}
					if (currentAction != null) {
						currentAction.setJumpStepId(atts.getValue("id"));
					}
					break;
				}
				case "death": {
					if (stage != STAGE_ACTION) {
						throw new SAXException();
					}
					if (currentAction != null) {
						currentAction.setDeath(true);
					}
					break;
				}
				case "victory": {
					if (stage != STAGE_ACTION) {
						throw new SAXException();
					}
					if (currentAction != null) {
						currentAction.setVictory(true);
					}
					break;
				}
				default: {
					throw new SAXException();
				}
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			switch (localName) {
				case "quest": {
					stage = STAGE_INITIAL;
					break;
				}
				case "level": {
					stage = STAGE_QUEST;
					break;
				}
				case "step": {
					if (step != null) {
						terminateParser();
					}
					stage = STAGE_LEVEL;
					break;
				}
				case "action": {
					if (currentAction != null) {
						if (!currentAction.isDeath() &&
							!currentAction.isVictory() &&
							currentAction.getJumpLevelId() == null &&
							currentAction.getJumpStepId() == null) {
							throw new SAXException("Action doesn't define any behaviour");
						}
						step.addAction(currentAction);
						currentAction = null;
					}
					stage = STAGE_STEP;
					break;
				}
			}
		}
		
	}
	
	private GameEntry gameEntry;
	
	private SAXParser parser;
	private GameDataHandler dataHandler;
	private File dataFile;
	
	public GameXMLDAO(String fileName) {
		dataFile = new File(fileName);
		dataHandler = new GameDataHandler();
	}

	@Override
	public GameLevel getLevel(String levelId) throws GameDAOException{
		try {
			dataHandler.setQuery(levelId, null);
			getSAXParser().parse(dataFile, dataHandler);
		} 
		catch (TerminateParsingException ex) {
			// Очікуване переривання роботи парсера
		}
		catch (Exception ex) {
			throw new GameDAOException("Error loading game data", ex);
		}
		return dataHandler.getLevel();
	}

	@Override
	public GameStep getStep(GameLevel level, String stepId) throws GameDAOException {
		try {
			dataHandler.setQuery(level.getId(), stepId);
			getSAXParser().parse(dataFile, dataHandler);
		} 
		catch (TerminateParsingException ex) {
			// Очікуване переривання роботи парсера
		}
		catch (Exception ex) {
			throw new GameDAOException("Error loading game data", ex);
		}
		return dataHandler.getStep();
	}

	@Override
	public GameEntry getEntry() throws GameDAOException {
		if (gameEntry == null) {
			try {
				dataHandler.setQuery(null, null);
				getSAXParser().parse(dataFile, dataHandler);
				gameEntry = dataHandler.getEntry();
			} 
			catch (TerminateParsingException ex) {
				// Очікуване переривання роботи парсера
			}
			catch (Exception ex) {
				throw new GameDAOException("Error loading game data", ex);
			}
		}
		return gameEntry;
	}

	private SAXParser getSAXParser() 
			throws ParserConfigurationException, SAXException {
		if (parser != null) {
			parser.reset();
		} else {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(false);
			parserFactory.setValidating(true);
			parser = parserFactory.newSAXParser();
		}
		return parser;
	}
	
}

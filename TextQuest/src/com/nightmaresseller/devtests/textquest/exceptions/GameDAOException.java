package com.nightmaresseller.devtests.textquest.exceptions;

public class GameDAOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public GameDAOException() {
		super();
	}

	public GameDAOException(String message) {
		super(message);
	}
	
	public GameDAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

package com.desire3d.mgit.exceptions;

/**
 * Exception to show failures occurred to local repository 
 * 
 * @author Mahesh Pardeshi
 *
 */
public class LocalRepositoryException extends BaseMGitAPIException {

	private static final long serialVersionUID = 7019130651659017634L;

	/**
	 * @param messageId
	 * @param message
	 * @param cause
	 */
	public LocalRepositoryException(String messageId, String message, Throwable cause) {
		super(messageId, message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LocalRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param messageId
	 */
	public LocalRepositoryException(String messageId) {
		super(messageId);
	}
}
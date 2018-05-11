package com.desire3d.mgit.exceptions;

/**
 * @author Mahesh Pardeshi
 *
 */
public class CloneFailureException extends BaseMGitAPIException {

	private static final long serialVersionUID = 1140898046199034236L;

	/**
	 * @param messageId
	 * @param message
	 * @param cause
	 */
	public CloneFailureException(String messageId, String message, Throwable cause) {
		super(messageId, message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CloneFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param messageId
	 */
	public CloneFailureException(String messageId) {
		super(messageId);
	}
	
	
}
package com.desire3d.mgit.exceptions;

/**
 * Exception to generate failure on code commit to central repository 
 *  
 * @author Mahesh Pardeshi
 *
 */
public class CommitFailureException extends BaseMGitAPIException {
	
	private static final long serialVersionUID = 7173596799423857193L;

	/**
	 * @param messageId
	 */
	public CommitFailureException(String messageId) {
		super(messageId);
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public CommitFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param messageId
	 * @param message
	 * @param cause
	 */
	public CommitFailureException(String messageId, String message, Throwable cause) {
		super(messageId, message, cause);
	}

}
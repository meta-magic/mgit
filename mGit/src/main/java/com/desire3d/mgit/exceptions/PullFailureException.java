package com.desire3d.mgit.exceptions;

/**
 * @author Mahesh Pardeshi
 *
 */
public class PullFailureException extends BaseMGitAPIException {

	private static final long serialVersionUID = 1608875955523981768L;

	/**
	 * @param messageId
	 * @param message
	 * @param cause
	 */
	public PullFailureException(String messageId, String message, Throwable cause) {
		super(messageId, message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PullFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param messageId
	 */
	public PullFailureException(String messageId) {
		super(messageId);
	}

}

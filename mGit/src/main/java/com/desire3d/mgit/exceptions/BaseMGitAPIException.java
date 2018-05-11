package com.desire3d.mgit.exceptions;

/**
 * Base / Super exception of MGit API
 * 
 * @author Mahesh Pardeshi
 * */
public abstract class BaseMGitAPIException extends Throwable {

	private static final long serialVersionUID = -2753833738350620657L;

	private String messageId;

	/**
	 * Constructor to build exception with message id 
	 * 
	 * @param messageId
	 */
	protected BaseMGitAPIException(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @param messageId
	 * @param cause
	 */
	public BaseMGitAPIException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor to build exception with occurred exception 
	 * 
	 * @param messageId
	 * @param message
	 * @param cause
	 */
	protected BaseMGitAPIException(String messageId, String message, Throwable cause) {
		super(cause.getMessage(), cause);
		this.messageId = messageId;
	}

	public String getMessageId() {
		return messageId;
	}
}

/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

/**
 * Thrown, when occurred problem with report library working.
 */
public final class ReportLibException extends Exception
{
	/**
	 * An ReportLibException with no detail message.
	 */
	public ReportLibException()
	{
		super();
	}
	
	/**
	 * An ReportLibException with a detail message.
	 * @param message Exception detail message
	 */
	public ReportLibException(String message)
	{
		super(message);
	}
	
	/**
	 * Internal use only. Creates an ReportLibException from other <i>java.lang.Exception</i>, to inform user, that occurred problem is related to report library.
	 * @param e - Source <i>java.lang.Exception</i>
	 */
	ReportLibException(Exception e)
	{
		super("("+e.getClass().getSimpleName()+"): "+e.getMessage());
		this.setStackTrace(e.getStackTrace());
	}
}

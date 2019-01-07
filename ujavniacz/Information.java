
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import static ujavniacz.Importance.*;

/**
 * Used to store information for user.
 * @see Tool#report(Information)
 */
public class Information
{
	private static boolean s_readStackTraceInfo = false;
	private static boolean s_readStackTraceWarning = false;
	private static boolean s_readStackTraceCritical = false;
	
	protected static final String UNKNOWN_EXCEPTION_MESSAGE_PREFIX = "Unknown";
	
	protected static void setStackTraceReading(boolean readInfo, boolean readWarning, boolean readCritical)
	{
		s_readStackTraceInfo = readInfo;
		s_readStackTraceWarning = readWarning;
		s_readStackTraceCritical = readCritical;
	}
	
	private String stackTraceToString(StackTraceElement[] ste)
	{
		String s = "";
		
		for(int i=0; i<ste.length; i++)
			s+=ste[i].toString()+"\n";		
		
		return s;
	}
	
	/**
	 * @param importance Information category Note: Using INFO flag for exceptions is not recommended.
	 * @param e Reported Exception.
	 * @see Importance
	 */
	public Information(Importance importance, Exception e)
	{		
		setImportance(importance);
		m_header = e.getClass().getSimpleName();		
		m_description = getContentFromException(e);
		if(shouldReadStackTrace())
			m_StackTraceString = stackTraceToString(e.getStackTrace());
		else
			m_StackTraceString = "";
		m_time = new Time();
	}
	/**
	 * @param importance Information category
	 * @param header Message title, a report reason
	 * @param description Report details
	 * @see Importance
	 */
	
	public Information(Importance importance, String header, String description)
	{
		setImportance(importance);
		setHeader(header);
		m_description = description;
		m_time = new Time();
		if(shouldReadStackTrace())
			m_StackTraceString = stackTraceToString(getStackTrace());
		else
			m_StackTraceString = "";
	}
	
	private String m_header;
	private String m_description;
	private Importance m_lvl;
	private String m_StackTraceString;
	private Time m_time;
	
	private void setImportance(Importance importance)
	{
		if(importance != null)
			m_lvl = importance;
		else
			m_lvl = INFO;
	}
	
	private void setHeader(String header)
	{
		m_header = header;			
	}
	
	private String getContentFromException(Exception e)
	{
		String s = e.getMessage();
		
		if(s != null && !s.isEmpty())
			return s;
		else
			return UNKNOWN_EXCEPTION_MESSAGE_PREFIX+" "+e.getClass().getSimpleName();		
	}	
	
	private StackTraceElement[] getStackTrace()
	{
		return Thread.currentThread().getStackTrace();
	}
	
	private boolean shouldReadStackTrace()
	{
		if(m_lvl == CRITICAL && shouldReadCriticalStackTrace())
			return true;
		if(m_lvl == WARNING && shouldReadWarningStackTrace())
			return true;
		if(m_lvl == INFO && shouldReadInfoStackTrace())
			return true;
		return false;
	}
	
	private boolean shouldReadCriticalStackTrace()
	{
		return s_readStackTraceCritical;
	}
	
	private boolean shouldReadWarningStackTrace()
	{
		return s_readStackTraceWarning;
	}
	
	private boolean shouldReadInfoStackTrace()
	{
		return s_readStackTraceInfo;
	}
	
	@Override
	public String toString()
	{		
		String s;
		s="[";
		s+= m_time.getTime();
		s+="] ";
		s+= m_lvl.toString();		
		if(m_header != null && m_header.length() >0)	
		{
			s+=" ";
			s+=m_header;
		}
		s+=": ";			
		s+=m_description;
		s+="\n";
		s+="\t"+m_StackTraceString.replaceAll("\n", "\n\t");
		s = s.substring(0, s.length()-2) +"\n";
		return s;
	}
	
	/**
	 * @return String array, which contain category, header, description and stack trace as elements.
	 */
	public String[] toArrString()
	{
		return new String[]
		{
			m_lvl.toString(),
			m_header,
			m_description,
			m_StackTraceString
		};
	}
	
	/**
	 * Note: In Information created from exception, a header is exception class name.
	 * @return Information details.
	 */
	public String getHeader()
	{
		return m_header;
	}
	
	/**
	 * Note: In Information created from exception, a description is exception message
	 * @return Event or situation description.
	 */
	public String getMsg()
	{
		return m_description;
	}
	
	/**
	 * @see Importance#INFO
	 * @return <i>true</i> if information has category INFO
	 */
	public boolean isInfo()
	{
		if(m_lvl == INFO)
			return true;
		return false;
	}
	
	/**
	 * @see Importance#WARNING
	 * @return <i>true</i> if information has category WARNING
	 */
	public boolean isWarning()
	{
		if(m_lvl == WARNING)
			return true;
		return false;
	}
	
	/**
	 * @see Importance#CRITICAL
	 * @return <i>true</i> if information has category CRITICAL
	 */
	public boolean isCritical()
	{
		if(m_lvl == CRITICAL)
			return true;
		return false;		
	}
}

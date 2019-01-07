
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

final class LibInfo
{
	private static final String PROGRAM_NAME = "uJavniacz";
	private static final String AUTHOR = "Arkadiusz Kostyra";
	
	private static final int DATE_BEGIN = 2018;
	private static final int DATE_END = 2018;
	
	private static final int MAJOR = 1;
	private static final int MINOR = 0;
	private static final int RELEASE = 0;
	private static final String SUFFIX = "";
	
	public static final int NAME = 0;
	public static final int COPYRIGHT = 1;
	public static final int VERSION = 2;
	
	private LibInfo()
	{
		buildName();
		buildCopyright();
		buildVersion();
		s_ready = true;
	}	
	
	private static boolean s_ready;
	private static LibInfo s_info;	
	
	private static void init()
	{
		s_info = new LibInfo();
	}
	
	static String get(int information)
	{
		if(!s_ready)
			init();
		switch(information)
		{
			case NAME: return s_info.m_name;
			case COPYRIGHT: return s_info.m_copyright;
			case VERSION: return s_info.m_version;
			default: return ""+information;
		}
	}
	
	private String m_name;
	private String m_copyright;
	private String m_version;
	
	private void buildName()
	{
		m_name = PROGRAM_NAME;
	}
	
	private void buildCopyright()
	{
		m_copyright = "Copyright \u00A9 "+getDate()+" "+AUTHOR;
	}
	
	@SuppressWarnings("unused")
	private String getDate()
	{
		String date = "";
		date += DATE_BEGIN;
		if(DATE_END>DATE_BEGIN)
			date+="-"+DATE_END;
		return date;
	}
	
	@SuppressWarnings("unused")
	private void buildVersion()
	{
		m_version = MAJOR+"."+MINOR;		
		if(RELEASE>0)
			m_version+="."+RELEASE;
		if(!SUFFIX.isEmpty())
			m_version+=SUFFIX;
	}
}

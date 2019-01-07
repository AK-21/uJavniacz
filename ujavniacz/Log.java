
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.util.List;
import java.util.ArrayList;

final class Log
{
	private static Log s_log;
	private static boolean s_ready = false;
	
	private Log()
	{		
		m_informations = new ArrayList<Information>();
		m_active = true;
		s_ready = true;
	}
	
	private static void init()
	{
		s_log = new Log();		
	}
	
	static Log get()
	{
		if(!s_ready)
		{
			init();
		}
		return s_log;
	}
	
	private List<Information> m_informations;
	private boolean m_active;	
	
	void add(Information information)
	{
		m_informations.add(information);
	}
		
	void enable()
	{
		m_active = true;
	}
	
	void disable()
	{
		m_active = false;
	}
	
	boolean isActive()
	{
		if(m_active)
			return true;
		return false;
	}
	
	Information getEntry(int index) throws ReportLibException
	{
		if(index>=0 && index<m_informations.size())
			return m_informations.get(index);
		else
		{
			throw new ReportLibException("Entry not exist at index "+index);
		}			
	}
	
	int getSize()
	{
		return m_informations.size();
	}
}

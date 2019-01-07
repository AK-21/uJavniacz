
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;
import java.util.Calendar;

class Time
{	
	private static final char SEPARATOR_DATE = '.';
	private static final char SEPARATOR_CLOCK = ':';
	private static final char SEPARATOR_SHORT = '-';
	private static final String SEPARATOR_BETWEEN_DATECLOCK = " | ";
	
	Time()
	{
		m_calendar = Calendar.getInstance();
	}
	
	private Calendar m_calendar;
	
	private String doubleNumFormat(int number)
	{	
		String s;
		if(number<10)
			s="0"+number;
		else
			s = ""+number;
		return s;
	}
	
	private String tripleNumFormat(int number)
	{	
		String s="";
		
		if(number<100)
		{
			s += "0";		
			if(number<10)
				s += "0";
		}
		s += number;
			
		return s;
	}
	
	private String quadrupleNumFormat(int number)
	{	
		String s="";
		
		if(number<1000)
		{
			s += "0";		
			if(number<100)
			{
				s += "0";		
				if(number<10)
					s += "0";
			}
		}
		s += number;
			
		return s;
	}
	
	private String shortYearFormat(int number)
	{
		String s ="";
		
		s+=number;
		
		if(number>=10)
			s=s.substring(s.length()-2, s.length());
		
		return s;
	}
	
	String getSimple()
	{
		String s = "";
		
		s+=shortYearFormat(m_calendar.get(Calendar.YEAR));
		s+=SEPARATOR_SHORT;
		s+=doubleNumFormat(m_calendar.get(Calendar.MONTH)+1);
		s+=SEPARATOR_SHORT;
		s+=doubleNumFormat(m_calendar.get(Calendar.DAY_OF_MONTH));
		s+=SEPARATOR_SHORT;
		s+=doubleNumFormat(m_calendar.get(Calendar.HOUR_OF_DAY));
		s+=SEPARATOR_SHORT;
		s+=doubleNumFormat(m_calendar.get(Calendar.MINUTE));
		
		return s;
	}
	
	String getTime()
	{
		String s = getDate();
		s+=SEPARATOR_BETWEEN_DATECLOCK;
		s+=getClock();
		return s;
	}
	
	private String getDate()
	{
		String s = "";
		s+=quadrupleNumFormat(m_calendar.get(Calendar.YEAR));
		s+=SEPARATOR_DATE;
		s+=doubleNumFormat(m_calendar.get(Calendar.MONTH)+1);
		s+=SEPARATOR_DATE;
		s+=doubleNumFormat(m_calendar.get(Calendar.DAY_OF_MONTH));
		return s;
	}
	
	private String getClock()
	{
		String s = "";
		s+=doubleNumFormat(m_calendar.get(Calendar.HOUR_OF_DAY));
		s+=SEPARATOR_CLOCK;
		s+=doubleNumFormat(m_calendar.get(Calendar.MINUTE));
		s+=SEPARATOR_CLOCK;
		s+=doubleNumFormat(m_calendar.get(Calendar.SECOND));
		s+=SEPARATOR_CLOCK;
		s+=tripleNumFormat(m_calendar.get(Calendar.MILLISECOND));
		return s;
	}				
}

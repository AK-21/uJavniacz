
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

class RptFile
{
	private static String DIR_RPT = "Reports";
	private static String PREFIX_RPT = "Rpt";
	private static String PREFIX_LOG = "Log";
	private static String PREFIX_INFO = "Info";
	private static String PREFIX_WARNING = "Warning";
	private static String PREFIX_CRITICAL = "Critical";
	private static String FILE_EXTENSION = "txt";
	
	private static String s_dirPath = DIR_RPT;
	
	static void setRptDirectory(String directory)
	{
		if(directory==null || directory.isEmpty())
			directory = DIR_RPT;
		else
			s_dirPath = directory;
	}
	
	RptFile(Information information) throws FileNotFoundException
	{
		findRptDir();
		
		String fileName = PREFIX_RPT;
		if(information.isCritical())
			fileName += PREFIX_CRITICAL;
		else if(information.isWarning())
			fileName += PREFIX_WARNING;
		else
			fileName += PREFIX_INFO;
		fileName += "_" + new Time().getSimple();		
		fileName += "."+FILE_EXTENSION;
		
		m_fileWriter = new PrintWriter(s_dirPath+File.separator+fileName);
		m_fileWriter.println(information.toString());
		m_fileWriter.close();
	}
	
	RptFile() throws FileNotFoundException, ReportLibException
	{
		Log logPointer = Log.get();
		int size = logPointer.getSize();
		
		String[] tmp = new String[size];
		
		findRptDir();
		
		String fileName = PREFIX_LOG+"_";				
		fileName +=	new Time().getSimple();
		fileName += "."+FILE_EXTENSION;		
		
		try
		{
			for(int i=0; i<size; i++)
			{
				tmp[i]=logPointer.getEntry(i).toString();			
			}
			
			m_fileWriter = new PrintWriter(s_dirPath+File.separator+fileName);
			
			for(int i=0; i<size; i++)
			{
				m_fileWriter.println(tmp[i]);				
			}
			
			m_fileWriter.close();
		}
		catch(ReportLibException e)
		{
			throw e;
		}
	}
	
	private File m_rptDir;	
	private PrintWriter m_fileWriter;		
	
	private void findRptDir()
	{
		m_rptDir = new File(s_dirPath);
		if(!m_rptDir.exists())
			m_rptDir.mkdir();
	}
}

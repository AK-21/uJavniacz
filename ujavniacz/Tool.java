
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import static ujavniacz.Importance.*;

import java.io.FileNotFoundException;

/**
 *	Single-instance class. Provides reporting functions contained inside library, to use in external application.
 */
public class Tool
{	
	//-------------------
	//	Const
	//-------------------	
	
	private static final Importance DEF_LVL_INFO = INFO;
	private static final Importance DEF_LVL_EXCEPTION = CRITICAL;
	
	/**
	 * Text-align left option
	 */
	public static final int ALIGN_LEFT = MsgDialog.TEXT_LEFT;
	
	/**
	 * Text-align right option
	 */
	public static final int ALIGN_RIGHT = MsgDialog.TEXT_RIGHT;
	
	/**
	 * Text-align center option
	 */
	public static final int ALIGN_CENTER = MsgDialog.TEXT_CENTER;
	
	/**
	 * Text-align justify option
	 */
	public static final int ALIGN_JUSTIFY = MsgDialog.TEXT_JUSTIFY;
	
	//-------------------
	//	Tool
	//-------------------
	
	private Tool()
	{
		setStackTraceReadingDefault();
		setDefaultSaveRpt();
		initUI(new Theme());
		initLog();
		s_ready = true;
	}	
	
	private Tool(Theme theme)
	{
		setStackTraceReadingDefault();
		setDefaultSaveRpt();
		initUI(theme);
		initLog();
		s_ready = true;
	}
	
	private static Tool s_tool;
	private static boolean s_ready = false;
	
	/**
	 * Provides access to Tool instance. Creates instance if is not initialized yet.
	 * @return Tool instance
	 */
	public static Tool get()
	{
		if(!isAlreadyInitialized())
			init();
		return s_tool;
	}
	
	private static boolean isAlreadyInitialized()
	{
		if(s_ready)			
			return true;
		return false;
	}
	
	private static void init()
	{
		s_tool = new Tool();		
	}
	
	/**
	 * Allows to initialize tool with custom color theme for output window.
	 * <br>
	 * Note: Function must be used to first Tool initialization. In other case program reports an error.
	 * @param theme Color theme
	 * @see Theme
	 */
	public static void customInit(Theme theme)
	{
		if(!isAlreadyInitialized())
			s_tool = new Tool(theme);
		else
			get().rptWarning("Initialization error","Tool is already intialized. Cannot initialize again!");
	}
	
	//-------------------
	//	Interface
	//-------------------
	
	private UI m_ui = null;
	
	private void initUI(Theme theme)
	{
		m_ui = new UI(theme);
	}	
	
	
	//-------------------
	//	Log
	//-------------------
	
	private Log m_log;	
	
	private void initLog()
	{
		m_log = Log.get();		
	}
	
	private void addToLog(Information information)
	{
		m_log.add(information);
	}
	
	private boolean shouldAddToLog(Information information)
	{
		if(m_log.isActive())
			return true;
		return false;
	}
	
	/**
	 * Disables data collecting in log
	 */
	public void disableLog()
	{
		m_log.disable();
	}
	
	/**
	 * Enables data collecting in log
	 */
	public void enableLog()
	{
		m_log.enable();
	}
	
	/**
	 * Save all data collected in log to text file.
	 */
	public void saveLogFile()
	{
		try
		{
			new RptFile();
			rptInfoPlus("File saving", "Log file saved successfully!");			
		}
		catch(Exception e)
		{
			rptWarning(e);
		}
	}
	
	/**
	 * Sets custom directory to save report files.
	 * @param directory Directory name. Note: Not path!
	 */
	public void setReportsDirectory(String directory)
	{
		RptFile.setRptDirectory(directory);
	}
	
	//-------------------
	//	Msg
	//-------------------
		
	private boolean m_showAlways = false;
	
	private void displayMsg(Information information) throws ReportLibException
	{
		if(m_ui == null)
			throw new ReportLibException("Interface not initialized!");
		else
			try
			{
				m_ui.displayMsg(information);
			}
			catch (Exception e)
			{			
				throw new ReportLibException(e);				
			}
	}
	
	private boolean shouldDisplayMsg(Information information)
	{
		if(m_showAlways || information.isWarning() || information.isCritical())
			return true;
		return false;
	}
	
	/**
	 * Displays custom dialog message without pausing current action.
	 * @param header Message title
	 * @param textAlign Align option (accepted values: ALIGN_LEFT, ALIGN_RIGHT, ALIGN_CENTER and ALIGN_JUSTIFY)
	 * @param message Message lines
	 * @throws ReportLibException Thrown, if tool GUI is not initialized yet.
	 */
	public void displayCustomMsg(String header, int textAlign, String... message) throws ReportLibException
	{
		if(m_ui == null)
			throw new ReportLibException("Interface not initialized!");
		else
			m_ui.displayInterfaceMsg(header, textAlign, message);

	}
	
	//-------------------
	//	Output window
	//-------------------
	
	private boolean m_useOutputWindow;
	
	private void printInOutputWindow(Information information) throws ReportLibException
	{
		if(m_ui == null)
			throw new ReportLibException("Interface not initialized!");
		else
		{
			try
			{
				m_ui.printInOutputWindow(information);
			}
			catch(Exception e)
			{
				throw new ReportLibException(e);
			}
		}
	}
	
	private boolean shouldPrintInOutputWindow(Information information)
	{
		return m_useOutputWindow;
	}
		
	/**
	 * Activates reports output window
	 */
	public void enableOutputWindow()
	{
		m_ui.displayOutputWindow();
		m_useOutputWindow = true;
	}
	
	//-------------------
	//	StackTrace
	//-------------------
	
	/**
	 * Allows to set for which types of Informations a stack trace will be captured.
	 * @param readInfo Setting for INFO type
	 * @param readWarning Setting for WARNING type
	 * @param readCritical Setting for CRITICAL type
	 * @see Importance
	 * @see Information
	 */
	public void setStackTraceReading(boolean readInfo, boolean readWarning, boolean readCritical)
	{
		Information.setStackTraceReading(readInfo, readWarning, readCritical);
	}
	
	/**
	 * Enables stack trace capturing for all Informations
	 * @see Importance
	 * @see Information
	 */
	public void setStackTraceReadingAll()
	{
		setStackTraceReading(true, true, true);
	}
	
	/**
	 * Disables stack trace capturing for all Informations
	 * @see Importance
	 * @see Information
	 */
	public void setStackTraceReadingNone()
	{
		setStackTraceReading(false, false, false);
	}
	
	/**
	 * Enables stack trace capturing for CRITICAL Informations and disables for others.
	 * @see Importance
	 * @see Information
	 */
	public void setStackTraceReadingCriticalOnly()
	{
		setStackTraceReading(false, false, true);
	}
	
	/**
	* Set stack trace capturing options to default.
	* For CRITICAL Informations stack trace will be captured, for INFO and WARNING - not.
	* @see Importance
	* @see Information
	*/
	public void setStackTraceReadingDefault()
	{
		setStackTraceReadingCriticalOnly();
	}
	
	//-------------------
	//	Single report file
	//-------------------
	
	private boolean m_saveRptWarning;
	private boolean m_saveRptCritical;
	
	private boolean shouldSaveRpt(Information information)
	{
		if
		(
			(information.isCritical() && m_saveRptCritical)
			||(information.isWarning() && m_saveRptWarning)
		)
			return true;
		
		return false;
	}
	
	/**
	 * Sets auto-saving WARNING reports to text files.
	 * @param value true for yes, false for not.
	 * @see Importance#WARNING
	 */
	public void setSaveRptWarning(boolean value)
	{
		m_saveRptWarning = value;
	}
	
	/**
	 * Sets auto-saving CRITICAL reports to text files.
	 * @param value true for yes, false for not.
	 * @see Importance#CRITICAL
	 */
	public void setSaveRptCritical(boolean value)
	{
		m_saveRptCritical = value;
	}
	
	/**
	 * Sets default auto-save presets (Only CRITICAL reports will be saved automatically)
	 * @see Importance#WARNING
	 * @see Importance#CRITICAL
	 */
	public void setDefaultSaveRpt()
	{
		m_saveRptWarning = false;
		m_saveRptCritical = true;
	}
	
	/**
	 * Allows to save single information to text file
	 * @param information Information to save
	 * @see Information
	 */
	public void saveRpt(Information information)
	{
		try
		{
			new RptFile(information);
		}
		catch (FileNotFoundException e)
		{
			rptWarning(e);
		}
	}
	
	//-------------------
	//	Reports
	//-------------------

	/**
	 * Generates report from Information.
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>WARNING and CRITICAL report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, WARNING and CRITICAL report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param information Reported information
	 * @see Information
	 * @see Tool#setSaveRptWarning(boolean)
	 * @see Tool#setSaveRptCritical(boolean)
	 */
	public void report(Information information)
	{		
		try
		{
			if(shouldAddToLog(information))
				addToLog(information);
			if(shouldPrintInOutputWindow(information))
				printInOutputWindow(information);
			else
				System.out.print(information.toString());
			if(shouldDisplayMsg(information))
				displayMsg(information);	
			if(shouldSaveRpt(information))
				saveRpt(information);
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}
	
	/**
	 * Generates report from Exception
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>WARNING and CRITICAL report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, WARNING and CRITICAL report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param importance Report category. Note: Although create INFO report from Exception is possible, it is not recommended.
	 * @param e Reported Exception
	 * @see Importance
	 * @see Tool#setSaveRptWarning(boolean)
	 * @see Tool#setSaveRptCritical(boolean)
	 */
	public void report(Importance importance, Exception e)
	{
		report(new Information(importance, e));
	}
	
	/**
	 * Generates report from Exception with default category (CRITICAL)
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param e Reported Exception
	 * @see Importance#CRITICAL
	 * @see Tool#report(Importance, Exception)
	 */
	public void report(Exception e)
	{
		report(DEF_LVL_EXCEPTION, e);
	}
	
	/**
	 * Generates report
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>WARNING and CRITICAL report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, WARNING and CRITICAL report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param importance Report category.
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance
	 */
	public void report(Importance importance, String header, String description)
	{
		report(new Information(importance, header, description));
	}
	
	/**
	 * Generates report with default category (INFO)
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will not be displayed in dialog message. Current action will not be paused</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance#INFO
	 */
	public void report(String header, String description)
	{
		report(DEF_LVL_INFO, header, description);
	}
	
	/**
	 * Generates INFO report from exception. Note: Not recommended!
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will not be displayed in dialog message. Current action will not be paused</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param e Reported Exception
	 * @see Importance#INFO
	 */
	public void rptInfo(Exception e)
	{
		report(INFO, e);
	}

	/**
	 * Generates INFO report
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will not be displayed in dialog message. Current action will not be paused</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance#INFO
	 */
	public void rptInfo(String header, String description)
	{
		report(INFO, header, description);
	}
	
	/**
	 * Generates extended INFO report from exception. Note: Not recommended!
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param e Reported Exception
	 * @see Importance#INFO
	 */
	public void rptInfoPlus(Exception e)
	{
		m_showAlways = true;
		report(INFO, e);
		m_showAlways = false;
	}
	
	/**
	 * Generates extended INFO report.
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance#INFO
	 */
	public void rptInfoPlus(String header, String description)
	{
		m_showAlways = true;
		report(INFO, header, description);
		m_showAlways = false;
	}
	
	/**
	 * Generates WARNING report from exception.
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param e Reported Exception
	 * @see Importance#WARNING
	 */
	public void rptWarning(Exception e)
	{
		report(WARNING, e);
	}
	
	/**
	 * Generates WARNING report.
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance#WARNING
	 */
	public void rptWarning(String header, String description)
	{
		report(WARNING, header, description);
	}
	
	/**
	 * Generates CRITICAL report from exception.
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param e Reported Exception
	 * @see Importance#CRITICAL
	 */
	public void rptCritical(Exception e)
	{
		report(CRITICAL, e);
	}
	
	/**
	 * Generates CRITICAL report
	 * <ul>
	 * <li>Report will be displayed in output window (if active)</li>
	 * <li>Report will be displayed in dialog messages. Current action will be paused</li>
	 * <li>Depending on the settings, report may be save to text file</li>
	 * <li>Report will be added to log (if active)</li>
	 * </ul>
	 * @param header Report title/reason
	 * @param description Report details
	 * @see Importance#CRITICAL
	 */
	public void rptCritical(String header, String description)
	{
		report(CRITICAL, header, description);
	}
}

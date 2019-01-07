
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.awt.Color;

/**
 * Using to set color theme in tool output window.
 */
public class Theme
{
	/**
	 * Creates default color theme
	 * @see Theme#setDefault()
	 */
	public Theme()
	{
		setDefault();
	}
	
	/**
	 * Creates custom color theme
	 * @param foreground Color for menu texts, scroll bars and other foreground elements
	 * @param background Color for output window and menu lists background
	 * @param info Color for INFO entries
	 * @param warning Color for WARNING entries
	 * @param critical Color for CRITICAL entries
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 * @see Importance
	 */
	public Theme(Color foreground, Color background, Color info, Color warning, Color critical)
	{
		setWindowColors(foreground, background);
		setTextColors(info, warning, critical);
	}
	
	private Color m_foreground;
	private Color m_background;
	
	private Color m_textWarning;
	private Color m_textCritical;
	private Color m_textInfo;
	
	private void setDefaultForeground()
	{
		m_foreground = Color.LIGHT_GRAY;
	}
	
	private void setDefaultBackground()
	{
		m_background = Color.BLACK;
	}
			
	private void setDefaultTextInfo()
	{
		m_textInfo = new Color(150,150,150);
	}
	
	private void setDefaultTextWarning()
	{
		m_textWarning = Color.ORANGE;
	}
	
	private void setDefaultTextCritical()
	{
		m_textCritical = Color.RED;
	}
	
	/**
	 * <style>
	 * 	.colorList
	 * 	{
	 *		background: black;
	 * 		padding: 10px;
	 * 		list-style-position: inside;
	 * 		list-style-type: none;
	 * 	}
	 * 
	 *	.colorList li
	 *	{
	 *		padding: 3px;
	 *	}
	 * 
	 * </style>
	 * Set all colors values to default.
	 * <ul class = "colorList">
		 * <li style ="color: lightgray;">Foreground = Color.LIGHT_GRAY</li>
		 * <li style ="color: black; background: white">Background = Color.BLACK</li>
		 * <li style ="color: RGB(150,150,150);">Text INFO = new Color(150,150,150)</li>
		 * <li style ="color: RGB(255,200,0);">Text WARNING = Color.ORANGE</li>
		 * <li style ="color: red;">Text CRITICAL = Color.RED</li>
	 * </ul>
	 */
	public void setDefault()
	{
		setDefaultForeground();
		setDefaultBackground();
		
		setDefaultTextInfo();
		setDefaultTextWarning();
		setDefaultTextCritical();
	}
	
	/**
	 * Sets colors for window parts
	 * @param foreground Color for menu texts, scroll bars and other foreground elements
	 * @param background Color for output window and menu lists background
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public void setWindowColors(Color foreground, Color background)
	{
		if(foreground != null)
			m_foreground = foreground;
		else
			setDefaultForeground();
		
		if(background != null)
			m_background = background;
		else
			setDefaultBackground();
		
		
	}
	
	/**
	 * Sets colors for entries
	 * @param info Color for INFO entries
	 * @param warning Color for WARNING entries
	 * @param critical Color for CRITICAL entries
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 * @see Importance
	 */
	public void setTextColors(Color info, Color warning, Color critical)
	{		
		if(info != null)
			m_textInfo = info;
		else
			setDefaultTextInfo();
		
		if(warning != null)
			m_textWarning = warning;
		else
			setDefaultTextWarning();
		
		if(critical != null)
			m_textCritical = critical;
		else
			setDefaultTextCritical();
	}	
	
	/**
	 * @return Menu texts, scroll bars and other foreground elements color
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public Color getColorForeground()
	{
		return m_foreground;
	}
	
	/**
	 * @return Output window and menu lists background color
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public Color getColorBackground()
	{
		return m_background;
	}
	
	/**
	 * @return INFO entries color
	 * @see Importance#INFO
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public Color getColorTextInfo()
	{
		return m_textInfo;
	}
	
	/**
	 * @return WARNING entries color
	 * @see Importance#WARNING
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public Color getColorTextWarning()
	{
		return m_textWarning;
	}
	
	/**
	 * @return Critical entries color
	 * @see Importance#CRITICAL
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/awt/Color.html">Color</a>
	 */
	public Color getColorTextCritical()
	{
		return m_textCritical;
	}
}

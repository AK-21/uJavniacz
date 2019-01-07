
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

final class Output
{
	private static final int CONSOLE_PADDING = 5;
	private static final int SCROLL_INCREMENT = 10;
	
	private static final String STYLE_DEFAULT = "default";
	private static final String STYLE_INFO = "info";
	private static final String STYLE_WARNING = "warning";
	private static final String STYLE_CRITICAL = "critical";
	
	Output(Theme theme)
	{
		init(theme);
		
	}
	
	private Theme m_theme;
	
	private JScrollPane m_scrollPane;	
	private JTextPane m_textPane;
	private StyledDocument m_outputDoc;
	
	private void init(Theme theme)
	{
		m_theme = theme;
		
		m_textPane = createTextPane();
		m_outputDoc = m_textPane.getStyledDocument();	
		initStyles(m_outputDoc);
		
		m_scrollPane = createScrollPane(m_textPane);
	}
	
	private JTextPane createTextPane()
	{
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(m_theme.getColorBackground());
		
		return textPane;
	}
	
	private JScrollPane createScrollPane(JTextPane textPane)
	{
		JScrollPane scrollPane = new JScrollPane(textPane);
		
		scrollPane.setVerticalScrollBar(createScrollBar(JScrollBar.VERTICAL));
		scrollPane.setHorizontalScrollBar(createScrollBar(JScrollBar.HORIZONTAL));
		
		EmptyBorder border = new EmptyBorder(CONSOLE_PADDING, CONSOLE_PADDING, CONSOLE_PADDING, CONSOLE_PADDING);
		scrollPane.setBorder(border);
		scrollPane.setBackground(m_theme.getColorBackground());
		
		return scrollPane;
	}
	
	private JScrollBar createScrollBar(int orientation)
	{
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBorder(new LineBorder(m_theme.getColorForeground()));		
		scrollBar.setUI(new OutputScrollBarUI(m_theme));
		scrollBar.setUnitIncrement(SCROLL_INCREMENT);
		scrollBar.setOrientation(orientation);
		
		return scrollBar;
	}
	
	private void initStyles(StyledDocument document)
	{
		Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		
		Style outputStyle = document.addStyle(STYLE_DEFAULT, defaultStyle);
        StyleConstants.setFontFamily(defaultStyle, "SansSerif");     
        
        Style style;
        
        style = document.addStyle(STYLE_INFO, outputStyle);
        StyleConstants.setForeground(style, m_theme.getColorTextInfo());       
        
        style = document.addStyle(STYLE_WARNING, outputStyle);
        StyleConstants.setForeground(style, m_theme.getColorTextWarning());
                
        style = document.addStyle(STYLE_CRITICAL, outputStyle);
        StyleConstants.setForeground(style, m_theme.getColorTextCritical());     
	}
	
	JScrollPane getOutputPane()
	{
		return m_scrollPane;
	}
	
	void add(Information information) throws BadLocationException
	{
		String style;
		
		if(information.isInfo())
			style = STYLE_INFO;
		else if(information.isWarning())
			style = STYLE_WARNING;
		else if(information.isCritical())
			style = STYLE_CRITICAL;
		else
			style = STYLE_DEFAULT;

		m_outputDoc.insertString(m_outputDoc.getLength(), information.toString(), m_outputDoc.getStyle(style));
		m_textPane.setCaretPosition(m_outputDoc.getLength());
	}
}

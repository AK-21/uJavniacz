
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

final class MsgDialog extends JDialog
{
	private static final int PADDING_LR = 20;
	private static final int LETTER_WIDTH = 7;
	private static final int LINE_HEIGHT = 18;
	private static final int MAX_LETTERS = 70;
	
	private static final int MIN_CONTENT_WIDTH = 200;
	private static final int MIN_CONTENT_HEIGHT = 30;
	
	private static final int BTN_AREA_HEIGHT = 40;
	private static final int BTN_HEIGHT = 30;
	private static final int BTN_WIDTH = 100;
	
	private static final String TEXT_BTN_EXT = "OK";
	
	private static final int CONTENT_MARGIN_VERTICAL = 20;
	private static final int CONTENT_MARGIN_HORISONTAL = 20;
	
	private static final Color BACKGROUND = new Color(232, 240, 247);
	private static final Color FONT = new Color(72, 72, 72);
	
	private static final String EMPTY = "Empty message";
	
	static final int TEXT_LEFT = 0;
	static final int TEXT_RIGHT = 1;
	static final int TEXT_CENTER = 2;
	static final int TEXT_JUSTIFY = 3;
	
	MsgDialog(boolean ignorePause, String header, int textAlign, String... message)
	{
		SetLocalInitValues();		
		setPauseAttention(ignorePause);			
		setTitle(header);		
		generateContent(textAlign, message);		
		setSize(m_width, m_height);		
		setProperties();		
		setLayout();
		addContent(m_msg);
		addExitBtn();
	}
	
	MsgDialog(String header, String... message)
	{
		this(false, header, TEXT_JUSTIFY, message);
	}
	
	MsgDialog(Information information)
	{
		this(information.getHeader(), information.getMsg());
	}
	
	MsgDialog(boolean ignorePause, Information information)
	{
		this(ignorePause, information.getHeader(), TEXT_JUSTIFY, information.getMsg());
	}
	
	private int m_LinesCounter;
	private String m_msg;
	private boolean m_ignorePause;
	private int m_width;
	private int m_height;
	private Container m_mainContainer;
	
	private void SetLocalInitValues()
	{
		m_LinesCounter = 0;
		m_msg = "";
		m_width = 0;
		m_height = 0;
	}
	
	private void setPauseAttention(boolean status)
	{
		m_ignorePause = status;
	}
	
	private String getTextAlignValue(int textAlignCode) throws ReportLibException
	{
		String align = "justify";
		
		if(textAlignCode == TEXT_LEFT)
			align = "left";
		else if(textAlignCode == TEXT_RIGHT)
			align = "right";
		else if(textAlignCode == TEXT_CENTER)
			align = "center";
		else if(textAlignCode == TEXT_JUSTIFY)
			align = "justify";
		else
			throw new ReportLibException("Incorrect text-align value!");
		
		return align;
	}
	
	private void setProperties()
	{
		setLayout(new BorderLayout());		
		setAlwaysOnTop(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);		
		setLocationRelativeTo(null);
	}	
	
	private void setLayout()
	{
		JPanel body = new JPanel();
		this.add(body);
		
		m_mainContainer = new Container();
		body.add(m_mainContainer);
		
		m_mainContainer.setLayout(new BoxLayout(m_mainContainer, BoxLayout.Y_AXIS));
		
		body.setBackground(BACKGROUND);
	}
	
	private void addContent(String content)
	{
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(BACKGROUND);
		
		JLabel contentLabel =  new JLabel(content, SwingConstants.CENTER);
		contentLabel.setForeground(FONT);
		
		int width = this.getWidth()-2*CONTENT_MARGIN_HORISONTAL;
		int height;		
		
		if(LINE_HEIGHT*m_LinesCounter>MIN_CONTENT_HEIGHT)
			height = LINE_HEIGHT*m_LinesCounter;
		else
			height = MIN_CONTENT_HEIGHT;		
		
		setSize(contentPanel, width, height);		
		
		contentPanel.add(contentLabel);
		m_mainContainer.add(contentPanel);
	}
	
	private void setSize(Component c, int width, int height)
	{
		c.setSize(width, height);
		c.setMinimumSize(new Dimension(width, height));
		c.setPreferredSize(new Dimension(width, height));
		c.setMaximumSize(new Dimension(width, height));
	}
	
	private void generateContent(int textAlign, String... message)
	{
		String align = "justify";
		try
		{
			align = getTextAlignValue(textAlign);
		}
		catch(ReportLibException rle)
		{
			System.err.println(rle);
		}
		
		m_msg = "<html><div style = text-align: "+align+";>";
		
		int length = message.length;
		
		if(length < 1)
		{
			length = 1;
			message = new String[]{EMPTY};
		}		
		
		for(int i=0; i<length; i++)
		{
			if(message[i]==null)
				message[i]="";
							
			message[i] = breakLines(message[i]);			
			
			updateWidth(countLineWidth(message[i]));			
		}		
		m_height += countMsgHeight(message);		
		
		
		for(int i =0; i < message.length; i++)
			m_msg+=message[i]+"<br>";
		
		m_msg+="</div></html>";	
	}
	
	private int countMsgHeight(String... message)
	{
		int tmpHeight = 0;
		m_LinesCounter += message.length;
		
		if(LINE_HEIGHT*m_LinesCounter>MIN_CONTENT_HEIGHT)
			tmpHeight += LINE_HEIGHT*m_LinesCounter;
		else
			tmpHeight += MIN_CONTENT_HEIGHT;
		
		tmpHeight += BTN_AREA_HEIGHT;
		tmpHeight += 2*CONTENT_MARGIN_VERTICAL;
		return tmpHeight;
	}
	
	private int countLineWidth(String line)
	{
		int tmpWidth = PADDING_LR*2;
		int length = line.length();
		
		if(length>=MAX_LETTERS)
			tmpWidth+=MAX_LETTERS*LETTER_WIDTH;
		else
			if(length*LETTER_WIDTH>MIN_CONTENT_WIDTH)
				tmpWidth += length*LETTER_WIDTH;
			else
				tmpWidth += MIN_CONTENT_WIDTH;
		
		return tmpWidth;
	}
	
	private void updateWidth(int width)
	{
		if(m_width < width)
			m_width = width;
	}
	
	private void addExitBtn()
	{
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(BACKGROUND);		
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
		
		int width = this.getWidth() - 2*CONTENT_MARGIN_HORISONTAL;
		int height = BTN_AREA_HEIGHT;		
		setSize(btnPanel, width, height);		
		
		JButton btnExit = new JButton(TEXT_BTN_EXT);
		btnExit.setFocusPainted(false);
		btnExit.setForeground(FONT);		
		setSize(btnExit, BTN_WIDTH, BTN_HEIGHT);		
		
		btnExit.addActionListener(createExitBtnListener());
		
		int fillerWidth = btnPanel.getWidth() - BTN_WIDTH;		
		Component filler = Box.createRigidArea(new Dimension(fillerWidth, BTN_AREA_HEIGHT));
		
		btnPanel.add(filler);
		btnPanel.add(btnExit);
		m_mainContainer.add(btnPanel);
	}
	
	private ActionListener createExitBtnListener()
	{
		ActionListener exitListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!m_ignorePause)
					Pause.get().disable();
				dispose();				
			}
		};
		
		return exitListener;
	}
	
	private String breakLines(String text)
	{
		boolean found = false;
				
		String tmp = "";
		
		if(text.length()<=MAX_LETTERS)
		{
			return text;
		}
		else
		{
			for(int i = MAX_LETTERS; i>0; i--)
			{
				if(text.charAt(i)==' ')
				{
					tmp = text.substring(0, i)+"<br>"+breakLines(text.substring(i+1, text.length()));
					found = true;
					break;
				}					
			}
			
			if(!found)
				tmp = text.substring(0, MAX_LETTERS)+"<br>"+breakLines(text.substring(MAX_LETTERS, text.length()));
			
			m_LinesCounter++;
			return tmp;	
		}
	}
	
	void display()
	{
		setVisible(true);
	}
}

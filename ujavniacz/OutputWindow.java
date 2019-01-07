
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.text.BadLocationException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final class OutputWindow extends JFrame
{	
	private static final int MIN_WIDTH = 550;
	private static final int MIN_HEIGHT = 600;		
	
	OutputWindow(Theme theme)
	{
		super(LibInfo.get(LibInfo.NAME));
		init(theme);		
	}
	
	private Theme m_theme;
	private Output m_output;
	
	
	private void init(Theme theme)
	{		
		m_theme = theme;
		
		initWindow();
		OutputWindowMenu menuBar = createMenu();
		m_output = createOutput();
		initBody(menuBar, m_output);
	}
	
	private void initWindow()
	{		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		pack();
	}
	
	private OutputWindowMenu createMenu()
	{
		OutputWindowMenu menuBar = new OutputWindowMenu(m_theme);		
		addToolMenu(menuBar);
		addHelpMenu(menuBar);		
		return menuBar;
	}
	
	private Output createOutput()
	{
		return new Output(m_theme);
	}
	
	private void initBody(OutputWindowMenu menu, Output output)
	{		
		JSplitPane m_body;
		
		
		JMenuBar bar = menu.getBar();
		JScrollPane scroll = output.getOutputPane();
		
		m_body = new JSplitPane(JSplitPane.VERTICAL_SPLIT, bar, scroll);
		
		
		m_body.setBackground(Color.BLACK);
		
		m_body.setBorder(null);
		m_body.setDividerSize(0);
		m_body.setEnabled(false);
		this.add(m_body);
	}
	
	private void addToolMenu(OutputWindowMenu menuBar)
	{		
		ActionListener listener = new ActionListener()
		{		
			public void actionPerformed(ActionEvent e)
			{
				saveLogFile();
			}
		};		
		MenuItemBuilder option = new MenuItemBuilder("Save log file", listener);		
		menuBar.addMenu("Tool", option);
	}
	
	private void saveLogFile()
	{
		try
		{
			new RptFile();
			new MsgDialog(true, null, MsgDialog.TEXT_CENTER, null,"Log file saved successfully!",null).display();			
		}
		catch(Exception ex)
		{
			new MsgDialog(true, new Information(Importance.CRITICAL, ex)).display();
		}
	}
	
	private void addHelpMenu(OutputWindowMenu menuBar)
	{
		ActionListener listener = new ActionListener()
		{		
			public void actionPerformed(ActionEvent e)
			{
				showInfo();
			}
		};		
		MenuItemBuilder option = new MenuItemBuilder("About...", listener);		
		menuBar.addMenu("Help", option);
	}
	
	private void showInfo()
	{
		new MsgDialog(true, "About", MsgDialog.TEXT_CENTER, null, LibInfo.get(LibInfo.NAME)+" "+LibInfo.get(LibInfo.VERSION),null,LibInfo.get(LibInfo.COPYRIGHT),null).display();
	}
	
	void display()
	{
		setVisible(true);
	}
	
	boolean isAvaliable()
	{
		if(isVisible())
			return true;
		return false;
	}
	
	void add(Information information) throws BadLocationException
	{
		m_output.add(information);
	}
}

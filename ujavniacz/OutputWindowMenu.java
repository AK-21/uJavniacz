
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

final class OutputWindowMenu
{
	OutputWindowMenu(Theme theme)
	{		
		init(theme);		
	}
	
	private Theme m_theme;
	private JMenuBar m_menuBar;
	
	JMenuBar getBar()
	{
		return m_menuBar;
	}
	
	private void init(Theme theme)
	{
		m_theme = theme;
		m_menuBar = new JMenuBar();		
		setBarSize(600, 30);
		m_menuBar.setBackground(m_theme.getColorBackground());		
		Border menuBarBorder = BorderFactory.createMatteBorder( 0, 0, 1, 0, m_theme.getColorForeground());
		m_menuBar.setBorder(menuBarBorder);
		m_menuBar.setAlignmentX(Component.RIGHT_ALIGNMENT);
	}
	
	private JMenu createMenu(String title, MenuItemBuilder... options)
	{
		JMenu menu = new JMenu(title);
		menu.setBorder(new EmptyBorder(0,5,0,5));
		menu.setForeground(m_theme.getColorForeground());
		menu.setBackground(m_theme.getColorBackground());		
		menu.getPopupMenu().setBorder(new EmptyBorder(5,0,5,0));		
		menu.getPopupMenu().setBackground(m_theme.getColorBackground());		
		
		int length = options.length;
		JMenuItem tmp;
		
		for(int i=0; i<length; i++)
		{
			tmp = options[i].buildMenuItem();
			tmp.setBorder(null);
			tmp.setBackground(m_theme.getColorBackground());
			tmp.setForeground(m_theme.getColorForeground());
			menu.add(tmp);
		}
		return menu;
	}
	
	void addMenu(String title, MenuItemBuilder... options)
	{
		m_menuBar.add(createMenu(title, options));
	}
	
	void setBarSize(int width, int height)
	{
		m_menuBar.setSize(width, height);
		m_menuBar.setMinimumSize(new Dimension(width, height));
		m_menuBar.setPreferredSize(new Dimension(width, height));
		m_menuBar.setMaximumSize(new Dimension(width, height));
	}
}

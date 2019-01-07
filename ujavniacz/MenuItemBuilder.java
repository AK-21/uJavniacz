
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

final class MenuItemBuilder
{
	protected MenuItemBuilder(String header, ActionListener listener)
	{
		m_header = header;		
		m_listener = listener;
	}
	
	private String m_header;
	private ActionListener m_listener;
	
	JMenuItem buildMenuItem()
	{
		JMenuItem item = new JMenuItem(m_header);		
		item.addActionListener(m_listener);		
		return item;
	}
}

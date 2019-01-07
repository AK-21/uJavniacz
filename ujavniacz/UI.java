
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import javax.swing.text.BadLocationException;

final class UI 
{	
	UI(Theme theme)
	{
		init(theme);
	}
	
	private boolean m_active = false;	
	private OutputWindow m_output;
	private Theme m_theme;
	
	private void init(Theme theme)
	{
		m_theme = theme;
		active();
		initOutputWindow();
	}
	
	void active()
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
	
	void initOutputWindow()
	{
		m_output = new OutputWindow(
				m_theme);
	}
	
	void displayMsg(Information information) throws InterruptedException
	{
		if(isActive())
		{
			(new MsgDialog(information)).display();
			Pause.get().enable();
		}
		else
			System.out.println("Message: " + information.getHeader() + "(" + information.getMsg() + ")");
	}
	
	void displayInterfaceMsg(String header, int textAlign, String... message)
	{
		if(isActive())
		{
			(new MsgDialog(true, header, textAlign, message)).display();
		}
		else
		{
			String tmp ="";
			for(int i=0; i<message.length; i++)
				tmp+=message[i]+"\n";
			System.out.println("Message: " + header + "(" + tmp + ")");
		}
	}
	
	void displayInterfaceMsg(Information information)
	{
		if(isActive())
		{
			(new MsgDialog(true, information)).display();
		}
		else
			System.out.println("Message: " + information.getHeader() + "(" + information.getMsg() + ")");
	}	
	
	void printInOutputWindow(Information information) throws BadLocationException
	{
		if(m_output.isAvaliable())
			m_output.add(information);
		else
			System.out.print(information.toString());
		
	}
	
	void displayOutputWindow()
	{
		m_output.display();
	}

}

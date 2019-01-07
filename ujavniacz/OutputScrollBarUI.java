
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

final class OutputScrollBarUI extends BasicScrollBarUI
{	
	private static final int INIT_THUMB_WIDTH = 32;
	private static final int INIT_THUMB_HEIGHT = 32;
	private static final int INIT_TRACK_WIDTH = 32;
	private static final int INIT_TRACK_HEIGHT = 32;
	
	OutputScrollBarUI(Theme theme)
	{
		m_theme = theme;
		
		m_emptyButton = new JButton();
   	 	m_emptyButton.setPreferredSize(new Dimension(0,0));
	}
	
	private JButton m_emptyButton;
	private Theme m_theme;
	
	private Image createImage(int width, int height, Color color)
	{
		BufferedImage bufferedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufferedImg.createGraphics();
		graphics.setPaint(color);
		graphics.fillRect(0, 0, width, height);
		graphics.dispose();
		return bufferedImg;
	}
	
	private Image createThumb()
	{
		return createImage(INIT_THUMB_WIDTH, INIT_THUMB_HEIGHT, m_theme.getColorForeground());
	}
	
	private Image createTrack()
	{
		return createImage(INIT_TRACK_WIDTH, INIT_TRACK_HEIGHT, m_theme.getColorBackground());
	}
	
	private void draw(Graphics graph, Rectangle rect, Image img)
	{
		Graphics2D graph2d = (Graphics2D)graph;
		graph2d.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
	}
	
	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r)
	{
		draw(g, r, createThumb());
	}
	
	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r)
	{
		draw(g, r, createTrack());
	}
	
	@Override
    protected JButton createDecreaseButton(int orientation)
    {
		return m_emptyButton;
    }
	
	@Override
    protected JButton createIncreaseButton(int orientation)
    {
		return m_emptyButton;
    }
}

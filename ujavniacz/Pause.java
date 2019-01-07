
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;

/**
 * Single-instance class, used to pause and resume current action.
 */
public class Pause
{
	private static Pause s_pause;
	private static boolean s_ready=false;
	
	private Pause()
	{
		s_ready = true;
	}
	
	private static void init()
	{
		s_pause = new Pause();		
	}
	
	/**
	 * Provides access to Pause object instance. Creates instance if is not initialized yet.
	 * @return Pause instance
	 */
	public static Pause get()
	{
		if(!s_ready)
		{
			init();
		}
		return s_pause;
	}
	
	private boolean m_wait = false;
	
	/**
	 * Pauses current action, running looped java.lang.Thread.sleep(long) function.
	 * @throws InterruptedException Thrown by internal <i>Thread.sleep(long)</i> function.
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#sleep-long-">Thread.sleep(long)</a>
	 * @see <a target=_blank href="https://docs.oracle.com/javase/8/docs/api/java/lang/InterruptedException.html">InterruptedException</a>
	 */
	public void enable() throws InterruptedException
	{
		m_wait = true;
					
		while(m_wait)
		{
			Thread.sleep(1);
		}
	}
	
	/**
	 * Breaks active pause loop
	 */
	public void disable()
	{
		m_wait = false;
	}
}

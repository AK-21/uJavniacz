
/*
 * Copyright (c) 2018 Arkadiusz Kostyra
 * Distributed under the MIT License. See LICENSE.txt file for full information.
 */

package ujavniacz;
/**
 *	Flag, which defines report category
 */
public enum Importance
{
	/**
	 * Flag used in normal event information
	 */
	INFO, 
	/**
	 * Flag used in reports about abnormal situations, that may have slight negative effect for program running
	 */
	WARNING,
	/**
	 * Flag used in reports about situations, that have critical effect for program running
	 */
	CRITICAL
}

package fr.neatmonster.nocheatplus.components;

import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * This component might be called periodically. Might not be called ever.
 * @author mc_dev
 *
 */
public interface ConsistencyChecker {
	/**
	 * Perform consistency checking. Depending on configuration this should clean up inconsistent states and/or log problems.
	 * @param onlinePlayers Players as returned by Server.getOnlinePlayers, at the point of time before checking.
	 */
	public void checkConsistency(Collection<? extends Player> onlinePlayers);
	
	// TODO: Might add method to check consistency for single players (on join, on certain check failures).
}

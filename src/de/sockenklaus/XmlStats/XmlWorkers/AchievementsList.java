/**
 * 
 */
package de.sockenklaus.XmlStats.XmlWorkers;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import de.sockenklaus.XmlStats.Objects.Achievements;

/**
 * @author socrates
 *
 */
public class AchievementsList extends XmlWorker {

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getXml(java.util.Map)
	 */
	@Override
	protected Element getXml(Map<String, List<String>> parameters) {
		Achievements ach = new Achievements();
		return ach.getXml(this.doc);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getSumXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getSumXml(List<String> playerList, Map<String, List<String>> parameters) {
		return this.getXml(parameters);
	}

	/* (non-Javadoc)
	 * @see de.sockenklaus.XmlStats.XmlWorkers.XmlWorker#getUserXml(java.util.List, java.util.Map)
	 */
	@Override
	protected Element getUserXml(List<String> playerList, Map<String, List<String>> parameters) {
		return this.getXml(parameters);
	}
}

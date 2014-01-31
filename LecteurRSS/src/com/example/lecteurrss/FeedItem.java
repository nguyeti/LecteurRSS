/**
 * Represent an item in the xml file
 * @author Timothée Nguyen & Etienne Nguyen
 */

package com.example.lecteurrss;

public class FeedItem {
	private String title;
	private String link;

	/**
	 * Set the title of the feed item
	 * 
	 * @param title
	 *            the given title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Set the link of the feed item
	 * 
	 * @param link
	 *            the given link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * Get the title of the feed item
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get the link of the feed item
	 * 
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Description of the object
	 */
	@Override
	public String toString() {
		 return title;
	}

}

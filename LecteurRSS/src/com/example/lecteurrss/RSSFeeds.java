/**
 * This class represents a RSSFeed.
 * @author Timothée Nguyen & Etienne Nguyen
 */
package com.example.lecteurrss;

public class RSSFeeds{
	private String feedId;
	private String feedName, feedLink;
	
	public RSSFeeds(){}
	
	public RSSFeeds(String feedName, String feedLink){
		this.feedName = feedName;
		this.feedLink = feedLink;
	}
	
	/**
	 * Set the name of the feed
	 * @param feedName the feed name
	 */
	public void setFeedName(String feedName){
		this.feedName = feedName;
	}
	
	/**
	 * Get the name of the feed
	 * @return the name of the feed
	 */
	public String getFeedName(){
		return feedName;
	}
	
	/**
	 * Set the link of the feed
	 * @param feedLink the link of the feed
	 */
	public void setFeedLink(String feedLink){
		this.feedLink = feedLink;
	}
	
	/**
	 * Get the link of the feed
	 * @return the link of the feed
	 */
	public String getFeedLink(){
		return feedLink;
	}
	
	/**
	 * Set an id for the feed
	 * @param feedId the id of the feed
	 */
	public void setFeedId(String feedId){
		this.feedId = feedId;
	}
	
	/**
	 * Get the id of the feed 
	 * @return the id of the feed
	 */
	public String getFeedId(){
		return feedId;
	}
	
	/**
	 * Describe the object
	 */
	public String toString(){
		return feedName;
	}
}
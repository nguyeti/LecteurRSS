/**
 * This is the parser class in which we get the title and link of an item in the xml file
 * @author Timothée Nguyen & Etienne Nguyen
 */
package com.example.lecteurrss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlParser {
	// name of XML tags that we need
	static final String ITEM = "item";
	static final String TITLE = "title";
	static final String LINK = "link";

	ArrayList<FeedItem> feedItemList = null;
	private FeedItem currentFeedItem = null;
	private String currentTag = null;

	public ArrayList<FeedItem> parse(InputStream is)
			throws XmlPullParserException, IOException {
		// Create a XmlPullParser instance with the XmlPullParserFactory
		XmlPullParser parser = XmlPullParserFactory.newInstance()
				.newPullParser();
		parser.setInput(is, "UTF-8");

		// Get the event type
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				feedItemList = new ArrayList<FeedItem>();
				break;
			case XmlPullParser.START_TAG:
				currentTag = parser.getName();
				if (currentTag.equalsIgnoreCase(ITEM)) {
					currentFeedItem = new FeedItem();
				} else if (currentFeedItem != null) {
					if (currentTag.equalsIgnoreCase(TITLE)) {
						currentFeedItem.setTitle(parser.nextText());
					} else if (currentTag.equalsIgnoreCase(LINK)) {
						currentFeedItem.setLink(parser.nextText());
					}
				}
				break;
			case XmlPullParser.END_TAG:
				currentTag = parser.getName();
				if (currentTag.equalsIgnoreCase(ITEM)
						&& currentFeedItem != null) {
					feedItemList.add(currentFeedItem);
				}
				break;
			}
			eventType = parser.next();
		}
		return feedItemList;
	}
}

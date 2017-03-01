package com.fractals.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * Reads a RSS feed xml from a provider and then parses it retrieve vital
 * information for the purpose of adding it to a website.
 *
 * @author Danieil Skrinikov
 * @version
 * @since 2017-02-07
 */
public class RSSFeedParser {

    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    /**
     * Creates the feed parser object.
     *
     * @param feedUrl URL to the RSS feed that you want to parse. Wwill throw an
     * exception if the URL is malformed.
     */
    public RSSFeedParser() {
    }

    /**
     * Takes an array of of urls from different RSS feeds and then reads the feed
     * for each. 
     * 
     * @param links Array of rss feed links.
     * @return feeds of all the provided valid urls in the order which it given to.
     */
    public List<FeedMessage> readFeed(String[] links) {
        if (links == null || links.length < 1) {
            return new ArrayList<FeedMessage>();
        }

        List<FeedMessage> fm = new ArrayList<>();
        URL url;

        for (String urlString : links) {
            try {
                url = new URL(urlString);
                
                //Calling the parseURL method to get a collection.
                fm.addAll(parseURL(url));
                
            } catch (MalformedURLException | XMLStreamException e) {
                //TODO Add log.
            }
        }

        return fm;
    }

    /**
     * Reads the feed from the given URL and then parses the response to select
     * information about each <item> only.
     *
     * @return list which contains all the RSS items as FeedMessage beans.
     */
    private List<FeedMessage> parseURL(URL url) throws XMLStreamException {
        XMLEvent e;
        List<FeedMessage> feed = new ArrayList<>();

        String title = "";
        String description = "";
        String link = "";
        String guid = "";
        String author = "";
        //try {
            // XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            InputStream in = read(url);

            if (in != null) {
                XMLEventReader eventReader = inputFactory.createXMLEventReader(in);               

                // read the XML document
                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();
                    if (event.isStartElement()) {

                        String localPart = event.asStartElement().getName()
                                .getLocalPart();

                        switch (localPart) {
                            case TITLE:
                                title = getCharacterData(event, eventReader);
                                break;

                            case DESCRIPTION:
                                description = getCharacterData(event, eventReader).trim();
                                break;

                            case LINK:
                                link = getCharacterData(event, eventReader);
                                break;

                            case GUID:
                                guid = getCharacterData(event, eventReader);
                                break;

                            case AUTHOR:
                                author = getCharacterData(event, eventReader);
                                break;

                        }
                    } // Basically if </tag>
                    else if (event.isEndElement()) {
                        if (event.asEndElement().getName().getLocalPart().equalsIgnoreCase(ITEM)) {

                            FeedMessage message = new FeedMessage();
                            message.setAuthor(author);
                            message.setDescription(description);
                            message.setGuid(guid);
                            message.setLink(link);
                            message.setTitle(title);

                            feed.add(message);
                            eventReader.nextEvent();
                        }
                    }
                }
            }
        //} catch (XMLStreamException ex) {
            //System.out.println(ex);
        //}
        return feed;
    }

    /**
     * Parses the characters from an event into a String.
     *
     * @param event XML tag.
     * @param eventReader XML tag reader.
     * @return String which is inside the XML tag.
     * @throws XMLStreamException if cannot read characters.
     */
    private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        while (event instanceof Characters && !event.isEndElement()) {
            result += event.asCharacters().getData();
            event = eventReader.nextEvent();
        }
        return result;
    }

    /**
     * Reads the XML from the RSS feed provider.
     *
     * @return InputStream which contains the XML from the RSS Feed.
     */
    private InputStream read(URL url) {
        try {
            return url.openStream();
        } catch (IOException e) {
            //
        }
        return null;
    }
}

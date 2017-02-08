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

    final URL url;

    public RSSFeedParser(String feedUrl) {
        try {
            this.url = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FeedMessage> readFeed() {
        XMLEvent e;
        List<FeedMessage> feed = new ArrayList<>();

        String title = "";
        String description = "";
        String link = "";
        String guid = "";
        String author = "";
        try {
            // XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            // read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                //System.out.println(event.asStartElement().getName().getLocalPart());
                if (event.isStartElement()) {

                    System.out.println(event.asStartElement().getName().getLocalPart());

                    String localPart = event.asStartElement().getName()
                            .getLocalPart();

                    switch (localPart) {
                        case TITLE:
                            title = getCharacterData(event, eventReader);
                            System.out.println("Title: " + title);
                            break;

                        case DESCRIPTION:
                            eventReader.nextEvent();
                            description = getCharacterData(event, eventReader).trim();
                            System.out.println("Description: " + description);
                            break;

                        case LINK:
                            link = getCharacterData(event, eventReader);
                            System.out.println("Link: " + link);
                            break;

                        case GUID:
                            guid = getCharacterData(event, eventReader);
                            break;

                        case AUTHOR:
                            author = getCharacterData(event, eventReader);
                            break;

                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == ITEM) {

                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);

                        feed.add(message);
                        event = eventReader.nextEvent();
                    }
                }
            }
        } catch (XMLStreamException ex) {
            System.out.println("Crashed");
        }
        return feed;
    }

    private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }

    private InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

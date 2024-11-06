package ru.skillbox.currency.exchange.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class ExchangeRateCentralBank extends DefaultHandler {

    private final XMLHandler handler;
    @Value("${centralBank.url}")
    private String url;

    public void parseFile() throws SAXException, ParserConfigurationException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(url, handler);
    }
}

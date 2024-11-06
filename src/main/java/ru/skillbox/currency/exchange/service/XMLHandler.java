package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;
@Component
@Slf4j
@RequiredArgsConstructor
public class XMLHandler extends DefaultHandler {
    private StringBuilder currentValue;
    private Currency currency;

    private final CurrencyRepository repository;
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("Valute")) {
            currency = new Currency();
        }
        currentValue = new StringBuilder();
    }
    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("NumCode")) {
            currency.setIsoNumCode(Long.parseLong(currentValue.toString()));
        }
        if(qName.equals("CharCode")) {
            currency.setIsoLetterCode(currentValue.toString());
        }
        if (qName.equals("Nominal")) {
            currency.setNominal(Long.parseLong(currentValue.toString()));
        }
        if(qName.equals("Name")) {
            currency.setName(currentValue.toString());
        }
        if(qName.equals("Value")) {
            currency.setValue(Double.parseDouble(currentValue.toString().replaceAll(",", ".")));
        }
        if(qName.equals("Valute")) {
            Currency currencyDb = repository.findByIsoLetterCode(currency.getIsoLetterCode());
            if(currencyDb == null) {
                repository.save(currency);
                log.info("Добавлена валюта: " + currency.getName());
            } else {
                currencyDb.setValue(currency.getValue());
                repository.save(currencyDb);
                log.info("Обновлена валюта: " + currency.getName());
            }
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) {
        currentValue.append(ch, start, length);
    }

}

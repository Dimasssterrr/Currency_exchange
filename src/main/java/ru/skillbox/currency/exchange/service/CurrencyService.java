package ru.skillbox.currency.exchange.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.skillbox.currency.exchange.dto.CurrenciesDto;
import ru.skillbox.currency.exchange.dto.CurrencyDto;
import ru.skillbox.currency.exchange.dto.CurrencySimpleDto;
import ru.skillbox.currency.exchange.entity.Currency;
import ru.skillbox.currency.exchange.mapper.CurrencyMapper;
import ru.skillbox.currency.exchange.repository.CurrencyRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyMapper mapper;
    private final CurrencyRepository repository;
    private final ExchangeRateCentralBank rateCentralBank;

    public CurrencyDto getById(Long id) {
        log.info("CurrencyService method getById executed");
        Currency currency = repository.findById(id).orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return mapper.convertToDto(currency);
    }

    public Double convertValue(Long value, Long numCode) {
        log.info("CurrencyService method convertValue executed");
        Currency currency = repository.findByIsoNumCode(numCode);
        return value * currency.getValue();
    }

    public CurrencyDto create(CurrencyDto dto) {
        log.info("CurrencyService method create executed");
        return  mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
    }
    public CurrenciesDto getAllCurrencies() {
        List<Currency> currencyList = repository.findAll();
        List<CurrencySimpleDto> currencySimpleDtoList = new ArrayList<>();
        currencyList.forEach(currency -> {
            CurrencySimpleDto currencySimpleDto = mapper.convertToSimpleDto(currency);
           currencySimpleDtoList.add(currencySimpleDto);
        });
        log.info("CurrencyService method getAllCurrencies executed");
        return new CurrenciesDto(currencySimpleDtoList);
    }
    @Scheduled(fixedDelayString = "3600000")
    public void trackingCostCurrency() {
        try {
            log.info("Запрос на обновление курсов отправлен!");
            rateCentralBank.parseFile();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}

package ru.skillbox.currency.exchange.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {
    private Long id;

    private String name;

    private Long nominal;

    private Double value;

    private Long isoNumCode;
    
    private String isoLetterCode;
}
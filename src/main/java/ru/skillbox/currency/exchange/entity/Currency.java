package ru.skillbox.currency.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@Getter
@Entity
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "create_sequence", allocationSize = 0)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "nominal")
    private Long nominal;

    @Column(name = "value")
    private Double value;

    @Column(name = "iso_num_code")
    private Long isoNumCode;

    @Column(name = "iso_letter_code")
    private String isoLetterCode;

}

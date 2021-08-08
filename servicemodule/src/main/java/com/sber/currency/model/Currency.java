package com.sber.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Currency {
    private Integer numCode;
    private String charCode;
    private String name;
    @JsonIgnore
    private String value;
}

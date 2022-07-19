package com.buildermaster.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
public class Teste {
    private Integer id;
    private List<Teste2> t2;
}

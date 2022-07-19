package com.buildermaster.builder;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log
public class BuilderInformation {
    private Integer classQuantity;
    private Integer validClassQuantity;
    private Integer createdClassQuantity;
    private Integer notCreatedClassQuantity;
    private List<String> classList;

    public BuilderInformation()
    {
        this.classQuantity = 0;
        this.validClassQuantity = 0;
        this.createdClassQuantity = 0;
        this.notCreatedClassQuantity = 0;
        this.classList = new ArrayList<>();
    }

    public void logConsole()
    {
        log.info("===Builder Master executado===");
        classList.parallelStream().forEach(item -> log.info("Classe criada: " + item));
        log.info("Quantidade de classes recebidas: " + classQuantity);
        log.info("Quantidade de classes validas: " + validClassQuantity);
        log.info("Total de classes criadas: " + createdClassQuantity);
        log.info("Falhas: " + notCreatedClassQuantity);
        log.warning("Classes iguais e que serão criadas no mesmo pacote são removidas da lista. Portanto não são criadas!");
    }
}

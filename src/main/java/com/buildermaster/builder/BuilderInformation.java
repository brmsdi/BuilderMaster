package com.buildermaster.builder;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
        System.out.println("===Builder Master executado===");
        classList.parallelStream().forEach(item -> System.out.println("Classe criada: " + item));
        System.out.println("Quantidade de classes recebidas: " + classQuantity);
        System.out.println("Quantidade de classes validas: " + validClassQuantity);
        System.out.println("Total de classes criadas: " + createdClassQuantity);
        System.out.println("Falhas: " + notCreatedClassQuantity);
        System.out.println("Classes iguais e que serão criadas no mesmo pacote são removidas da lista. Portanto não são criadas!");
    }
}

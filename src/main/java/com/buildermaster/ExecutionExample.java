package com.buildermaster;

import com.buildermaster.builder.BuilderConfigureParam;
import com.buildermaster.builder.BuilderMasterCreateClass;
import com.buildermaster.builder.engine.engineImpl.GenerateEngineToConsole;
import com.buildermaster.builder.engine.engineImpl.GenerateEngineToFileJava;
import com.buildermaster.models.Teste;
import com.buildermaster.models.Teste2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * CLASSE EXEMPLO PARA CRIAR BUILDERMASTERS NO CONSOLE OU EM ARQUIVO JAVA
 * Para gerar os builders no console utiliza-se a classe GenerateEngineToConsole implementação de GenerateEngine.
 * Para gerar arquvios java utiliza-se a classe GenerateEngineToFileJava implementação de GenerateEngine.
 * @author WISLEY BRUNO MARQUES FRANÇA
 */
@Component
public class ExecutionExample {

    //@Bean
    public static void initializeToConsole() {
       BuilderMasterCreateClass
               .prepare()
               .execute(Set.of(
                       new BuilderConfigureParam("com.buildermaster", Teste2.class),
                       new BuilderConfigureParam("com.buildermaster", Teste.class),
                       new BuilderConfigureParam("com.buildermaster", Teste.class),
                       new BuilderConfigureParam("com.buildermaster1", Teste.class),
                       new BuilderConfigureParam("com.buildermaster2", Teste.class),
                       new BuilderConfigureParam("com.buildermaster3", Teste.class)), new GenerateEngineToConsole());
    }

    //@Bean
    public static void initializeToFile() {
        BuilderMasterCreateClass
                .prepare()
                .execute(Set.of(
                        new BuilderConfigureParam("com.buildermaster", Teste2.class),
                        new BuilderConfigureParam("com.buildermaster", Teste.class),
                        new BuilderConfigureParam("com.buildermaster", Teste.class),
                        new BuilderConfigureParam("br.teste.models", Teste.class)), new GenerateEngineToFileJava());
    }
}

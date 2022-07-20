package com.buildermaster.builder.engine.engineImpl;

import com.buildermaster.builder.BuilderInformation;
import com.buildermaster.builder.engine.GenerateEngine;

import java.util.Set;

public class GenerateEngineToConsole implements GenerateEngine {

    @Override
    public void generate(String packageName, Set<String> listImports, Class<?> _class, StringBuilder builder, BuilderInformation builderInformation) {
        System.out.printf("package %s;", packageName);
        System.out.println("\n");
        for(String str: listImports) {
            System.out.println(str);
        }
        System.out.println("import " + _class.getCanonicalName() + ";");
        System.out.println("");
        System.out.println(builder.toString());
        System.out.println("");
        System.out.printf("CREATED CLASS %s ==============================================================================", _class.getSimpleName());
        System.out.println("\n");
        builderInformation.setCreatedClassQuantity(builderInformation.getCreatedClassQuantity()+1);
    }
}

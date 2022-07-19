package com.buildermaster.builder.engine;

import com.buildermaster.builder.BuilderInformation;

import java.util.Set;

public interface GenerateEngine {
    void generate(String packageName, Set<String> listImports, Class<?> _class, StringBuilder builder, BuilderInformation builderInformation);
}

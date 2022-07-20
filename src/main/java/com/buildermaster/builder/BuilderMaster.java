package com.buildermaster.builder;

import com.buildermaster.builder.engine.GenerateEngine;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.buildermaster.utils.MasterUtils.initLowerLetter;

/**
 * Classe responsável pela criação de builders de entidades
 * Classe modificada por Wisley Bruno Marques França
 * @author wcaquino
 *
 */
public class BuilderMaster {

    Set<String> listImports;

    private final GenerateEngine generateEngine;
    private final BuilderInformation builderInformation;

    public BuilderMaster(GenerateEngine generateEngine, BuilderInformation builderInformation) {
        listImports = new HashSet<String>();
        listImports.add("import java.util.Arrays;");
        this.generateEngine = generateEngine;
        this.builderInformation = builderInformation;
    }

    @SuppressWarnings("rawtypes")
    public void generateCodeClass(String packageName, Class _class) {
        String nameClass = _class.getSimpleName() + "Builder";
        StringBuilder builder = new StringBuilder();
        builder.append("public class ").append(nameClass).append(" {\n");
        builder.append("\tprivate ")
                .append(_class.getSimpleName())
                .append(String.format(" %s;\n", initLowerLetter(_class.getSimpleName())));

        builder.append("\tprivate ").append(nameClass).append("(){}\n\n");

        builder.append("\tpublic static ").append(nameClass).append(" newInstance").append("() {\n");
        builder.append("\t\t").append(nameClass).append(" builder = new ").append(nameClass).append("();\n");
        builder.append("\t\tinitializeDefaultData(builder);\n");
        builder.append("\t\treturn builder;\n");
        builder.append("\t}\n\n");

        builder.append("\tpublic static void initializeDefaultData(").append(nameClass).append(" builder) {\n");
        builder.append("\t\tbuilder.")
                .append(String.format("%s = new ", initLowerLetter(_class.getSimpleName())))
                .append(_class.getSimpleName()).append("();\n");
        builder.append("\t\t").append(_class.getSimpleName())
                .append(String.format(" %s = builder.%s;\n", initLowerLetter(_class.getSimpleName()), initLowerLetter(_class.getSimpleName())));
        builder.append("\t\t\n");

        List<Field> declaredFields = getClassFields(_class);
        for(Field field: declaredFields) {
            if(field.getName().equals("serialVersionUID"))
                continue;
            if(Modifier.isStatic(field.getModifiers()))
                continue;
            builder.append(String.format("\t\t%s.set", initLowerLetter(_class.getSimpleName())))
                    .append(field.getName().substring(0, 1).toUpperCase())
                    .append(field.getName().substring(1)).append("(")
                    .append(getDefaultParameter(field)).append(");\n");

        }
        builder.append("\t}\n\n");

        for(Field field: declaredFields) {
            if(field.getName().equals("serialVersionUID"))
                continue;
            if(Modifier.isStatic(field.getModifiers()))
                continue;
            if(field.getType().getSimpleName().equals("List")) {
                ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
                builder.append("\tpublic ")
                        .append(nameClass)
                        .append(" comLista").append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1))
                        .append("(").append(((Class)stringListType.getActualTypeArguments()[0]).getSimpleName()).append("... params) {\n");
                builder.append(String.format("\t\t%s.set", initLowerLetter(_class.getSimpleName())))
                        .append(field.getName().substring(0, 1).toUpperCase())
                        .append(field.getName().substring(1))
                        .append("(Arrays.asList(params));\n");

                builder.append("\t\treturn this;\n");
                builder.append("\t}\n\n");
            } else {
                builder.append("\tpublic ")
                        .append(nameClass)
                        .append(" with").append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1))
                        .append("(").append(field.getType().getSimpleName()).append(" param) {\n");
                registerImports(field.getType().getCanonicalName());
                builder.append(String.format("\t\t%s.set", initLowerLetter(_class.getSimpleName())))
                        .append(field.getName().substring(0, 1).toUpperCase()).append(field.getName().substring(1))
                        .append("(param);\n");
                builder.append("\t\treturn this;\n");
                builder.append("\t}\n\n");
            }
        }
        builder.append("\tpublic ").append(_class.getSimpleName()).append(" now() {\n");
        builder.append(String.format("\t\treturn %s;\n", initLowerLetter(_class.getSimpleName())));
        builder.append("\t}\n");
        builder.append("}");
        generateEngine.generate(packageName, listImports, _class, builder, builderInformation);
    }

    @SuppressWarnings("rawtypes")
    public List<Field> getClassFields(Class _class) {
        List<Field> fields = new ArrayList<>(Arrays.asList(_class.getDeclaredFields()));
        Class superClass = _class.getSuperclass();
        if(superClass != Object.class) {
            List<Field> fieldsSC = Arrays.asList(superClass.getDeclaredFields());
            fields.addAll(fieldsSC);
        }
        return fields;
    }

    public String getDefaultParameter(Field campo) {
        String type = campo.getType().getSimpleName();
        if(type.equals("int") || type.equals("Integer")){
            return "0";
        }
        if(type.equals("long") || type.equals("Long")){
            return "0L";
        }
        if(type.equals("double") || type.equals("Double")){
            return "0.0";
        }
        if(type.equals("boolean") || type.equals("Boolean")){
            return "false";
        }
        if(type.equals("String")){
            return "\"\"";
        }
        return "null";
    }

    public void registerImports(String classe) {
        if(classe.contains("."))
            listImports.add("import " + classe + ";");
    }
}
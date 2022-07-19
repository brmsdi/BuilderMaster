package com.buildermaster.builder.engine.engineImpl;

import com.buildermaster.builder.BuilderInformation;
import com.buildermaster.builder.engine.GenerateEngine;
import lombok.extern.java.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.Set;

@Log
public class GenerateEngineToFileJava implements GenerateEngine {
    private static final Path path = Path.of(System.getProperty("user.dir"));
    private static final String PATH_BASE = "buildersGenerated";
    private static final String EXTENSION_FILE = ".java";
    private File pathFile;
    private StringBuilder fileText;

    @Override
    public void generate(String packageName, Set<String> listImports, Class<?> _class,  StringBuilder builder, BuilderInformation builderInformation) {
        fileText = new StringBuilder();
        fileText.append(String.format("package %s;\n\n", packageName));
        listImports.forEach(item -> fileText.append(String.format("%s\n", item)));
        fileText.append(String.format("import %s;\n", _class.getCanonicalName()));
        fileText.append("\n").append(builder);
        if (createDirectory(PATH_BASE.concat(File.separator).concat(removeDotAndPutSlash(packageName))))
        {
            createFile(_class, builderInformation);
        } else {
            builderInformation.setNotCreatedClassQuantity(builderInformation.getNotCreatedClassQuantity()+1);
        }
    }

    private boolean createDirectory(String packagePath) {
        pathFile = new File(path.toString().concat(File.separator).concat(packagePath));
        if (!pathFile.isDirectory())
        {
            if (!pathFile.mkdirs()) {
                log.warning("Não foi possível criar o diretório para armazenar os builders");
                log.warning("Diretório: " + pathFile.toString());
                return false;
            }
        }
        return true;
    }

    private String removeDotAndPutSlash(String text)
    {
        return text.replace(".", File.separator);
    }

    private void createFile(Class<?> _class, BuilderInformation builderInformation)
    {
        try (Formatter formatter = new Formatter(pathFile.toString().concat(File.separator).concat(_class.getSimpleName()).concat(EXTENSION_FILE))) {
            formatter.format("%s", fileText);
            builderInformation.setCreatedClassQuantity(builderInformation.getCreatedClassQuantity()+1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

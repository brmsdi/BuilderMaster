package com.buildermaster.builder;

import com.buildermaster.builder.engine.GenerateEngine;
import com.buildermaster.proxies.ProxyBuilderMasterCreateClass;
import org.springframework.cglib.proxy.Enhancer;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.buildermaster.utils.MasterUtils.isValidClass;

public class BuilderMasterCreateClass {
    static BuilderInformation builderInformation = new BuilderInformation();
    public static BuilderMasterCreateClass prepare() {
        ProxyBuilderMasterCreateClass proxyBuilderMasterCreateClass = new ProxyBuilderMasterCreateClass();
        proxyBuilderMasterCreateClass.setBuilderInformation(builderInformation);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BuilderMasterCreateClass.class);
        enhancer.setCallback(proxyBuilderMasterCreateClass);
        return (BuilderMasterCreateClass) enhancer.create();
    }

    public void execute(Set<BuilderConfigureParam> builderConfigureParamSet, GenerateEngine generateEngine) {
        builderConfigureParamSet
                .forEach(param -> new BuilderMaster(generateEngine, builderInformation).generateCodeClass(param.getPackageClass(), param.getIClass()));
    }

    public void executeAllInThePackage(String filePath, String classPackage, GenerateEngine generateEngine) {
        try {
            Path pathBase = Path.of(System.getProperty("user.dir").concat(File.separator + filePath));
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(pathBase);
            Set<Path> paths = new HashSet<>();
            directoryStream.forEach(paths::add);
            Set<BuilderConfigureParam> BuilderConfigureParams = paths
                    .parallelStream()
                    .filter(path -> new File(path.toString()).isFile())
                    .filter(path -> path.getFileName().toString().endsWith(".java"))
                    .filter(path -> isValidClass(classPackage, path))
                    .map(path -> createBuilderConfigureParam(classPackage, path))
                    .collect(Collectors.toSet());
            execute(BuilderConfigureParams, generateEngine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BuilderConfigureParam createBuilderConfigureParam(String classPackage, Path path)
    {
        try {
            return new BuilderConfigureParam(classPackage, Class.forName(classPackage.concat(".").concat(path.getFileName().toString().replace(".java", ""))));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

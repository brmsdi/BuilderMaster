package com.buildermaster.proxies;

import com.buildermaster.builder.BuilderConfigureParam;
import com.buildermaster.builder.BuilderInformation;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.buildermaster.utils.MasterUtils.isValidClass;

public class ProxyBuilderMasterCreateClass implements MethodInterceptor {
    private BuilderInformation builderInformation;

    public void setBuilderInformation(BuilderInformation builderInformation) {
        this.builderInformation = builderInformation;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object intercept(Object superClass, Method method, Object[] arguments, MethodProxy methodProxy) throws Throwable {
        if (method.getDeclaringClass() != Object.class && method.getName().equals("execute"))
        {
            Set<BuilderConfigureParam> oldArguments = (Set<BuilderConfigureParam>) arguments[0];
            Set<BuilderConfigureParam> newsArguments = oldArguments
                    .parallelStream()
                    .filter(param -> isValidClass(param.getIClass()))
                    .collect(Collectors.toCollection(() ->
                            new TreeSet<>(Comparator.comparing(builderConfigureParam -> builderConfigureParam.getPackageClass().concat(" " + builderConfigureParam.getIClass().toString())))));
            builderInformation.setClassQuantity(oldArguments.size());
            builderInformation.setValidClassQuantity(newsArguments.size());
            Object object = methodProxy.invokeSuper(superClass, new Object[] { newsArguments, arguments[1] });
            builderInformation.setClassList(newsArguments.parallelStream().map(arg -> arg.getPackageClass().concat(" " + arg.getIClass().getSimpleName())).toList());
            builderInformation.logConsole();
            return object;
        } else if (method.getDeclaringClass() != Object.class && method.getName().equals("executeAllInThePackage")){
            String filePath = String.valueOf(arguments[0]).replace(".", File.separator) ;
            Path path = Path.of(System.getProperty("user.dir").concat(File.separator + filePath));
            if (!Files.isDirectory(path)) {
                System.out.println(path);
                System.out.println("Diretório invalido!");
                return null;
            }
            return methodProxy.invokeSuper(superClass, new Object[] { filePath, arguments[1], arguments[2] });
        }
        return methodProxy.invokeSuper(superClass, arguments);
    }
}

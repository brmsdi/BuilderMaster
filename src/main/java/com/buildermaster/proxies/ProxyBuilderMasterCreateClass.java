package com.buildermaster.proxies;

import com.buildermaster.builder.BuilderConfigureParam;
import com.buildermaster.builder.BuilderInformation;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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
                    .collect(Collectors.toCollection(() ->
                            new TreeSet<>(Comparator.comparing(builderConfigureParam -> builderConfigureParam.getPackageClass().concat(" " + builderConfigureParam.getIClass().toString())))));
            builderInformation.setClassQuantity(oldArguments.size());
            builderInformation.setValidClassQuantity(newsArguments.size());
            Object object = methodProxy.invokeSuper(superClass, new Object[] { newsArguments, arguments[1] });
            builderInformation.setClassList(newsArguments.parallelStream().map(arg -> arg.getPackageClass().concat(" " + arg.getIClass().getSimpleName())).toList());
            builderInformation.logConsole();
            return object;
        }
        return methodProxy.invokeSuper(superClass, arguments);
    }
}

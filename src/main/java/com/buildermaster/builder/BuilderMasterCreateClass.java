package com.buildermaster.builder;

import com.buildermaster.builder.engine.GenerateEngine;
import com.buildermaster.proxies.ProxyBuilderMasterCreateClass;
import org.springframework.cglib.proxy.Enhancer;

import java.util.Set;

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
}

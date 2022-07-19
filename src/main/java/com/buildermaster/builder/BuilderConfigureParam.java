package com.buildermaster.builder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuilderConfigureParam {
    private String packageClass;
    private Class<?> iClass;

    public BuilderConfigureParam(String packageClass, Class<?> iClass)
    {
        this.packageClass = packageClass;
        this.iClass = iClass;
    }

}

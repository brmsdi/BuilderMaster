package com.buildermaster.utils;

import java.nio.file.Path;

public class MasterUtils {
    public static boolean isValidClass(String classPackage, Path path)
    {
        try {
            return Class.forName(classPackage.concat("." + path.getFileName().toString().replace(".java", ""))).getSuperclass() != null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidClass(Class<?> _class)
    {
        return _class != null && _class.getSuperclass() != null;
    }

    public static String initLowerLetter(String text)
    {
        String regex = ".*[A-Z].*";
        if (text.length() == 0) throw new IllegalArgumentException("O texto não pode estar vázio");
        if (!String.valueOf(text.charAt(0)).matches(regex)) return text.concat("Object");
        String startText = String.valueOf(text.charAt(0)).toLowerCase();
        return startText.concat( text.substring(1));
    }
}

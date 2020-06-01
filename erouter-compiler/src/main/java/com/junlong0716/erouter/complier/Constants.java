package com.junlong0716.erouter.complier;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
public class Constants {
    public static final String PACKAGE_NAME_EROUTER = "com.junlong0716.erouter.support";
    /**
     * 常用的 Android 类
     */
    protected static final String CLASS_NAME_ACTIVITY = "android.app.Activity";
    protected static final String CLASS_NAME_FRAGMENT = "android.app.Fragment";
    protected static final String CLASS_NAME_PARCELABLE = "android.os.Parcelable";
    protected static final String CLASS_NAME_BUNDLE = "android.os.Bundle";
    protected static final String CLASS_NAME_FRAGMENT_V4 = "android.support.v4.app.Fragment";
    protected static final String CLASS_NAME_FRAGMENT_X = "androidx.fragment.app.Fragment";


    /**
     * 常用类型
     */
    private static final String PACKAGE_NAME_LANG = "java.lang";
    protected static final String CLASS_NAME_BYTE = PACKAGE_NAME_LANG + ".Byte";
    protected static final String CLASS_NAME_SHORT = PACKAGE_NAME_LANG + ".Short";
    protected static final String CLASS_NAME_INTEGER = PACKAGE_NAME_LANG + ".Integer";
    protected static final String CLASS_NAME_LONG = PACKAGE_NAME_LANG + ".Long";
    protected static final String CLASS_NAME_FLOAT = PACKAGE_NAME_LANG + ".Float";
    protected static final String CLASS_NAME_DOUBLE = PACKAGE_NAME_LANG + ".Double";
    protected static final String CLASS_NAME_BOOLEAN = PACKAGE_NAME_LANG + ".Boolean";
    protected static final String CLASS_NAME_CHAR = PACKAGE_NAME_LANG + ".Character";
    protected static final String CLASS_NAME_STRING = PACKAGE_NAME_LANG + ".String";
    protected static final String CLASS_NAME_SERIALIZABLE = "java.io.Serializable";

    protected static final String DOT = ".";
    /**
     * 生成方法名称
     */
    protected static final String FUNCTION_QUERY_BIND = "bindExtras";
    protected static final String FUNCTION_QUERY_PARAMETER_NAME_TARGET = "target";
    protected static final String FUNCTION_QUERY_PARAMETER_NAME_EXTRA = "extra";
    protected static final String CLAZZ_ROUTER_PATH = "com.junlong0716.erouter.support.Constants";
    static final String SIMPLE_NAME_INTERFACE_QUERY_BINDING = "IQueryBinding";
    private static final String SIMPLE_NAME_SUB_SEPARATOR = "$$";
    protected static final String SIMPLE_NAME_SUFFIX_OF_QUERY_BINDING = SIMPLE_NAME_SUB_SEPARATOR + "QueryBinding";

}

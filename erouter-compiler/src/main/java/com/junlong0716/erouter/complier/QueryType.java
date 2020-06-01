package com.junlong0716.erouter.complier;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
public enum QueryType {
    // 基本类型
    BOOLEAN,
    BYTE,
    SHORT,
    INT,
    LONG,
    CHAR,
    FLOAT,
    DOUBLE,

    // 其他类型
    STRING,
    SERIALIZABLE,
    PARCELABLE,
}
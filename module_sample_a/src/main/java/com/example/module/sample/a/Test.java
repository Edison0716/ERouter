package com.example.module.sample.a;

import com.example.erouter.annotation.Query;

public class Test {
    @Query(key = "haha")
    String name;


    public String getName() {
        return name;
    }
}

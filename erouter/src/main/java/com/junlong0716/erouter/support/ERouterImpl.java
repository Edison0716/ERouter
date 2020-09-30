package com.junlong0716.erouter.support;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/09/30 14:19
 * desc : 门面模式 ERouter 的实现类
 */
public class ERouterImpl {
    @Nullable
    static Context mAppContext = null;

    static synchronized void init(Application app){
        mAppContext = app;
    }

    static <T> void bindQuery(T binder, Bundle args) {

    }
}

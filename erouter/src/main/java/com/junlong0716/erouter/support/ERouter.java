package com.junlong0716.erouter.support;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author 35068 EdisonLi <a href="junlong.li@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/09/30 14:19
 * desc : 门面模式 ERouter
 */
public class ERouter {

    public static synchronized void init(Application application){
        ERouterImpl.init(application);
    }

    /**
     * 绑定bindQuery
     * @param binder instanceof Context
     * @param args bundle
     * @param <T>
     */
    public static <T> void bindQuery(@Nullable T binder, @Nullable Bundle args){
        if (binder == null || args == null){
            return;
        }
        ERouterImpl.bindQuery(binder, args);
    }

}

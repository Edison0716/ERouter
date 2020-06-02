package com.junlong0716.erouter.support.utils;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Logger
 */
public class Logger {
    private static final String TAG_DEFAULT = Logger.class.getSimpleName();

    private static boolean isDebug = false;

    private static final ILogger REAL_LOGGER = new ILogger.LoggerImpl();

    // 动态代理
    private static final ILogger PROXY_LOGGER = (ILogger) Proxy.newProxyInstance(ILogger.class.getClassLoader(), new Class<?>[]{ILogger.class}, new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isDebug) {
                return method.invoke(REAL_LOGGER, args);
            }
            return null;
        }
    });

    private Logger() {
        throw new UnsupportedOperationException("禁止初始化");
    }

    public static void isDebug(boolean debug) {
        isDebug = debug;
    }

    /**
     * =============================== Verbose =================================
     */
    static void v(@NonNull CharSequence content) {
        v(defaultTag(), content.toString());
    }

    static void v(@NonNull String tag, @NonNull CharSequence content) {
        PROXY_LOGGER.v(tag, content.toString());
    }

    static void v(@NonNull CharSequence content, @NonNull Throwable e) {
        v(defaultTag(), e, content.toString());
    }

    static void v(@NonNull String tag, @NonNull Throwable e, @NonNull CharSequence content) {
        PROXY_LOGGER.v(tag, e, content.toString());
    }

    /**
     * =============================== Debug =================================
     */
    static void d(@NonNull CharSequence content) {
        d(defaultTag(), content.toString());
    }

    static void d(@NonNull String tag, @NonNull CharSequence content) {
        PROXY_LOGGER.d(tag, content.toString());
    }

    static void d(@NonNull CharSequence content, @NonNull Throwable e) {
        d(defaultTag(), e, content.toString());
    }

    static void d(@NonNull String tag, @NonNull Throwable e, @NonNull CharSequence content) {
        PROXY_LOGGER.d(tag, e, content.toString());
    }

    /**
     * =============================== Info =================================
     */
    public static void i(@NonNull CharSequence content) {
        i(defaultTag(), content.toString());
    }

    public static void i(@NonNull String tag, @NonNull CharSequence content) {
        PROXY_LOGGER.i(tag, content.toString());
    }

    public static void i(@NonNull CharSequence content, @NonNull Throwable e) {
        i(defaultTag(), e, content.toString());
    }

    static void i(@NonNull String tag, @NonNull Throwable e, @NonNull CharSequence content) {
        PROXY_LOGGER.i(tag, e, content.toString());
    }

    /**
     * =============================== Warn =================================
     */
    static void w(@NonNull CharSequence content) {
        w(defaultTag(), content.toString());
    }

    static void w(@NonNull String tag, @NonNull CharSequence content) {
        PROXY_LOGGER.w(tag, content.toString());
    }

    public static void w(@NonNull CharSequence content, @NonNull Throwable e) {
        w(defaultTag(), e, content.toString());
    }

    static void w(@NonNull String tag, @NonNull Throwable e, @NonNull CharSequence content) {
        PROXY_LOGGER.w(tag, e, content.toString());
    }

    /**
     * =============================== Error =================================
     */
    static void e(@NonNull CharSequence content) {
        e(defaultTag(), content.toString());
    }

    static void e(@NonNull String tag, @NonNull CharSequence content) {
        PROXY_LOGGER.e(tag, content.toString());
    }

    static void e(@NonNull CharSequence content, @NonNull Throwable e) {
        e(defaultTag(), e, content.toString());
    }

    static void e(@NonNull String tag, @NonNull Throwable e, @NonNull CharSequence content) {
        PROXY_LOGGER.e(tag, e, content.toString());
    }


    private static String defaultTag() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // 获取线程任务栈中深度为 4 的元素信息 为当前Logger提示所在的类
        StackTraceElement log = stackTrace[4];
        String className = log.getClassName();
        if (className.isEmpty()) {
            return TAG_DEFAULT;
        }
        // 截取最后一个位置的信息
        int subIndex = className.lastIndexOf(".") + 1;
        return className.substring(subIndex);
    }
}

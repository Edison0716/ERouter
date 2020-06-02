package com.junlong0716.erouter.support.utils;

import android.util.Log;

import androidx.annotation.NonNull;

public interface ILogger {

    void v(@NonNull String tag, @NonNull String content);

    void v(@NonNull String tag, @NonNull Throwable e, @NonNull String content);

    void d(@NonNull String tag, @NonNull String content);

    void d(@NonNull String tag, @NonNull Throwable e, @NonNull String content);

    void i(@NonNull String tag, @NonNull String content);

    void i(@NonNull String tag, @NonNull Throwable e, @NonNull String content);

    void w(@NonNull String tag, @NonNull String content);

    void w(@NonNull String tag, @NonNull Throwable e, @NonNull String content);

    void e(@NonNull String tag, @NonNull String content);

    void e(@NonNull String tag, @NonNull Throwable e, @NonNull String content);


    /**
     * Logger 实现类
     */
    class LoggerImpl implements ILogger{

        @Override
        public void v(@NonNull String tag, @NonNull String content) {
            Log.v(tag, content);
        }

        @Override
        public void v(@NonNull String tag, @NonNull Throwable e, @NonNull String content) {
            Log.v(tag, content, e);
        }

        @Override
        public void d(@NonNull String tag, @NonNull String content) {
            Log.d(tag, content);
        }

        @Override
        public void d(@NonNull String tag, @NonNull Throwable e, @NonNull String content) {
            Log.d(tag, content, e);
        }

        @Override
        public void i(@NonNull String tag, @NonNull String content) {
            Log.i(tag, content);
        }

        @Override
        public void i(@NonNull String tag, @NonNull Throwable e, @NonNull String content) {
            Log.i(tag, content, e);
        }

        @Override
        public void w(@NonNull String tag, @NonNull String content) {
            Log.w(tag, content);
        }

        @Override
        public void w(@NonNull String tag, @NonNull Throwable e, @NonNull String content) {
            Log.w(tag, content, e);
        }

        @Override
        public void e(@NonNull String tag, @NonNull String content) {
            Log.e(tag, content);
        }

        @Override
        public void e(@NonNull String tag, @NonNull Throwable e, @NonNull String content) {
            Log.e(tag, content, e);
        }
    }
}

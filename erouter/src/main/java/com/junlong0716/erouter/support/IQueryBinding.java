package com.junlong0716.erouter.support;

import android.os.Bundle;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
public interface IQueryBinding<T> {
    void bindExtras(T target, Bundle data);
}

package com.junlong0716.erouter.support;

import android.os.Bundle;

public interface IQueryBinding<T> {

    void bindExtras(T target, Bundle data);

}

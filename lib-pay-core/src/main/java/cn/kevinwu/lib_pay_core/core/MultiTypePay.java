package cn.kevinwu.lib_pay_core.core;

import android.app.Application;

import cn.kevinwu.lib_pay_core.paytype.AbsPayType;

public class MultiTypePay {

    public static <T extends AbsPayType> T pay(Application application, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        renewSingleStatus(application);
        return newTclass(clazz);
    }

    private static void renewSingleStatus(Application application) {
        MultiPayContext.getInstance().renewSingleStatus(application);
    }

    private static <T extends AbsPayType> T newTclass(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }
}

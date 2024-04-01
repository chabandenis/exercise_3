package ru.denisch;

import ru.denisch.cache.FunctionForTestHadler;
import ru.denisch.service.FunctionForTest;

import java.lang.reflect.Proxy;

public class Utils {

    public static Object cache(Object obj) {
        Class cls = obj.getClass();
        Object res = Proxy.newProxyInstance(cls.getClassLoader(),
                new Class[]{FunctionForTest.class},
                new FunctionForTestHadler(obj));



        return res;
    }
}

package ru.denisch.service;

import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;
import ru.denisch.cache.Run;

public class FunctionForTestOverride implements FunctionForTest {

    private boolean inCache = false;
    FunctionForTest functionForTest = new FunctionForTestImpl();

    Object cache;

//    public FunctionForTestOverride(FunctionForTest functionForTest) {
//        this.functionForTest = functionForTest;
//    }


    @Override
    @Mutator
    public void setI(int i) {
        System.out.println("    Установили значение в кешируемом классе " + i);
        inCache = false;
        functionForTest.setI(i);
    }

    @Override
    @Run
    @Cache
    public String getI() {
        if (inCache) {
            System.out.println("    из кеша");
            return (String)cache;
        }

        System.out.println("    посчитали");
        cache = functionForTest.getI();
        inCache = true;

        return (String)cache;
    }
}
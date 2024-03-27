package ru.denisch.service;

import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;

public interface FunctionForTest {

    @Mutator
    void setI(int i);


    @Cache
    String getI();
}

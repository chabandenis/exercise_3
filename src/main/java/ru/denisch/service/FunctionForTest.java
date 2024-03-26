package ru.denisch.service;

import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;
import ru.denisch.cache.Run;

public interface FunctionForTest {

    @Mutator
    void setI(int i);


    @Cache
    @Run
    String getI();
}

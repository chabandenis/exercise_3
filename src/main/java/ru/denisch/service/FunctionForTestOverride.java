package ru.denisch.service;

public class FunctionForTestOverride implements FunctionForTest {

    private boolean inCache = false;
    FunctionForTest functionForTest = new FunctionForTestImpl();

    Object cache;

    public FunctionForTestOverride(FunctionForTest functionForTest) {
        this.functionForTest = functionForTest;
    }

    @Override
    public void setI(int i) {
        System.out.println("Установили значение в кешируемом классе " + i);
        inCache = false;
        functionForTest.setI(i);
    }

    @Override
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
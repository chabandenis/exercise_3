package ru.denisch;

import ru.denisch.service.FunctionForTest;
import ru.denisch.service.FunctionForTestImpl;

public class Run {
    public static void main(String[] args) {
        FunctionForTestImpl function = new FunctionForTestImpl();
        FunctionForTest proxyFunction= (FunctionForTest) Utils.cache(function);


        System.out.println("***Установим set  11 ");
        proxyFunction.setI(11);

        System.out.println("***Прочитаем, должен сохранить в кеш ");
        System.out.println("*** i " + proxyFunction.getI());

        System.out.println("***Прочитаем, должен взять из кеша ");
        System.out.println("*** i " + proxyFunction.getI());

        System.out.println("***Установим set  12 ");
        proxyFunction.setI(12);

        System.out.println("***Прочитаем, должен сохранить в кеш ");
        System.out.println("*** i " + proxyFunction.getI());

        System.out.println("***Прочитаем, должен взять из кеша ");
        System.out.println("*** i " + proxyFunction.getI());


        System.out.println("***function " + function);
        System.out.println("***proxyFunction " + proxyFunction);
    }
}

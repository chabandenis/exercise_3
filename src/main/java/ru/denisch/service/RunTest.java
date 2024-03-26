package ru.denisch.service;

import ru.denisch.TestExe;
import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;
import ru.denisch.cache.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class RunTest {

    private int allCntBefore;
    // тестов
    private int allCntTest;
    // всего тестов после
    private int allCntAfter;


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Set<Method> setCache = new HashSet<>();
        Set<Method> setTest = new HashSet<>();
        Set<Method> setMutator = new HashSet<>();

        TestExe testExe = new TestExe();

        Class cl = Class.forName("ru.denisch.service.FunctionForTestImpl");

        Method[] method = cl.getMethods();

        for (Method md : method) {
            if (md.isAnnotationPresent(Cache.class)) {
                setCache.add(md);
            } else if (md.isAnnotationPresent(Test.class)) {
                setTest.add(md);
            } else if (md.isAnnotationPresent(Mutator.class)) {
                setMutator.add(md);
            }
        }


        System.out.println("методов с аннотацией @Cache " + setCache.size());
        System.out.println("методов с аннотацией @Mutator " + setMutator.size());
        System.out.println("методов с аннотацией @Test " + setTest.size());

        for (Method test : setTest) {
            System.out.println("Проверяем метод " + test.getName() + "; " + test.getParameterCount());

            Object object = cl.getDeclaredConstructor().newInstance();

            //testExe.allCntTest++;
            System.out.println("    ru.denisch.cache.Test  " + test);
            try {
                test.invoke(object);
            } catch (Exception e) {
                //testExe.errCntTest++;
            }


        }

        System.out.println("Статистика по запускам");
/*
        System.out.println("Всего тестов до " + testExe.allCntBefore + "; успешно " + (testExe.allCntBefore - testExe.errCntBefore) + "; ошибок " + testExe.errCntBefore);
        // тестов
        System.out.println("Тестов " + testExe.allCntTest + "; успешо " + (testExe.allCntTest - testExe.errCntTest) + "; ошибок " + testExe.errCntTest);
        // всего тестов после
        System.out.println("Всего тестов после " + testExe.allCntAfter + "; успешо " + (testExe.allCntAfter - testExe.errCntAfter) + "; ошибок " + testExe.errCntAfter);

 */

/*
        int i = 1;

        // исходный класс
        FunctionForTest functionForTestImpl = new FunctionForTestImpl();
        functionForTestImpl.setI(i);

        // доработанный класс
        FunctionForTest functionForTestOverride = new FunctionForTestOverride(functionForTestImpl);
        functionForTestOverride.setI(i);

        // результат без кеширования
        String resultWithoutCache = functionForTestOverride.getI();

        // результат с кешированием
        String resultWithCache = functionForTestOverride.getI();

        if (resultWithoutCache.contains(resultWithCache)) {
            System.out.println("первое чтение");
        }

        if (functionForTestOverride.getI().contains(resultWithCache)) {
            System.out.println("второе чтение");
        }

        System.out.println("Установили значение");
        functionForTestOverride.setI(5);

        if (resultWithoutCache.contains(resultWithCache)) {
            System.out.println("первое чтение");
        }

        if (functionForTestOverride.getI().contains(resultWithCache)) {
            System.out.println("второе чтение");
        }
    }

*/
    }
}


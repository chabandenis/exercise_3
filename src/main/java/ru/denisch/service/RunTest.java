package ru.denisch.service;

import ru.denisch.Utils;
import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;
import ru.denisch.cache.Run;

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

        Class cl = Class.forName("ru.denisch.service.FunctionForTestOverride");

        Method[] method = cl.getMethods();

        for (Method md : method) {
            if (md.isAnnotationPresent(Cache.class)) {
                setCache.add(md);
            }
            if (md.isAnnotationPresent(Run.class)) {
                setTest.add(md);
            }
            if (md.isAnnotationPresent(Mutator.class)) {
                setMutator.add(md);
            }
        }

        System.out.println("методов с аннотацией @Cache " + setCache.size());
        System.out.println("методов с аннотацией @Mutator " + setMutator.size());
        System.out.println("методов с аннотацией @Run " + setTest.size());

        for (Method test : setTest) {
            Object object = cl.getDeclaredConstructor().newInstance();

            System.out.println("Проверяем метод первый вызов " + test.getName() + "; " + test.getParameterCount());
            System.out.println("    ru.denisch.cache.Test  " + test);
            try {
                System.out.println("    вернули значение " + test.invoke(object) );
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("Проверяем метод второй вызов " + test.getName() + "; " + test.getParameterCount());
            System.out.println("    ru.denisch.cache.Test  " + test);
            try {
                System.out.println("    вернули значение " + test.invoke(object) );
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("Проверяем метод третий вызов дернули мутатор " + test.getName() + "; " + test.getParameterCount());
            System.out.println("    ru.denisch.cache.Test  " + test);
            try {
                ((FunctionForTest)object).setI(55);
                System.out.println("    вернули значение " + test.invoke(object) );
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("Проверяем метод четвертый вызов " + test.getName() + "; " + test.getParameterCount());
            System.out.println("    ru.denisch.cache.Test  " + test);
            try {
                System.out.println("    вернули значение " + test.invoke(object) );
            } catch (Exception e) {
                System.out.println(e);
            }

        }

    }
}


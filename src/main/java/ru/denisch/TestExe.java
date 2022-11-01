package ru.denisch;// запуск теста


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestExe {
    // всего до
    private int allCntBefore;
    // тестов
    private int allCntTest;
    // всего тестов после
    private int allCntAfter;

    // ошибок до
    private int errCntBefore;
    // ошибок тестов
    private int errCntTest;
    // ошибок после
    private int errCntAfter;


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {

        Set<Method> setBefor = new HashSet<>();
        Set<Method> setTest = new HashSet<>();
        Set<Method> setAfter = new HashSet<>();

        TestExe testExe = new TestExe();

        Class cl = Class.forName(args[0]);

        Method[] method = cl.getMethods();

        for (Method md : method) {
            if (md.isAnnotationPresent(Before.class)) {
                setBefor.add(md);
            } else if (md.isAnnotationPresent(Test.class)) {
                setTest.add(md);
            } else if (md.isAnnotationPresent(After.class)) {
                setAfter.add(md);
            }
        }

        System.out.println("методов с аннотацией @Before " + setBefor.size());
        System.out.println("методов с аннотацией @After " + setAfter.size());
        System.out.println("методов с аннотацией @Test " + setTest.size());

        for (Method test : setTest) {
            System.out.println("Проверяем метод " + test.getName() + "; " + test.getParameterCount());

            Object object = cl.getDeclaredConstructor().newInstance();

            System.out.println("    ru.denisch.Before ");
            for (Method before : setBefor) {
                testExe.allCntBefore++;
                try {
                    before.invoke(object);
                } catch (Exception e) {
                    testExe.errCntBefore++;
                }
            }

            testExe.allCntTest++;
            System.out.println("    ru.denisch.Test  " + test);
            try {
                test.invoke(object);
            } catch (Exception e) {
                testExe.errCntTest++;
            }


            System.out.println("    ru.denisch.After ");
            for (Method after : setAfter) {
                testExe.allCntAfter++;
                try {
                    after.invoke(object);
                } catch (Exception e) {
                    testExe.errCntAfter++;
                }
            }

        }

        System.out.println("Статистика по запускам");

        System.out.println("Всего тестов до " + testExe.allCntBefore + "; успешно " + (testExe.allCntBefore - testExe.errCntBefore) + "; ошибок " + testExe.errCntBefore);
        // тестов
        System.out.println("Тестов " + testExe.allCntTest + "; успешо " + (testExe.allCntTest - testExe.errCntTest) + "; ошибок " + testExe.errCntTest);
        // всего тестов после
        System.out.println("Всего тестов после " + testExe.allCntAfter + "; успешо " + (testExe.allCntAfter - testExe.errCntAfter) + "; ошибок " + testExe.errCntAfter);

    }
}

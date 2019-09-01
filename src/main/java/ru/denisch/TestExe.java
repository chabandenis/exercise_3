package ru.denisch;// запуск теста


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestExe {
    // всего до
    private static int allCntBefore;
    // тестов
    private static int allCntTest;
    // всего тестов после
    private static int allCntAfter;

    // ошибок до
    private static int errCntBefore;
    // ошибок тестов
    private static int errCntTest;
    // ошибок после
    private static int errCntAfter;


    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class cl = Class.forName(args[0]);
//        System.out.println(cl);

        Method[] method = cl.getMethods();

        Object beforTestObj = cl.newInstance();
        Object testObj = cl.newInstance();
        Object afterTestObj = cl.newInstance();

        // test

        for (Method md : method) {
            if (md.isAnnotationPresent(Before.class)) {
                allCntBefore++;
                System.out.println("ru.denisch.Before " + md);
                try {
                    md.invoke(beforTestObj);
                }
                catch (Exception e){
                    errCntBefore++;
                }
            }
        }

        for (Method md : method) {
            if (md.isAnnotationPresent(Test.class)) {
                allCntTest++;
                System.out.println("ru.denisch.Test " + md);
                try {
                    md.invoke(testObj);
                }
                catch (Exception e){
                    errCntTest++;
                }
            }
        }

        for (Method md : method) {
            if (md.isAnnotationPresent(After.class)) {
                allCntAfter++;
                System.out.println("ru.denisch.After " + md);
                try {
                    md.invoke(afterTestObj);
                }
                catch (Exception e){
                    errCntAfter++;
                }

            }
        }

        System.out.println("Статистика по запускам");

        System.out.println("Всего тестов до " + allCntBefore + "; успешно " + (allCntBefore - errCntBefore) + "; ошибок " + errCntBefore);
        // тестов
        System.out.println("Тестов " + allCntTest + "; успешо " + (allCntTest - errCntTest) + "; ошибок " + errCntTest);
        // всего тестов после
        System.out.println("Всего тестов после " + allCntAfter + "; успешо " + (allCntAfter - errCntAfter) + "; ошибок " + errCntAfter);

    }
}

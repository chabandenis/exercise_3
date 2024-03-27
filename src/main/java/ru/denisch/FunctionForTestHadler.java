package ru.denisch;

import ru.denisch.cache.Cache;
import ru.denisch.cache.Mutator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FunctionForTestHadler implements InvocationHandler {
    private Object obj;

    private Map<String, Object> setCache = new HashMap<>();
    private Map<String, Object> setMutator = new HashMap<>();

    public FunctionForTestHadler(Object obj) {
        System.out.println("Конструктор");
        this.obj = obj;

        for (Method md : obj.getClass().getMethods()) {
            if (md.isAnnotationPresent(Cache.class)) {
                setCache.put(md.getName(), null);
            }
            if (md.isAnnotationPresent(Mutator.class)) {
                setMutator.put(md.getName(), null);
            }
        }

        System.out.println("методов с аннотацией @Cache " + setCache.size());
        System.out.println("методов с аннотацией @Mutator " + setMutator.size());
    }

    /*


        PlayableInvHadler(Object obj) {
            this.obj = obj;
            cashes = new HashMap<>();
            modCounts = new HashMap<>();
            terminators = new HashMap<>();
        }

        private Object cashedVal;
        private Integer modCount=1;
        private HashMap<String, Object> cashes;
        private HashMap<String, Integer> modCounts;

        private class Result
        {
            Object value;
            Integer modCount;
        }

        private HashMap<String,Runnable> terminators;


    */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("    method " + method);

        if (setCache.containsKey(method.getName())
                && setMutator.containsKey(method.getName())) {
            System.out.println("    и кеш и мутатор, просто вызываем метод");
            return method.invoke(obj, args);
        }

        if (setCache.containsKey(method.getName())) {
            System.out.println("    есть аннотация кеширования");
            if (setCache.get(method.getName()) == null) {
                setCache.put(method.getName(), method.invoke(obj, args));
                System.out.println("    посчитали, в кеш " + setCache.get(method.getName()));
            } else {
                System.out.println("    Прочитали из кеша" + setCache.get(method.getName()));
                return setCache.get(method.getName());
            }

            return setCache.get(method.getName());
        } else {
            if (setMutator.containsKey(method.getName())) {
                System.out.println("    мутатор");
                // сбросить кеш
                for (String key: setCache.keySet()){
                    setCache.put(key, null);
                }
            } else {
                System.out.println("    Не мутатор");
            }

            System.out.println("    без кеша");
            return method.invoke(obj, args);
        }

/*
        Method m = obj.getClass().getMethod(method.getName(), method.getParameterTypes());
        boolean hasMutatorAnn = false;
        boolean hasCasheAnn = false;

        for (Annotation a: m.getAnnotations())
        {
            if (a.annotationType().equals(Mutator.class)){
                hasMutatorAnn = true;
            };
            if (a.annotationType().equals(Cache.class)){
                hasCasheAnn = true;
            };
        }

        if (hasMutatorAnn) {
            //modCount++;
            modCounts.put(m.getName(), modCounts.containsKey(m.getName())?modCounts.get(m.getName())+1:1);
            System.out.println("Not cashed val from mutator ");
            return method.invoke(obj, args);

        }

        if (hasCasheAnn) {

            //Integer time = m.getAnnotation(Cache.class).time();
            if (!modCounts.containsKey(m.getName()) || modCounts.get(m.getName()) > 0) {
                System.out.println("Not cashed val: ");
                cashedVal = method.invoke(obj, args);
                cashes.put(m.getName(),cashedVal);


                Thread t  = new Thread(()->{
                    System.out.println("timer is running");
                    //Thread.sleep(time);
                    modCounts.put(m.getName(), modCounts.get((m.getName())+1));

                });
                terminators.put(m.getName(), t);
                t.start();



                //modCount=0;
                modCounts.put(m.getName(), 0);
                return cashedVal;
            } else {
                //modCount=0;
                System.out.println("Cashed val: ");
                //return cashedVal;
                return cashes.get(m.getName());
            }

        }



*/
    }
}

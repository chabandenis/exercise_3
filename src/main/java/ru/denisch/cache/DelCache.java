package ru.denisch.cache;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DelCache {
    private static FunctionForTestHadler hadler;

    public static void setHadler(FunctionForTestHadler hadler) {
        DelCache.hadler = hadler;
    }

    public DelCache(FunctionForTestHadler hadler) {
        this.hadler = hadler;
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                System.out.println("##Удаление кеша каждые 7 секунды");
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                List<String> arr = new ArrayList<>();

                for (String s : hadler.getMultiCache().keySet()) {
                    System.out.println("##поток, ключ " + s);

                    long timeAfter = ChronoUnit.SECONDS.between(hadler.getMultiCache().get(s).getTime(), LocalDateTime.now());
                    if (timeAfter > 2) {
                        System.out.println("## время в кеше истекло, удалим " + timeAfter);
                        arr.add(s);

                    }

                }

                for (String s : arr) {
                    System.out.println("Удалили s " + s);
                    hadler.getMultiCache().remove(s);
                }

            }
        }
    }
}

package ru.denisch.cache;

import java.time.LocalDateTime;

// время и значения кеша
public class CacheInfo {
    // значение кеша
    private Object obj;
    // время инициализации кеша
    private LocalDateTime time;

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Object getObj() {
        return obj;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "CacheInfo{" +
                "obj=" + obj +
                ", time=" + time +
                '}';
    }
}

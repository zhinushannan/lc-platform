package io.github.zhinushannan.lcplatformback.lock;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class LockManager {

    private static final Map<String, Lock> LOCK = new HashMap<>();

    static {
        new Thread(() -> LOCK.forEach((s, lock) -> {
            try {
                while (true) {
                    if (!LOCK.isEmpty()) {
                        LocalDateTime now = LocalDateTime.now();
                        if (lock.getExpireTime().isBefore(now)) {
                            unlock(s);
                        }
                        Thread.sleep(500);
                    }
                }
            } catch (InterruptedException ignored) {
            }
        })).start();
    }

    public static boolean lock(String key, Integer expireSeconds) {
        boolean contains = LOCK.containsKey(key);
        if (contains) {
            return false;
        }
        LOCK.put(key, new Lock(expireSeconds));
        return true;
    }

    public static boolean lock(String key) {
        return lock(key, 10);
    }

    public static void unlock(String key) {
        LOCK.remove(key);
    }

}

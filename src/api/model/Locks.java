package api.model;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Locks {
    public static ReadWriteLock outputListLock = new ReentrantReadWriteLock();
    public static ReadWriteLock fileTableLock  = new ReentrantReadWriteLock();
    public static ReadWriteLock heapLock       = new ReentrantReadWriteLock();
}

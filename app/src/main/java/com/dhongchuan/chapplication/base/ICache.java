package com.dhongchuan.chapplication.base;

/**
 * Created by dhongchuan on 15/2/11.
 */
public interface ICache<K, V> {
    public void set(K key, V value);
    public V get(K key);
    public void delete(K key);
    public void deleteAll();
}

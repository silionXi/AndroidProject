package com.silion.simplenet.cache;

/**
 * Created by silion on 2017/8/8.
 */

public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    V remove(K key);
}

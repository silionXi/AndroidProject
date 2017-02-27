package com.silion.androidproject.network.simplenet.cache;

/**
 * Created by silion on 2017/2/27.
 */

public interface Cache<K, V> {
    public V get(K key);

    public void put(K key, V value);

    public void remove(K key);
}

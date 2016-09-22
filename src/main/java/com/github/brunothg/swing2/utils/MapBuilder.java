package com.github.brunothg.swing2.utils;

import java.util.Map;

public class MapBuilder<K, V> {

	private Map<K, V> map;

	public MapBuilder(Map<K, V> map) {
		this.map = map;
	}

	public MapBuilder<K, V> put(K key, V value) {

		map.put(key, value);
		return this;
	}

	public MapBuilder<K, V> put(Map<? extends K, ? extends V> m) {

		map.putAll(m);
		return this;
	}

	public Map<K, V> build() {

		return map;
	}
}

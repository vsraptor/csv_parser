package my;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class TwoWayHashmap<K extends Object, V extends Object> {

	private Map<K,V> forward = new LinkedHashMap<K, V>();
	private Map<V,K> backward = new HashMap<V, K>();

	public Set<K> keys() { return forward.keySet(); }
	public Collection<V> values() { return forward.values(); }
	
	public void add(K key, V value) {
		forward.put(key, value);
		backward.put(value, key);
	}

	public V via_key(K key) {
	    return forward.get(key);
	}

	public K via_val(V key) {
		return backward.get(key);
	}
	
	public String toString() {
		StringBuffer str = new StringBuffer();
		for (K key : forward.keySet()) {
			str.append(key + " => " + forward.get(key) + "\n");
		}
		return str.toString();
	}
}


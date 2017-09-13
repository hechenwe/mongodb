package sooncode.mongodb;

/**
 * 遍历
 * @author hechenwe@gmail.com
 *
 * @param <K>
 * @param <V>
 */
public interface Traversal <K,V> {
	public void each (K key , V value);
}

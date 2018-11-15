import java.util.NoSuchElementException;

/**
 * 
 * @authors Shawn Ge, Alexander Fusco
 *
 * @param <K>
 * @param <V>
 */
public interface HashTableADT<K extends Comparable<K>, V> {

	/**
	 * 
	 * @return
	 */
	public int size();

	/**
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 */
	public void put(K key, V value) throws IllegalArgumentException;

	/**
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException
	 */
	public V get(K key) throws NoSuchElementException;
	
	/**
	 * 
	 * @param key
	 * @throws NoSuchElementException
	 */
	public void remove(K key) throws NoSuchElementException;

}

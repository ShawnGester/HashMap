import java.util.NoSuchElementException;

/**
 * 
 * @authors Shawn Ge, Alexander Fusco
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
		
	// private members
	private Node[] buckArray; // array holding all buckets for hash table
	private int size; // tracks number of populated buckets
	private int numBuckets; // tracks size of array
	private double loadFactor; // helps prevent over capacity of buckArray
	
	/**
	 * Constructor method. Initializes 20 buckets with size of 20. Starts with size of 0.
	 */
	public HashTable() {
		// initialize load factor
		this.loadFactor = 0.75;
		// initialize bucket array
		this.buckArray = new Node[11];
		// initialize to 20 if not specified
		this.numBuckets = 11;
		// initialize empty size
		this.size = 0;
	}
  
	/**
	 * Constructor method. Initializes the number of buckets to the initialCapacity argument, and
	 * makes the size of each bucket the same. Will resize array when size/capacity > loadFactor.
	 * @param initialCapacity - int for the number of buckets for the table to start with.
	 * @param loadFactor - double for that the the fraction of used buckets should stay under.
	 */
	public HashTable(int initialCapacity, double loadFactor) {
		
		// initialize bucket array
		buckArray = new Node[initialCapacity];
		this.numBuckets = initialCapacity;
		this.loadFactor = loadFactor;
		// initialize empty size
		this.size = 0;
		//FIXME: Add exception handling for when loadFactor > 1.
	}

	/** 
	 * Gets hashcode for the key that will determine the bucket for the node.
	 * @param key - K instance used to determine hash code.
	 * @return int for hash code.
	 */
	private int getBucket(K key) {
		// generate hash code from java API
		int hashCode = key.hashCode();
		// standardize hashCode with current table capacity to index value
		int toRet = hashCode % numBuckets;
		return toRet;
	}
	
	/**
	 * When the size > capacity * loadFactor, this method should be called to rehash everything
	 * with a larger capacity, and larger buckets.
	 */
	private void rehash() {
		// CHECKING SIZE VS. NUMBUCKETS
		if (this.size > this.numBuckets * this.loadFactor) {
			// double array size if hash table becoming full
			this.numBuckets = this.numBuckets * 2 + 1; // FIXME: times 2 plus 1 on piazza??
			// temporarily keep copy of buckArray
			Node[] temp = buckArray;
			// create new bucket array with bigger table size
			buckArray = new Node[numBuckets];
			
			// put all elements back into bigger hash table
			for (int i = 0; i < temp.length; ++i) {
				// if bucket not empty at this index
				if (temp[i] != null) {
					Node<K,V> head = temp[i];
					// add all links in chain
					while(head != null) {
						// put into buckArray
						put(head.getKey(), head.getValue());
						head = head.getNextNode();
					}
				}
			}
		}
	}

	/**
	 * insert a <key,value> pair entry into the hash table 
	 * if the key already exists in the table, 
	 * replace existing value for that key with the 
	 * value specified in this call to put.
	 * 
	 * permits null values but not null keys and permits the same value 
	 * to be paired with different key
	 * 
	 * @throws IllegalArgumentException when key is null
	 */
	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		// calculate index of bucket on hash table
		int indexToFind = getBucket(key);
		// set head to node at beginning of chain
		Node<K, V> head = buckArray[indexToFind];
		if(head == null) {
			buckArray[indexToFind] = new Node(key, value);
			return;
		}
		// INSERTING
		// search through chain for pre-existing link in chain
		while (head.getNextNode() != null) {
			// if current link in chain is matching
			if (head.getNextNode().getKey().equals(key)) {
				// set the value of current node to new value
				head.getNextNode().setValue(value);
				return;
			} else {
				// keep checking other links in chain
				head = head.getNextNode();
			}
		}
		Node<K,V> toAdd = new Node<K,V>(key, value);
		head.setNext(toAdd);
		// insert key into chain of current bucket
		++size;
		rehash();
	}

	/**
	 * @return the value associated with the given key.
	 * @throws IllegalArgumentException if key is null 
	 * @throws NoSuchElementException if key does not exist 
	 */
	@Override
	public V get(K key) throws NoSuchElementException {
		
		if(key == null) {
			throw new IllegalArgumentException();
		}
		// find bucket index of node
		int indexToFind = getBucket(key);
		// check through chaining
		Node<K, V> head = buckArray[indexToFind];
		
		if (head == null) {
			throw new NoSuchElementException();
		}
		
		// check if not at end of chain
		while (head != null) {
			// matching node found
			if (head.getKey().equals(key)) {
				return head.getValue();
				// matching node not found, move next link in chain
			} else {
				head = head.getNextNode();
			}
		}
		throw new NoSuchElementException();
	}
	
	@Override
	public void remove(K key) throws NoSuchElementException {
		// calculate index of bucket on hash table
		int indexToFind = getBucket(key);
		// default null, testing for if node with key exists
		Node<K, V> head = this.buckArray[indexToFind];
		
		if(head == null) {
			throw new NoSuchElementException();
		}
		
		// Element is found at beginning of list.
		if (head.getKey().equals(key)) {
			--size;
			head.setNext(head.getNextNode());
			return;
		}
		
		Node<K, V> leaf = head.getNextNode();
		
		// see if node to remove actually exists in bucket
		while (leaf != null) {
			// node to remove found
			if (leaf.getKey().equals(key)) {
				break;
			} else {
				// else move on to next node
				head = leaf;
				leaf = leaf.getNextNode();
			}
		}
		// if child never found non-null node
		if(leaf == null) {
			throw new NoSuchElementException();
		// remove the node
		} else {
			--size;
			head.setNext(leaf.getNextNode());
			//FIXME: Possible exception handling for when child.nextNode() returns null
		}
		
		
		
	}

	/** @return  the number of keys in the hash table */
	@Override
	public int size() {
		return this.size;
	}

	/** Sub-class representing the individual nodes that get inserted into bucket array
	 * 
	 * @author Shawn Ge and Alexander Fusco
	 *
	 * @param <K>
	 * @param <V>
	 */
	class Node<K extends Comparable<K>, V> {
		private K key;
		private V value;
		private Node<K, V> nextNode;
		
		/**
		 * Constructor method.
		 * @param key for hashing list.
		 * @param value to be stored.
		 */
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter method.
		 * @return next node in list.
		 */
		public Node<K, V> getNextNode() {
			return this.nextNode;
		}
		
		/**
		 * Getter method.
		 * @return key of given node.
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Getter method.
		 * @return value of given node.
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Setter method.
		 * @param nextNode
		 */
		public void setNext(Node nextNode) {
			this.nextNode = nextNode;
		}
		
		/**
		 * Setter method.
		 * @param value
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
}

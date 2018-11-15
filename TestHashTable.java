import java.util.NoSuchElementException; // expect to need
import static org.junit.Assert.*; 
import org.junit.Before;  // setUp method
import org.junit.After;   // tearDown method
import org.junit.Test;   

/**
 * 
 * @author Shawn Ge, Alexander Fusco
 *
 */
public class TestHashTable{

	// Allows us to create a new hash table before each test
	static HashTableADT<Integer, Object> ht;
	
	@Before
	public void setUp() throws Exception {
		ht = new HashTable<Integer, Object>();  
	}

	@After
	public void tearDown() throws Exception {
		ht = null;
	}
		
	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Tests that a HashTable is empty upon initialization
	 */
	@Test
	public void test000_IsEmpty() {
		assertEquals("size with 0 entries:", 0, ht.size());
	}
	
	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Tests that a HashTable is not empty after adding one (K, V) pair
	 */
	@Test
	public void test001_IsNotEmpty() {
		ht.put(1,"0001");
		int expected = 1;
		int actual = ht.size();
		assertEquals("size with one entry:",expected,actual);
	}
	
	/**
	 * Tests that a single element can be added to the hash table
	 */
	@Test
	public void test002_InsertOne() {
		try {
			String testValue = "chocolate";
			Integer testK = new Integer(1);
			ht.put(testK, testValue);
			assertTrue(ht.get(testK).equals(testValue));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** IMPLEMENTED AS EXAMPLE FOR YOU
	 * Other tests assume <int,Object> pairs,
	 * this test checks that <Long,Object> pair also works.
	 */
	@Test
	public void test010_Long_Object() {
		Long key = 9876543210L;
		Object expected = "" + key;		
		HashTableADT<Long,Object> table = 
				new HashTable<Long,Object>();
		table.put(key, expected);
		Object actual = table.get(key);
		assertTrue("put-get of (Long,Object) pair",
				expected.equals(actual));
	}
	
	/*
	 * Tests that the value for a key is updated 
	 * when tried to insert again.
	 */
	@Test
	public void test011_Update() {
		// TODO: implement this test
		fail("not implemented yet");
	}
	
	/*
	 * Tests that inserting many and removing one entry
	 * from the hash table works
	 */
	@Test(timeout=1000 * 10)
	public void test100_InsertManyRemoveOne() {
		String testValue = "chocolate";
		Integer test1 = new Integer(1);
		int expected = 99;
		int actual = ht.size();
		
		ht.put(test1, "chocolate");
		for(int i = 0; i < 100; ++i) {
			Integer testN = new Integer(i);
			ht.put(testN, testValue);
		}
		ht.remove(test1);
		assertEquals(null, ht.get(test1));
		assertEquals("size with 99 entries:",expected,actual);
	}
	
	/*
	 * Tests ability to insert many entries and 
	 * and remove many entries from the hash table
	 */
	@Test(timeout=1000 * 10)
	public void test110_InsertRemoveMany() {
		// TODO: implement this test
		fail("not implemented yet");
	}
}

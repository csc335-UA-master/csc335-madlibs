package alex;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Map implemented with arrays.
 * 
 * This class is responsible for making a generic map
 * between keys and values.  This map is implemented using
 * two arrays: one for keys and one for values, and can be
 * of any size as the arrays are resized if more storage is needed.
 * All of the standard operations done on a map can be done here.
 * 
 * @author Gavin Magee
 * @version 1.0
 * 
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
public class ArrayMap<K, V> extends AbstractMap<K,V> {
	
	private Object[] keys = new Object[10];
	private Object[] values = new Object[10];
	
	private Set<Entry<K, V>> entrySet = new ArrayMapEntrySet();
	
	private int length = 0;
	
	
	/**
	 * This method is used to place a key value entry into the map.
	 * 
	 * This method searches through the map looking for a duplicate
	 * key.  If it finds a duplicate key, the old keys value
	 * is replaced with the new keys value and returned. If we run
	 * out of storage, we double the storage space in our arrays
	 * through a call to addStorage.
	 * 
	 * @param key The unique key that will be used to access the value.
	 * @param value The value associated with key.
	 * 
	 * @return The replaced value if a duplicate key was inserted,
	 * and null if the new key inserted was unique.
	 */
	@Override
	public V put(K key, V value) {
		V retVal = null;
		int i = 0;
		boolean dupKeyFound = false;
		//look for dup key
		while (i < length && !dupKeyFound) {
			if (key.equals(keys[i])) {
				retVal = (V) values[i];
				values[i] = value;
				dupKeyFound = true;
			}
			i++;
		}
		if (!dupKeyFound) {
			if (length == keys.length) {
				addStorage();
			}
			keys[i] = key;
			values[i] = value;
			length++;
		}	
		return retVal;
	}
	
	/**
	 * This method deletes index i in the array arr.
	 * 
	 * The method shifts everything on the right one
	 * to the left starting at i and going to the end of the
	 * array.  The last element of this newly shifted array
	 * will always be null because there is nothing to
	 * the right of the last element.
	 * 
	 * @param i The index of the array that we want gone.
	 * 
	 * @param arr The array housing index i that will remove at
	 * index i and shift the array to maintain contiguity.
	 */
	private static void eliminateAndShift(int i, Object[] arr) {
		while (i < arr.length - 1) {
			arr[i] = arr[i + 1];
			i++;
		}
		//for last element, nothing to right so make it null
		arr[i] = null;
	}
	
	/**
	 * This method increases the array sizes of keys
	 * and values for ArrayMap.
	 * 
	 * The method is called whenever we run out of space and
	 * need to put new entries into the map. It doubles the
	 * previous amount of space that the arrays used and copies
	 * all of the old array items over to the new one.
	 */
	private void addStorage() {
		Object[] newKeys = new Object[keys.length * 2];
		Object[] newVals = new Object[keys.length * 2];
		for (int i = 0; i < keys.length; i++) {
			newKeys[i] = keys[i];
			newVals[i] = values[i];
		}
		keys = newKeys;
		values = newVals;
	}
	
	/**
	 * This method returns the maps size.
	 * 
	 * The method is a getter for the length field
	 * of the map.
	 * 
	 * @return An integer of the amount of entries in the map.
	 */
	@Override
	public int size() {
		return length;
	}
	
	/**
	 * This method returns an entrySet of all map entries.
	 * 
	 * This method returns a Set object holding mapping Entry objects.
	 * 
	 * @return entrySet is an internal class representation of the
	 * entries of the map.
	 */
	@Override
	public Set<Entry<K, V>> entrySet(){
		return entrySet;
	}
	
	/**
	 * This class represents a set of all the entries
	 * in the ArrayMap, with each element being an Entry<K,V>
	 * holding the key and value for that entry.  The concrete
	 * iterator for this set is also provided as an
	 * inner class of the ArrayMapEntrySet.  This class should
	 * never exist outside of the ArrayMap object, thus it is
	 * an inner class.
	 * 
	 * @author Gavin Magee
	 * @version 1.0
	 */
	private class ArrayMapEntrySet extends AbstractSet<Entry<K,V>>{
		/**
		 * This method is a getter for entrySet size.
		 * 
		 * This method returns the size of the entrySet
		 * by returning the length of the map, which will
		 * be the same size.
		 * 
		 * @return An integer representation of the length
		 * of the set.
		 */
		@Override
		public int size() {
			return length;
		}
		
		/**
		 * This method returns a boolean representation of
		 * whether or not the entrySet contains the specified
		 * object.
		 * 
		 * This method first looks to see if the Object o is
		 * of the type Entry.  If it is, then it looks across
		 * all of the entries to find a match (returning false
		 * if no match is found).  If it isn't of type entry, false is returned.
		 * 
		 * @param o The object being searched for in entrySet
		 * 
		 * @return A boolean of whether or not the Object o is in the set.
		 */
		@Override
		public boolean contains(Object o) {
			if (o instanceof Entry) {
				Entry potentialEntry = (Entry) o;
				for (int i = 0; i < length; i++) {
					if (potentialEntry.getKey().equals(keys[i]) &&
							potentialEntry.getValue().equals(values[i])) {
						return true;
					}
				}
			}
			return false;
		}
		
		/**
		 * This method returns an iterator of the set.
		 * 
		 * The method returns an internal EntrySet class
		 * iterator that knows how to traverse the
		 * set of entries.
		 * 
		 * @return Iterator of the entrySet.
		 */
		@Override
		public Iterator<Entry<K,V>> iterator(){
			return new ArrayMapEntrySetIterator<Entry<K,V>>();
		}
		
		/**
		 * This class is the Iterator for the set of entries of the
		 * ArrayMap class. It traverses the set of entries by
		 * walking a pointer down the arrays of keys and values.
		 * When a removal is made, all of the elements are shifted down
		 * so the array maintains contiguity in memory.  A boolean field
		 * is also used to make sure that removal only occurs once
		 * after a next() at most.
		 * 
		 * @author Gavin Magee
		 * @version 1.0
		 * 
		 * @param <T> The type of each set element being iterated over.
		 */
		private class ArrayMapEntrySetIterator<T> implements Iterator<T>{
			private int curr = 0;
			private boolean canRemove = false;
			
			/**
			 * This method returns whether or not a next element exists in
			 * the EntrySet in which to get.
			 * 
			 * This method uses the curr and length of the entrySet and map
			 * to determine whether or not a next element exists.  If curr
			 * (the index of the next element that will be retrieved by a
			 * call to next) is smaller than the length, it is true that
			 * there is a next element so true is returned.  Otherwise,
			 * false is returned because we have no more elements.
			 * 
			 * @return A boolean of whether or not we have a next element
			 */
			@Override
			public boolean hasNext() {
				return curr < length;
			}
			
			/**
			 * This method returns the next element in the set.
			 * 
			 * This method returns the next element by making a new
			 * Entry with the key and value at the curr index (which stores
			 * where we are in the set iteration).  This new entry is
			 * returned but not before incrementing the curr position
			 * and setting canRemove to true.
			 * 
			 * @return The next Entry object of the set.
			 * 
			 * @throws NoSuchElementException when there is no other element
			 * to get but next is called.
			 */
			@Override
			public T next() throws NoSuchElementException{
				if (curr >= length) {
					throw new NoSuchElementException("No next available.");
				}
				T currEntry = (T) new SimpleEntry(keys[curr], values[curr]);
				//change curr so next gets something new next time its called.
				curr++;
				canRemove = true;
				return currEntry;
			}
			
			/**
			 * This method removes the last element retrieved by next().
			 * 
			 * This method can only be called once after each call to next,
			 * anything else will result in an exception being thrown. A call
			 * to this method results in the removal of the last element
			 * returned by next, with the underlying array holding
			 * this data having its contents shifted after removal
			 * to ensure contiguity of the array. This method also
			 * decrements curr after removal to ensure that each
			 * element is visited once (the element after the removed element
			 * would be skipped if we did not do this).
			 * 
			 * @throws IllegalStateException when remove is illegal to perform
			 * due to next not being called first, or remove already being called
			 * once on that next.
			 */
			@Override 
			public void remove() throws IllegalStateException {
				if (canRemove) {
					//remove curr - 1 in both keys and values
					eliminateAndShift(curr - 1, keys);
					eliminateAndShift(curr - 1, values);
					curr--;
					length--;
				}else {
					throw new IllegalStateException("Can only remove once after each next call.");
				}
				canRemove = false;
			}
		}
	}
}
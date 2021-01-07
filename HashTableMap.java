// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * @author Michael Brudos
 *
 * @param <KeyType>   The key associated with our Map implementation
 * @param <ValueType> The Value associated with our Map implementation
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  // Put new private array list here
  // private KeyValuePair<KeyType, ValueType>[] map;
  private LinkedList<KeyValuePair<KeyType, ValueType>>[] map;
  private int capacity;
  private int size;

  /**
   * Constructs a new HashtableMap object
   * 
   * @param capacity - the capacity of the map to be created
   */
  public HashTableMap(int capacity) {
    this.capacity = capacity;
    map = new LinkedList[capacity]; // ignore the yellow warning here
  }

  /**
   * Constructs a new default HashtableMap object
   */
  public HashTableMap() {
    capacity = 10;
    map = new LinkedList[capacity];
  }

  /**
   * @param key   - the key to be input to the hash function
   * @param value - the value associated with the provided key Places a new entry into the hash
   *              table that maps key to value
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    if (size + 1 >= capacity * 0.8) {
      this.growArray();
    }
    // Lets create a KVP to represent out key target
    KeyValuePair<KeyType, ValueType> target = new KeyValuePair<KeyType, ValueType>(key, value);
    // now lets find where this potential target should go
    int hash = Math.abs(key.hashCode()) % capacity;

    // Lets see if we have the same key in the list
    if (this.containsKey(key)) {
      return false;
    }
    // Ok so we don't have the same key here... is there anything here?
    if (map[hash] == null) {
      // Empty? Lets just put it here then....
      LinkedList<KeyValuePair<KeyType, ValueType>> chain = new LinkedList<>();
      map[hash] = chain;
      chain.add(target);
      size++;
      return true;
    }
    // Well, there certainly is something here... lets add!
    LinkedList<KeyValuePair<KeyType, ValueType>> chain = map[hash];
    chain.add(target);
    size++;
    return true;
  }

  /**
   * @param key - the key to be input to the hash function
   * @throws NoSuchElementException e - if the key is not located within the hash table Returns the
   *                                value from a hashed key
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // Check to see if the value we want is actually in the map somewhere
    if (!this.containsKey(key)) {
      NoSuchElementException e = new NoSuchElementException();
      throw e;
    }
    // Ok so now it must have the key... time to find it
    // Create a target KVP
    KeyValuePair<KeyType, ValueType> target = new KeyValuePair<KeyType, ValueType>(key, null);
    int hash = Math.abs(key.hashCode()) % capacity;
    // Since containsKey ran and returned true that means this location should have a LinkedList
    LinkedList<KeyValuePair<KeyType, ValueType>> chain = map[hash];
    // Find location of target
    int indexOfTarget = -1;
    for (int i = 0; i < chain.size(); i++) {
      if (chain.get(i).equals(target)) {
        indexOfTarget = i;
        break;
      }
    }
    // Use location to retrieve value
    return chain.get(indexOfTarget).getValue();


  }

  /**
   * Returns the size of the hash table
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * @param key - the key to be input to the hash function Checks to see if the provided key is
   *            located within the hash table, yes - true, else false
   */
  @Override
  public boolean containsKey(KeyType key) {
    // Find where in the array our KeyValuePair is
    int hash = Math.abs(key.hashCode()) % capacity;
    // Test if that location is empty in which case it is not in the collection
    if (map[hash] == null) {
      return false;
    }
    // Guess what! its certainly in a linked list
    LinkedList<KeyValuePair<KeyType, ValueType>> chain = map[hash];
    // Create a target KVP to compare to those in the linked list
    KeyValuePair<KeyType, ValueType> target = new KeyValuePair<KeyType, ValueType>(key, null);
    // Find how many values we must go through
    int sizeOfList = chain.size();
    // lets start searching through the list
    for (int i = 0; i < sizeOfList; i++) {
      // Check to see if what we found in the list is actually equal to what we are looking for
      if (chain.get(i).equals(target)) {
        return true;
      }
    }
    // List is exhausted, the value is no where to be found
    return false;

  }

  /**
   * @param key - the key to be input to the hash function removes a key and associated pair from
   *            the hash table
   */
  @Override
  public ValueType remove(KeyType key) {
    // Lets create the target -- null is acceptable for the value parameter because equals doesn't
    // use it
    if (!this.containsKey(key)) {
      return null;
    }
    // Target should be in the HashTableMap
    KeyValuePair<KeyType, ValueType> target = new KeyValuePair<KeyType, ValueType>(key, null);
    int hash = Math.abs(key.hashCode()) % capacity;
    // Since containsKey ran and returned true that means this location should have a LinkedList
    LinkedList<KeyValuePair<KeyType, ValueType>> chain = map[hash];
    // Find where it is in the LinkedList
    int toRemove = -1;
    for (int i = 0; i < chain.size(); i++) {
      if (chain.get(i).equals(target)) {
        toRemove = i;
        break;
      }
    }
    // Save the value of target
    ValueType removedValue = chain.get(toRemove).getValue();
    // Remove it from the list
    chain.remove(toRemove);
    // Decrement size
    size--;
    // Return the value required
    return removedValue;
  }

  /**
   * removes all entries from the hash table
   */
  @Override
  public void clear() {
    // TODO Auto-generated method stub
    map = new LinkedList[capacity]; // ignore the yellow warning here
    size = 0;
  }

  /**
   * Used to grow the hash table if ever filled beyond 80% of capacity
   */
  private void growArray() {
    // Make the new array
    LinkedList<KeyValuePair<KeyType, ValueType>>[] newMap = new LinkedList[capacity * 2];
    int newSize = 0;
    // Run through the old array
    for (int i = 0; i < capacity; i++) {
      // if theres something at this index, run through it!
      if (map[i] != null) {
        LinkedList<KeyValuePair<KeyType, ValueType>> chain = map[i];
        for (int j = 0; j < chain.size(); j++) {
          KeyValuePair<KeyType, ValueType> toAdd = map[i].get(j);
          KeyType key = toAdd.getKey();
          // now lets find where this potential target should go
          int hash = Math.abs(key.hashCode()) % (capacity * 2);

          // Ok so we don't have the same key here... is there anything here?
          if (newMap[hash] == null) {
            // Empty? Lets just put it here then....
            LinkedList<KeyValuePair<KeyType, ValueType>> innerChain = new LinkedList<>();
            newMap[hash] = innerChain;
            innerChain.add(toAdd); 
            newSize++;

          } else {
            // Well, there certainly is something here... lets add!
            LinkedList<KeyValuePair<KeyType, ValueType>> innerChain = newMap[hash];
            innerChain.add(toAdd);
            newSize++;
          }
        }
      }
    }
    // Copy over entries
    size = newSize;
    map = newMap;
    capacity = capacity * 2;
  }
  
//  public static void main(String[] args) {
//    HashTableMap<String,String> map = new HashTableMap<>(3);
//    String[] names = {"Michael", "Abbey", "Joseph", "Tyler"};
//    map.put("Michael", "Brudos");
//    map.put("Abbey", "Meyer");
//    map.put("Tyler", "S");
//    map.put("Joseph", "Ritter");
//  //  System.out.println(Math.abs(names[0].hashCode()) % (map.capacity * 2));
//    
//    for(int i = 0; i < names.length; i++) {
//      System.out.println(map.get(names[i]));
//    }
//  }
}

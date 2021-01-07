// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;

/**
 * @author Michael Brudos
 *  Implements a HashTable / Linked List combo to track a list of items associated with a person of interest
 */
public class GiftHashTable {


private HashTableMap<String,Gift> GiftList; // HashTable to store name, Gift object values
private LinkedList<String> names; // List of all keys (names) of gifts stored in the hash table
private double totalCost; // running total of all gifts 
private int totalGifts; // total # of gifts in the list
  /** Creates a new GiftHashTable 
   * @param capacity - Capacity of hashtable 
   */
  public GiftHashTable(int capacity) {
    GiftList = new HashTableMap<>(capacity);
    names = new LinkedList<>();
    totalCost = 0;
    totalGifts = 0;
  }
  
  /**
   *  Creates a new GiftHashTable
   */
  public GiftHashTable() {
    GiftList = new HashTableMap<>(20);
    names = new LinkedList<>();
    totalCost = 0;
    totalGifts = 0;
  }
  
  
  /** Adds a gift to the hashtable and linkedlist only if no other gift with this name is in the table
   * @param gift - gift to add
   * @return true if added successful, false otherwise.
   */
  public boolean addGift(Gift gift) {
    boolean added = GiftList.put(gift.getName(), gift); // Can the gift be put in the map?
    if(added) {
      //If yes, track a few values
      double cost = gift.getCost();
      String name = gift.getName();
      // Update counters, including linked list
      this.totalCost += cost;
      this.totalGifts++;
      this.names.add(name);
    }
    return added;
  }
  
  public boolean removeGift(Gift gift) {
    Gift remove = GiftList.remove(gift.getName()); // Can this gift be remove from the map?
    if(remove != null) {
      //if so... record some values
      double cost = gift.getCost();
      String name = gift.getName();
      // update counters and return
      this.totalCost -= cost;
      this.totalGifts--;
      this.names.remove(name);
      return true;
    }
    else {
      //Gift was likely not found
      return false;
    }
  }
  
  public boolean removeGift(String key) {
    Gift remove = GiftList.remove(key); // Can this gift be remove from the map?
    if(remove != null) {
      //if so... record some values
      double cost = remove.getCost();
      String name = remove.getName();
      // update counters and return
      this.totalCost -= cost;
      this.totalGifts--;
      this.names.remove(name);
      return true;
    }
    else {
      //Gift was likely not found
      return false;
    }
  }
  
  /** Checks hash table for a gift with the same name
   * @param gift Gift to check hash table for
   * @return true if gift is found, false otherwise
   */
  public boolean containsGift(Gift gift) {
    return GiftList.containsKey(gift.getName());
  }
  
  /** Gets a gift from the hash table
   * @param gift - gift to find
   * @throws NoSuchElementException - if gift is not in the hash table
   * @return Gift object matching the name of the provided gift
   */
  public Gift getGift(Gift gift) {
    if(containsGift(gift)) {
      return GiftList.get(gift.getName());
    }
    else {
      throw new java.util.NoSuchElementException("A gift with this name could not be found");
    }
  }
  
  /** Gets a gift from the hash table. 
   * @param name
   * @throws NoSuchElementException - if there is no gift that matches the name within the hash table
   * @return Gift that matches the name within the hash table
   * 
   */
  public Gift getGift(String name) {
    return GiftList.get(name);
  }
  
  /**
   * Clears the hash table and resets counters and linked list 
   */
  public void clear() {
    GiftList.clear();
    names = new LinkedList<>();
    totalGifts = 0;
    totalCost = 0;
  }
  
  /**
   * Creates a String representation of the GiftHashTable
   * @return result - the String that has been processed using elements of the GiftHashTable
   */
  public String toString() {
    String result = "";
    //Check for empty list
    if(names.getFirst() == null) {
      return "The Gift List is empty";
    }
    //Iterates through each type of key in the list
    for(int i = 0; i < names.size(); i++) {
      String item = names.get(i);
      //Retrieve item
      Gift toFind = this.getGift(item);
      //Format output line by pulling values from above gift
      result += item + "@ " + toFind.getStore() + "  Price: " + toFind.getCost() + "\n";
    }
    //Final line, concluding the String output
    result += "Total: $" + totalCost +" for " + totalGifts + " gifts!";
    return result;
  }

  /** Getter method for this.names
   * @return returns a LinkedList of Strings associates with the names of gifts in the table
   */
  public LinkedList<String> getNames() {
    return names;
  }

  /**Getter method for this.getTotalCost
   * @return Double: cost of all gifts in the hashTable
   */
  public double getTotalCost() {
    return totalCost;
  }

  /** Getter method for this.getTotalGifts
   * @return int: totalGifts - number of gifts in the table
   */
  public int getTotalGifts() {
    return totalGifts;
  }
  
  /** Checks if the table is empty
   * @return true if the hash table is empty, false otherwise
   */
  public boolean isEmpty() {
    return totalGifts == 0;
  }
  
  
  
}

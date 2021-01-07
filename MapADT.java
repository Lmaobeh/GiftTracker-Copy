// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import java.util.NoSuchElementException;

/**
 * 
 *
 * @param <KeyType>   The key associated with our Map implementation
 * @param <ValueType> The Value associated with our Map implementation MapADT provides the interface
 *                    to be overloaded by the HashTableMap
 */
public interface MapADT<KeyType, ValueType> {
  public boolean put(KeyType key, ValueType value);

  public ValueType get(KeyType key) throws NoSuchElementException;

  public int size();

  public boolean containsKey(KeyType key);

  public ValueType remove(KeyType key);

  public void clear();
}

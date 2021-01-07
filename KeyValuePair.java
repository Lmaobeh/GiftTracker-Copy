// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


/**
 * @author Michael Brudos
 *
 * @param <KeyType>   The key associated with our Map implementation
 * @param <ValueType> The Value associated with our Map implementation Provides an object that can
 *                    be used to store two generic data types
 */
public class KeyValuePair<KeyType, ValueType> {
  private KeyType key;
  private ValueType value;

  /**
   * @param key   - The key associated with our KeyValuePair
   * @param value - The value associated with our KeyValuePair Constructs a new KeyValuePair
   */
  public KeyValuePair(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
  }


  /**
   * @return the key of the KVP getter method
   */
  public KeyType getKey() {
    return key;
  }

  /**
   * @return the value of the KVP getter method
   */
  public ValueType getValue() {
    return value;
  }

  /**
   * @return true if the keys of two KVPs are the same, false otherwise Checks if two KVPs are the
   *         same and returns a boolean
   */
  public boolean equals(KeyValuePair<KeyType, ValueType> pair) {
    return this.getKey().equals(pair.getKey());
  }


}

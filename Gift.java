// --== CS400 File Header Information ==--
// Name: Qosai
// Email: kadadha@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Florian
// Notes to Grader: <optional extra notes>

 
public class Gift {
  private String name; //Name of the gift
  private double cost; // Cost of the gift
  private String store; // Store a particular gift can be found at 
  
  /** Creates a new Gift object 
   * @param name - name of object
   * @param cost - of object
   * @param number - of object
   */
  public Gift(String name, double cost, String store) {
    this.name = name;
    this.cost = cost;
    this.store = store;
  }
  
  /** Getter method for name 
   * @return name of the object
   */
  public String getName() {
    return name;
  }
  
  /** Getter method for cost
   * @return cost of the object
   */
  public double getCost() {
    return cost;
  }
  
  /** Getter method for store
   * @return store of the object
   */
  public String getStore() {
    return store;
  }
  
  
  /**
   * @param toCheck - Gift to be compared too
   * @return true if gifts are the same, false otherwise
   */
  public boolean equals(Gift toCheck) {
    return this.name.contentEquals(toCheck.name);
  }
  /**Puts all parts of gift object into string 
   * @return String object that contains name, cost, and store separated by commas 
   */
  public String toString(){
    String gift = this.name + ", $" + this.cost + ", from: " + this.store;
    return gift;
  }
}
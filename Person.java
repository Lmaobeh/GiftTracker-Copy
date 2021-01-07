
// --== CS400 File Header Information ==--
// Name: Qosai Kadadha
// Email: kadadha@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
public class Person implements Comparable<Person>{
  public String name;//name of person
  protected boolean isVisible;
  private GiftHashTable giftList;//hashtable that stores all gifts for a particular person
  
  
  public Person(String name) {
    this.name = name;
    isVisible = true;
    giftList = new GiftHashTable();
  }
  public Person(String name, int capacity) {
    this.name = name;
    isVisible = true;
    giftList = new GiftHashTable(capacity);
  }
  @Override
  public int compareTo(Person p) {
    return this.name.compareTo(p.name);
  }

  public String toString() {
    return name;
  }
  
  public GiftHashTable getGiftList() {
    return giftList;
  }
  public double getTotalCost() {
    return giftList.getTotalCost();
  }
  public boolean add(Gift gift) {
    if(this.giftList.addGift(gift)) {
      return true;
    }
    return false;
  }
  public boolean removeGift(Gift gift) {
    if(giftList.removeGift(gift)) {
      return true;
    }
    return false;
  }
  public String giftsToString() {
    String gifts = giftList.toString();
    return gifts;
  }
}

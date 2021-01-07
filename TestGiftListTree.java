// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Michael Brudos
 * This classes proper functionality of the Christmas Gift RBT
 */
class TestGiftListTree {

  /**
   * Tests that toString is updated properly
   */
  @Test
  void testToString() {
    GiftListTree tree = new GiftListTree();
    String list = tree.toString();
    if(!list.equals("")) {
      fail("The list should have reported having 0 people" + " " + list);
    }
    tree.insertPerson(new Person("Michael"));
    list = tree.toString();
    if(!(list.contains("1") && list.contains("Michael"))) {
      fail("The list should have reported having 1 person and Michael but instead:" + " " + list);
    }
    tree.insertPerson(new Person("Abby"));
    list = tree.toString();
    if(!(list.contains("2") && list.contains("Abby"))) {
      fail("The list should have reported having 2 person and Abby but instead:" + " " + list);
    }
    tree.insertPerson(new Person("Zach"));
    list = tree.toString();
    if(!(list.contains("3") && list.contains("Zach"))) {
      fail("The list should have reported having 3 person and Zach but instead:" + " " + list);
    }
    tree.insertPerson(new Person("Yahtzee"));
    list = tree.toString();
    if(!(list.contains("4") && list.contains("Yahtzee"))) {
      fail("The list should have reported having 4 person and Yahtzee but instead:" + " " + list);
    }
  }
  
  /**
   * Tests the ability to insert, check nodes with contains and lookup, modify a node, and remove a node
   */
  @Test
  void testInsert() {
    GiftListTree tree = new GiftListTree();
    //Test insert
    tree.insertPerson(new Person("Michael"));
    if(!tree.containsPerson(new Person("Michael"))) {
      fail("Michael is in the tree");
    }
    //Test for person not in the tree
    if(tree.containsPerson(new Person("Abbey"))) {
      fail("Abbey is not in the tree");
    }
    //Test now that she is in the tree
    tree.insertPerson(new Person("Abbey"));
    if(!tree.containsPerson(new Person("Abbey"))) {
      fail("Abbey is not in the tree");
    }
    Person abbey = tree.lookUpPerson("Abbey");
    abbey.isVisible = false;
    Person modified = tree.lookUpPerson("Abbey");
    //Changes saved
    if(modified.isVisible) {
      fail("Abbey should be invisible");
    }
    tree.RemovePerson("Michael");
    String list = tree.toString();
    if(list.contains("Michael") || list.contains("Abbey")) {
      fail("Michael and Abbey should not be visible in the list");
    }
  }
  
  /**
   * Check that the tree can properly calculate totals
   */
  @Test
  void testTotals() {
    // Create people, Michael adds 1 item and 1.0
    //                Abbey adds 1 item and 1.0
    //                Gary adds 0 items and 0.0
    Person michael = new Person("Michael");
    Person abbey = new Person("Abbey");
    GiftHashTable table1 = abbey.getGiftList();
    //Populate Abbey's GiftHashTable
    table1.addGift(new Gift("Stick", 1.0, "Target"));
    Person gary = new Person("Gary");
    //Populate Michael's GiftHashTable
    table1 = michael.getGiftList();
    table1.addGift(new Gift("Stick", 1.0, "Target"));
    GiftListTree tree = new GiftListTree();
    tree.insertPerson(michael);
    //Test to make sure totals are correct for Michael
    if(tree.getGiftCost() != 1.0) {
      fail("The gift cost should be 1.0 but instead was: " + tree.getGiftCost());
    }
    if(tree.getGiftCount() != 1) {
      fail("The gift number should be 1, but instead was: " + tree.getGiftCount());
    }
    tree.insertPerson(abbey);
    //Test totals are correct for Michael + Abbey
    if(tree.getGiftCost() != 2.0) {
      fail("The gift cost should be 2.0 but instead was: " + tree.getGiftCost());
    }
    if(tree.getGiftCount() != 2) {
      fail("The gift number should be 2, but instead was: " + tree.getGiftCount());
    }
    tree.insertPerson(gary);
    //Test totals are correct for Michael + Abbey + Gary
    if(tree.getGiftCost() != 2.0) {
      fail("The gift cost should not have changed");
    }
    if(tree.getGiftCount() != 2) {
      fail("The gift count should not have changed");
    }
  }
  
  /**
   * Tests for a critical bug that removed additional people if remove is called on an middle node
   */
  @Test
  void testExtraNotVisible() {
    //Create tree and populate with 4 people
    GiftListTree tree = new GiftListTree();
    Person toInsert = new Person("Michael");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("Abbey");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("Charlie");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("David");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
 //   System.out.println(tree);
    //Test that important methods work prior to removal
//    System.out.println(tree.containsPerson(toInsert));
//    System.out.println(tree.lookUpPerson(toInsert.name));
//    System.out.println(tree.getPeople());
    if(tree.getGiftCost() != 4.0) {
      fail("Should be 4.0");
    }
    if(tree.getGiftCount() != 4) {
      fail("Should be 4");
    }
    tree.RemovePerson("Michael");
//    System.out.println(tree);
//    System.out.println(tree.containsPerson(toInsert));
//    System.out.println(tree.lookUpPerson(toInsert.name));
//    System.out.println(tree.getPeople());
    //Tests to see if the methods return value changes appropriately
    if(tree.getGiftCost() != 3.0) {
      fail("Should be 3.0");
    }
    if(tree.getGiftCount() != 3) {
      fail("Should be 3");
    }
  }
  /**
   * Tests the save and load function of the GiftListTree class
   */
  @Test
  void saveAndLoad() {
    //Create the test tree
    GiftListTree tree = new GiftListTree();
    Person toInsert = new Person("Michael");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("Abbey");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("Charlie");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    toInsert = new Person("David");
    toInsert.add(new Gift("Stick", 1.0, "Target"));
    tree.insertPerson(toInsert);
    //Save the list and load info into another tree
    tree.saveList();
//    System.out.println(tree);
//    System.out.println("************************************************");
    GiftListTree tree2 = new GiftListTree();
    tree2.loadGiftListTree();
//    System.out.println(tree2);
    if(tree2.getGiftCost() != 4.0) {
      fail("Should cost 4.0");
    }
    if(tree2.getGiftCount() != 4) {
      fail("Should have a total of 4");
    }
    if(!tree.toString().contentEquals(tree2.toString())) {
      fail("Trees are not the same");
    }
    
    //Rerun after removing a middle node
    tree.RemovePerson("Michael");
    tree.saveList();
    tree2.loadGiftListTree();
    if(tree2.getGiftCost() != 3.0) {
      fail("Should cost 3.0");
    }
    if(tree2.getGiftCount() != 3) {
      fail("Should have a total of 3");
    }
    if(!tree.toString().contentEquals(tree2.toString())) {
      fail("Trees are not the same after removing a middle node");
    }
//    System.out.println(tree);
//    System.out.println("************************************************");
//    System.out.println(tree2);
  }
}

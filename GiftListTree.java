// --== CS400 File Header Information ==--
// Name: Michael Brudos
// Email: mbrudos@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

/**
 * @author Michael Brudos Creates a specialized RBT for the Christmas Gift List Application. This
 *         RBT uses type Person and hold the different users to be stored in the application
 */
import java.io.File;
import java.io.PrintWriter;
import java.util.LinkedList;

public class GiftListTree {
  private RedBlackTree<Person> Gifttree = new RedBlackTree<Person>(); // The RBT from earlier this
                                                                      // semester
  private int size = 0; // Number of people in the tree


  public void loadGiftListTree() {
    // Method call from Data wrangler that returns a GiftHashTable
    // Set fields based off GiftHashTable.
    GiftListTree load = GiftReader.readGifts("gift.csv");
    this.Gifttree = load.Gifttree;
    this.size = load.size;
  }

  /**
   * Inserts a person into the tree. If a person with the same name is found in the tree with no
   * visibility, visibility will be toggled to true;
   * 
   * @param p - person to be inserted into the tree
   * @throws NullPointerException     - when p is null or name is null
   * @throws IllegalArgumentException - when the name of the person to be inserted already exists in
   *                                  the tree
   */
  public void insertPerson(Person p) {
    // Can't be null
    if (p == null || p.name == null) {
      throw new NullPointerException("Person cannot be null");
    }
    if (Gifttree.root != null && containsPerson(p)) {
      // Check for invisible, same name, or not in tree
      Person toFind = lookUpPerson(p.name);
      if (toFind.isVisible == false) {
        toFind.isVisible = true;
        size++;
      } else if (toFind.isVisible == true) {
        throw new IllegalArgumentException("Can't enter two people with the same name");
      }
    } else {
      Gifttree.insert(p);
      size++;
    }
  }

  /**
   * Checks to see if the person provided is in the tree. True if found, false otherwise
   * 
   * @param p - person to search for in the tree
   * @return true if a person with the same name if found in the tree, false otherwise
   * @throws NullPointerException - If p is null or p's name is null
   */
  public boolean containsPerson(Person p) {
    if (p == null || p.name == null) {
      throw new IllegalArgumentException("Person cannot be null");
    }
    return containsHelper(Gifttree.root, p) != null;
  }

  /**
   * Fetches a person object from the tree with the given name
   * 
   * @param name - name to search for in the tree
   * @return Person - Person with the matching name field
   * @throws IllegalArgumentException - if name is null
   * @throws Illegal              ArgumentException - There is no person within the tree with the
   *                              given name
   */
  public Person lookUpPerson(String name) {
    Person p = new Person(name);
    if (containsPerson(p)) {
      return containsHelper(Gifttree.root, p).data;
    } else {
      throw new IllegalArgumentException(
          "There exists no person with name: " + name + "in the gift tree");
    }
  }

  /**
   * Traverses the Gifttree to locate a Person with a given name
   * 
   * @param toCheck - Node within Gifttree to search
   * @param toFind  - Person object to find (based on name)
   * @return Node within tree where match was made successfully. This Node is null if unsuccessful
   */
  private RedBlackTree.Node<Person> containsHelper(RedBlackTree.Node<Person> toCheck,
      Person toFind) {
    int result = toCheck.data.compareTo(toFind);
    RedBlackTree.Node<Person> finalNode = null;
    // Check left subtree
    if (result > 0) {
      // Check for null reference
      if (toCheck.leftChild != null) {
        finalNode = containsHelper(toCheck.leftChild, toFind);
      }
    } else if (result < 0) {
      // Check for null reference
      if (toCheck.rightChild != null) {
        finalNode = containsHelper(toCheck.rightChild, toFind);
      }
    } else {
      // Person found (result = 0)
      return toCheck;
    }
    // unsuccessful case
    return finalNode;
  }

  /**
   * String representation of GiftListTree
   */
  public String toString() {
    // return Gifttree.toString();
    if (Gifttree.root == null) {
      return "";
    } else {
      return "This list contains " + getTreeSize() + " people { " + toStringHelper(Gifttree.root)
          + "}";
    }

  }

  /**
   * Helps toString method
   * 
   * @param current - current node in the tree
   * @return String representation of tree in ABC order
   */
  private String toStringHelper(RedBlackTree.Node<Person> current) {
    String result = "";
    if (current.leftChild != null) {
        result = result + toStringHelper(current.leftChild) + "";
    }
    if (current.data.isVisible) {
      result = result + current.data.toString() + ",";
    }
    if (current.rightChild != null) {
        result = result + toStringHelper(current.rightChild) + "";
    }
    result = result.replaceAll(",", " ");
    return result;
  }

  /**
   * Gathers a list of visible persons in the RBT
   * 
   * @return - list of people in RBT
   */
  public LinkedList<Person> getPeople() {
    if (Gifttree.root == null) {
      return null;
    } else {
      return getPeopleHelper(Gifttree.root, new LinkedList<Person>());
    }
  }

  /**
   * Iterates through RBT and gathers people for getPeople method
   * 
   * @param current - current position in the tree
   * @return - LinkedList<Person> - list of visible people in the tree
   */
  public LinkedList<Person> getPeopleHelper(RedBlackTree.Node<Person> current, LinkedList<Person> list) {
    if (current.leftChild != null) {
        getPeopleHelper(current.leftChild, list);
    }
    if (current.data.isVisible) {
      list.add(current.data);
    }
    if (current.rightChild != null) { 
        getPeopleHelper(current.rightChild, list); 
    }
    return list;
  }

  /**
   * "Remove" a person from the GiftListTree. This method disables the visibility of
   * 
   * @param name
   * @throws IllegalArgumentException - name could not be found in the GiftListTree
   * @throws NullPointerException     - name is null
   */
  public void RemovePerson(String name) {
    if (name == null) {
      throw new NullPointerException("Name cannot be null");
    }
    if (containsPerson(new Person(name))) {
      Person toRemove = lookUpPerson(name);
      toRemove.isVisible = false;
      // TODO Clear gift list
      toRemove.getGiftList().clear();
      size--;
    }
    else {
      throw new IllegalArgumentException("This name was not found in the list and therefore could not be removed");
    }
  }

  /**
   * Gets the size of the tree
   * 
   * @return size - # of People in the tree list
   */
  public int getTreeSize() {
    return size;
  }

  /**
   * Iterates through the tree and calculates the number of gifts in the tree
   * 
   * @return - number of gifts in tree (int)
   */
  public int getGiftCount() {
    if (Gifttree.root == null) {
      return 0;
    } else {
      return getGiftCountHelper(Gifttree.root);
    }
  }

  /**
   * Iterates through the tree and calculates the number of gifts in the tree
   * 
   * @param current - current node in the tree
   * @return - int, # of people in the tree
   */
  public int getGiftCountHelper(RedBlackTree.Node<Person> current) {
    int total = 0;
    if (current.data.isVisible) {
        total += current.data.getGiftList().getTotalGifts();
    }
    if (current.leftChild != null) {
        total += getGiftCountHelper(current.leftChild);
    }
    if (current.rightChild != null) {
        total += getGiftCountHelper(current.rightChild);
    }
    return total;
  }

  /**
   * Iterates through the tree and calculates the cost of gifts in the tree
   * @return - cost of gifts in the tree
   */
  public double getGiftCost() {
    if (Gifttree.root == null) {
      return 0.0;
    } else {
      return getGiftCostHelper(Gifttree.root);
    }
  }

  /**
   * Iterates through the tree and calculates the cost of gifts in the tree
   * @param current - current position in the RBT
   * @return - double, cost of gifts in the tree
   */
  public double getGiftCostHelper(RedBlackTree.Node<Person> current) {
    double total = 0.0;
    //Add value of current
    if(current.data.isVisible) {
        total += current.data.getGiftList().getTotalCost();
    }
    //continue down left subtree
    if (current.leftChild != null) {
        total += getGiftCostHelper(current.leftChild);
    }
    //continue down right subtree
    if (current.rightChild != null) {
        total += getGiftCostHelper(current.rightChild);
    }
    return total;
  }

  // ref person, name, cost, store.
  /**
   * Saves the GiftListTree to a csv file so that the data can be recovered 
   */
  public void saveList() {
    if(size != 0) {
    File giftCSV = new File("gift.csv");
    PrintWriter giftWriter = null;
    try {
       giftWriter = new PrintWriter(giftCSV);
       giftWriter.println("Recipient,Gift Name,Cost,Store");
      // Start format for person.csv
      // personWriter.print(s);
      LinkedList<Person> people = this.getPeople();
      for(Person p : people) {
        giftWriter.println(savePerson(p));
      }
    } catch (java.io.FileNotFoundException e) {
      System.out.println("Save files could not be found");
    }
    giftWriter.close();
    }
  }

  /** Formats a person object into a string to be stored in a csv file by saveList() method
   * @param toSave - Person to break down into a String
   * @return - String representing object variable values necessary to recreate GiftListTree for the given person
   */
  private static String savePerson(Person toSave) {
    String result = "";
    GiftHashTable giftList = toSave.getGiftList();
    LinkedList<String> names = giftList.getNames();
    if (giftList.isEmpty()) {
      result += toSave.name + "," + "NULL" + "," + "NULL" + "," + "NULL" + "\n";
    } else {
      // Iterates through each type of key in the list
      for (int i = 0; i < names.size(); i++) {
        String item = names.get(i);
        // Retrieve item
        Gift toFind = giftList.getGift(item);
        // Format output line by pulling values from above gift
        result += toSave.name + "," + toFind.getName() + "," + toFind.getCost() + ","
            + toFind.getStore() + "\n";
      }
    }
    return result.trim();
  }
  
   
//   public static void main(String[] args) {
//   GiftListTree tree = new GiftListTree();
//   tree.insertPerson(new Person("Michael"));
//   tree.insertPerson(new Person("Abby"));
//   tree.insertPerson(new Person("Zach"));
//   tree.insertPerson(new Person("Yahtzee"));
//   LinkedList<Person> list = tree.getPeople();
//   for(Person p: list) {
//     System.out.println(p.name);
//   }
//   Gift stick = new Gift("Stick", 2.0, "Target");
//   Person toAdd = new Person("Gerry");
//   toAdd.add(stick);
//   tree.insertPerson(toAdd);
//   tree.saveList();
//   System.out.println(tree.containsPerson(new Person("Michael")));
//   System.out.println(tree.containsPerson(new Person("Abby")));
//   System.out.println(tree.containsPerson(new Person("Zach")));
//   System.out.println(tree.containsPerson(new Person("Yahtzee")));
//   System.out.println(tree);
//   tree.RemovePerson("Michael");
//   list = tree.getPeople();
//   for(Person p: list) {
//     System.out.println(p.name);
//   }
//   tree.RemovePerson("Abby");
//   list = tree.getPeople();
//   for(Person p: list) {
//     System.out.println(p.name);
//   }
//   tree.RemovePerson("Yahtzee");
//   System.out.println(tree);
//   tree.RemovePerson("Zach");
//   System.out.println(tree);
//     Person test = new Person("Danny");
//     Gift gift = new Gift("Train", 19.0, "Walmart");
//     test.add(gift);
//     gift = new Gift("Teddy Bear", 14.99, "Target");
//     test.add(gift);
//     System.out.println(savePerson(test));
//     tree.saveList();
//     tree.insertPerson(test);
//     tree.saveList();
//     test = new Person("Zylus");
//     test.add(gift);
//     gift = new Gift("Guitar", 200.0, "Walmart");
//     test.add(gift);
//     System.out.println(savePerson(test));
//     tree.insertPerson(test);
//     tree.saveList();
//     tree.RemovePerson("Danny");
//     tree.saveList();
//     tree.insertPerson(new Person("Danny"));
//     tree.saveList();
 //  }
   
}

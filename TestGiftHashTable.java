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
 * Determines proper functionality of the GiftHashTable class
 *
 */
class TestGiftHashTable {

  /**
   * Tests important methods of the GiftHashTableClass such as add, remove, containsGift, getTotalCost(), getTotalGifts(), clear
   */
  @Test
  void testCase1() {
    GiftHashTable table = new GiftHashTable();
    table.addGift(new Gift("Stick", 2.00, "Target"));
    if(!table.containsGift(new Gift("Stick", 2.00, "Target"))) {
      fail("Stick was not added to the hash table");
    }
    if(table.getTotalCost() != 2.0) {
      fail("Stick is supposed to cost 2.00");
    }
    if(table.getTotalGifts() != 1) {
      fail("There is only 1 gift in the tree");
    }
    table.addGift(new Gift("Linking Logs", 3.00, "Target"));
  //  System.out.println(table);
    if(!table.containsGift(new Gift("Linking Logs", 2.00, "Target"))) {
      fail("Linking Logs was not added to the hash table");
    }
    if(table.getTotalCost() != 5.0) {
      fail("Stick is supposed to cost 5.00");
    }
    if(table.getTotalGifts() != 2) {
      fail("There is 2 gifts in the tree");
    }
    if(!table.removeGift(new Gift("Linking Logs", 2.00, "Target"))) {
      fail("removeGift should return true");
    }
    if(table.containsGift(new Gift("Linking Logs", 2.00, "Target"))) {
      fail("Linking Logs were too be removed");
    }
    table.clear();
    if(!table.isEmpty()) {
      fail("Table should be empty");
    }
    if(table.containsGift(new Gift("Linking Logs", 2.00, "Target"))) {
      fail("Linking Logs was not still be in the hash table");
    }
    
    
  }

}

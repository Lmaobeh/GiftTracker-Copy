// --== CS400 File Header Information ==--
// Name: Pieran Robert
// Email: probert@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class GiftReader {

public static GiftListTree readGifts(String fileName)   
{  
GiftListTree giftTree = new GiftListTree();
String line = "";  
String splitBy = ",";  
try   
{  
//parsing a CSV file into BufferedReader class constructor  
BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));  
br.readLine();
if(br.readLine() == null) {
  br.close();
  return giftTree;
}
else {
  br = new BufferedReader(new FileReader(new File(fileName)));  
  br.readLine();
}
while ((line = br.readLine()) != null)   //returns a Boolean value  
{  
    Person p;
    String[] gifts = line.split(splitBy);
    try{
         p = giftTree.lookUpPerson(gifts[0]);
    }
    catch(Exception e){
     p = new Person(gifts[0]);
    giftTree.insertPerson(p);
    }
    if(!gifts[1].equals("NULL")) {
        p.add(new Gift(gifts[1], Double.parseDouble(gifts[2]), gifts[3])); 
    }
}   
}
catch (IOException e)   
{  
e.printStackTrace();  
}  
    return giftTree;
}

}

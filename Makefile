run: Main.class
	java --module-path JavaFXForLinux/ --add-modules javafx.controls Main

Main.class: Main.java GiftListTree.class GiftHashTable.class
	javac --module-path JavaFXForLinux/ --add-modules javafx.controls Main.java

test: compile
	java -jar junit5.jar -cp . --scan-classpath -n TestGiftHashTable
	java -jar junit5.jar -cp . --scan-classpath -n TestGiftListTree

compile: Main.class TestGiftListTree.class TestGiftHashTable.class

TestGiftListTree.class: GiftListTree.class junit5.jar TestGiftListTree.java
	javac -cp .:junit5.jar TestGiftListTree.java

TestGiftHashTable.class: GiftHashTable.class junit5.jar TestGiftHashTable.java
	javac -cp .:junit5.jar TestGiftHashTable.java

GiftListTree.class: GiftListTree.java GiftReader.class
	javac GiftListTree.java

GiftReader.class: GiftReader.java Person.class
	javac GiftReader.java

Person.class: Person.java
	javac Person.java

RedBlackTree.class: RedBlackTree.java
	javac RedBlackTree.java

GiftHashTable.class: GiftHashTable.java Gift.class HashTableMap.class
	javac GiftHashTable.java

Gift.class: Gift.java 
	javac Gift.java

HashTableMap.class: HashTableMap.java KeyValuePair.class MapADT.class
	javac HashTableMap.java

MapADT.class: MapADT.java
	javac MapADT.jav

KeyValuePair.class: KeyValuePair.java
	javac KeyValuePair.java
clean:
	$(RM) *.class

preview:
	eog screenshot.png

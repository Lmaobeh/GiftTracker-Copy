// --== CS400 File Header Information ==--
// Name: Lucas Nguyen
// Email: lfnguyen@wisc.edu
// Team: NE
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader: Also GiftListStyle.css
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.event.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

/**
 * @author Lucas
 *
 */
public class Main extends Application{

  private GiftListTree friendList;
  private Person currentPerson;
  private String userName;
  private Scene mainScene;
  private Stage primaryStage;
  
  /**
   * Shows the first page after the user logs in
   */
  public void login() {
    BorderPane mainPane = new BorderPane();
    mainPane.setPadding(new Insets(25));
    Text title = new Text("Hello, " + this.userName + " this is your current list");
    title.setId("title");
    mainPane.setTop(title);
    
    //sets up bottom button actions
    Button addPerson = new Button("Add/Remove Person");
    Button logout = new Button("Logout");
    logout.setOnAction((event)->{
      this.friendList.saveList();
      this.primaryStage.close();
    });
    addPerson.setOnAction((event)->{
      this.addRemovePersonPage();
    });
    HBox buttonBox = new HBox(10, addPerson, logout);  
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    mainPane.setBottom(buttonBox);
    
    //sets up scrollable button list
    VBox personTable = new VBox();
    personTable.setId("personTable");
    VBox.setVgrow(personTable, Priority.ALWAYS); 
    personTable.setFillWidth(true);
    personTable.setAlignment(Pos.TOP_LEFT);
    //scroll Pane setup
    ScrollPane scroll = new ScrollPane();
    scroll.setContent(personTable);
    mainPane.setCenter(scroll);
    
    BorderPane.setMargin(mainPane.getCenter(), new Insets(10, 25,25,25));
    //adds in the buttons into the list from the contained tree
    Text listTitle = new Text("Friends List:");
    personTable.getChildren().add(listTitle);
    List<Person> personList = this.getPersonList();
    if (personList == null || personList.isEmpty()) {
        Text emptyMsg = new Text("Your List is empty, people below");
        personTable.getChildren().add(emptyMsg);
        this.mainScene.setRoot(mainPane);
        return;
      }
     
      for (Person p : personList) {
        Button person = new Button(p.name);
        person.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(person, new Insets(5,10,5,10));
        personTable.getChildren().add(person);
        person.setOnAction((event) -> {
          this.showGiftList(person.getText());
        });
      }
      //shows this
      this.mainScene.setRoot(mainPane);
  }


  /**
   * Initalizes a tree from a file or returns tree as a list
   * @return
   */
  public List<Person> getPersonList() {
    if (this.friendList ==null) {
      this.friendList = new GiftListTree();// will load from file here
      this.friendList.loadGiftListTree();
    }
    LinkedList<Person> personList = friendList.getPeople();
    return personList;
  }

  /**
   * Gets a list of gift names from a person's name
   * @param person
   * @return
   */
  public List<String> getGifts(String person) {
    LinkedList<String> giftList = this.friendList.lookUpPerson(person).getGiftList().getNames();
    return giftList;
  }

  /**
   * Gets a gift from a persons  gift list (only works in a person buffer)
   * @param giftName Name of the gift
   * @return the gift
   */
  public Gift getGift(String giftName) {
    return currentPerson.getGiftList().getGift(new Gift(giftName, 0.0, ""));
  }


  /**
   * Shows the gift list page for a person
   * @param person The name of the person whos page is showinf
   */
  public void showGiftList(String person) {
    //get the persons name and the data about person and initalize
    this.currentPerson = this.friendList.lookUpPerson(person);
    List<String> giftList = this.getGifts(this.currentPerson.name);
    BorderPane mainPane = new BorderPane();
    mainPane.setPadding(new Insets(25));
    Text title = new Text(""+ currentPerson.name +"'s gift list");
    title.setId("title");
    mainPane.setTop(title);
    //set up control buttons
    Button back = new Button("back");
    Button addRemove = new Button("Add/Remove");
    HBox buttonBox = new HBox(10, addRemove, back);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    mainPane.setBottom(buttonBox);
    back.setOnAction((event)->{
      this.login();
    });
    addRemove.setOnAction((event)->{
      this.addRemoveGiftPage();
    });
    
    //set up scroll pane for the list
    ScrollPane scroll = new ScrollPane();
    VBox giftTable = new VBox();
    VBox.setVgrow(giftTable, Priority.ALWAYS);
    giftTable.setFillWidth(true);
    scroll.setContent(giftTable);
    mainPane.setCenter(scroll);
    BorderPane.setMargin(mainPane.getCenter(), new Insets(10, 25,25,25));
    
    //get the gifts and dysplay in the scroll pane
    if(giftList.isEmpty()) {
      Text empty = new Text("This person's List is empty, add people");
      giftTable.getChildren().add(empty);
      this.mainScene.setRoot(mainPane);
      return;
    } 
    for (String g: giftList) {
      Button b = new Button(g);
      b.setOnAction((event)->{
        this.showGift(b.getText());
      });
      giftTable.getChildren().add(b);
      
    }
    //set the main scene
    this.mainScene.setRoot(mainPane);
    return;
  }

  /**
   * Show the page for a gift
   * @param giftName the name of the gift whos page is showing
   */
  public void showGift(String giftName) {
    // get inital data
    Gift gift = this.getGift(giftName);
    Text title = new Text(gift.toString());
    title.setId("title");
    //set ub control buttons
    Button back = new Button("Back");
    HBox buttonBox = new HBox(back);
    back.setOnAction((event)-> {
      this.showGiftList(this.currentPerson.name);
    });
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    //create border pane and insert
    BorderPane mainPane = new BorderPane();
    mainPane.setCenter(title);
    mainPane.setBottom(buttonBox);
    //dysplay scene
    this.mainScene.setRoot(mainPane);
  }
  /**
   * Displays page to add or remove a person
   */
  public void addRemovePersonPage() {
    Text title = new Text("Enter name of the person to add/remove:");
    title.setId("title");
    Label name = new Label("Name: ");
    TextField userInput = new TextField();
    Button add = new Button("Add");
    Button remove = new Button("Remove");
    Text errorMsg = new Text();
    //sets up control buttons
    add.setOnAction((event)->{
      if (userInput.getLength() == 0) {
        errorMsg.setText("Please enter a name to add");
        errorMsg.setFill(Color.CRIMSON);
        return;
      }
      if (this.addPerson(userInput.getCharacters().toString())) {
        this.login();
        return;
      }
      errorMsg.setText("Person already exists");
      errorMsg.setFill(Color.CRIMSON);
      
    });
    
    remove.setOnAction((event)->{
      if (userInput.getLength() ==0 ) {
        errorMsg.setText("Please enter a name to remove");
        errorMsg.setFill(Color.CRIMSON);
        return;
      }
      if(this.removePerson(userInput.getCharacters().toString())){
        this.login();
        return;
      }
      errorMsg.setText("Person doesn't exist");
      errorMsg.setFill(Color.CRIMSON);
    });
    //sets up gird view
    GridPane mainPane = new GridPane();
    mainPane.setHgap(10);
    mainPane.setVgap(10);
    mainPane.setPadding(new Insets(25, 25, 25, 25));
    mainPane.setAlignment(Pos.CENTER);
 //adds first components   
    mainPane.add(title, 0, 0,2,1);
    mainPane.add(name, 0, 1);
    mainPane.add(userInput, 1, 1);
   //adds contorls
    mainPane.add(add, 1, 2);
    mainPane.add(remove, 2,2 );
    GridPane.setHalignment(add, HPos.RIGHT);
    mainPane.add(errorMsg, 0, 2,2,1);
    //display
    this.mainScene.setRoot(mainPane);
    
  }
  
  /**
   * Adds a person to gift tree
   * @param personName the name of a person to add
   * @return if the person was removed
   */
  public boolean addPerson(String personName) {
    try {
    this.friendList.insertPerson(new Person(personName));
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  /** remoevs a perons from the gift tree
   * @param personName name of person to remove
   * @return if the person was removed
   */
  public boolean removePerson(String personName) {
    try {
    this.friendList.RemovePerson(personName);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  /**
   * Page to add and remove the gifts
   */
  public void addRemoveGiftPage() {
    //sets up componnents
    Text title = new Text("Enter information for the gift to add/remove "
        + "(remove only needs a Name):");
    title.setId("title");
    title.setStyle("-fx-font-size: 14px;");
    Label name = new Label("Name: ");
    TextField userInputName = new TextField();
    Label cost = new Label("Cost: ");
    TextField userInputCost = new TextField();
    Label store = new Label("Store: ");
    TextField userInputStore = new TextField();
    Button add = new Button("Add");
    Button remove = new Button("Remove");
    Button back = new Button("Back");
    Text errorMsg = new Text();
    // create controls
    back.setOnAction((event)->{
      this.showGiftList(this.currentPerson.name);
    });
    
    add.setOnAction((event)->{
      if (userInputName.getLength() == 0|| userInputCost.getLength()== 0 ||
          userInputStore.getLength() ==0) {
        errorMsg.setText("Please fill out all fields");
        errorMsg.setFill(Color.CRIMSON);
        return;
      }
      if (this.addGift(userInputName.getCharacters().toString(),
          userInputCost.getCharacters().toString(), 
          userInputStore.getCharacters().toString())) {
        this.showGiftList(this.currentPerson.name);
        return;
      }
      errorMsg.setText("A field is formatted incorrectly or Gift exists already");
      errorMsg.setFill(Color.CRIMSON);
      
    });
    
    remove.setOnAction((event)->{
      if (userInputName.getLength() ==0 ) {
        errorMsg.setText("Please enter a name to remove");
        errorMsg.setFill(Color.CRIMSON);
        return;
      }
      if(this.removeGift(userInputName.getCharacters().toString())){
        this.showGiftList(this.currentPerson.name);
        return;
      }
      errorMsg.setText("Gift doesn't exist");
      errorMsg.setFill(Color.CRIMSON);
    });
    //set up the grid pane
    GridPane mainPane = new GridPane();
    mainPane.setHgap(10);
    mainPane.setVgap(10);
    mainPane.setPadding(new Insets(25, 25, 25, 25));
    mainPane.setAlignment(Pos.CENTER);
    //add components
    mainPane.add(title, 0, 0,2,1);
    mainPane.add(name, 0, 1);
    mainPane.add(userInputName, 1, 1);
    mainPane.add(cost, 0, 2);
    mainPane.add(userInputCost, 1, 2);
    mainPane.add(store, 0, 3);
    mainPane.add(userInputStore, 1, 3);
    // add controls
    mainPane.add(add, 1, 4);
    mainPane.add(remove, 2,4 );
    mainPane.add(back, 3,4);
    GridPane.setHalignment(add, HPos.RIGHT);
    mainPane.add(errorMsg, 0, 4,2,1);
    
    this.mainScene.setRoot(mainPane);
  }
  
  /**
   * Adds a gift to a persons list
   * @param name name of gift
   * @param cost cost of gift
   * @param store Store where gift is bought
   * @return true if added false if not
   */
  public boolean addGift(String name, String cost, String store) {
    try { 
    Double costdouble = Double.parseDouble(cost);
    return  this.currentPerson.add(new Gift(name, costdouble, store));
    } catch(Exception e) {
      return false;
    }
  }
  
  /**
   * Removes a gift for a person, must be in a person page
   * @param name name of the gift to remove
   * @return if the person was removed
   */
  public boolean removeGift(String name) {
    return this.currentPerson.removeGift(new Gift(name, 0.0,""));
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    BorderPane mainPane = new BorderPane();
    Button closeButton = new Button("Close");
    mainPane.setBottom(closeButton);
    Text title = new Text("Hello, please enter your username");
    title.setId("title");
    Label userName = new Label("User name: ");
    TextField userNameInput = new TextField();
    Button enterButton = new Button("submit");
    
    HBox buttonBox = new HBox(enterButton);
    buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
    
    Text errorText = new Text();
    errorText.setFont(new Font(10));
    errorText.setFill(Color.CRIMSON);
   
    Text outputText = new Text();

    GridPane gridPane = new GridPane();
    gridPane.add(title, 0, 0,2,1);
    gridPane.add(userName, 0, 1);
    gridPane.add(userNameInput, 1, 1);
    gridPane.add(buttonBox, 1,2);
    gridPane.add(errorText, 0, 2,2,1);
    gridPane.add(outputText, 0, 3);
    
    gridPane.setAlignment(Pos.CENTER);
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(25, 25, 25, 25));
    mainPane.setCenter(gridPane);
  
    Scene mainScene = new Scene(mainPane, 500, 500);
    this.mainScene = mainScene;
    mainScene.getStylesheets().add("GiftListStyle.css");
    primaryStage.setScene(mainScene);
  
    primaryStage.setTitle("Gift List Tracker");
    
    primaryStage.show();
    
//Button event handling
    closeButton.setOnAction((event)->{
      this.primaryStage.close();
    });
    enterButton.setOnAction((event)->{
      if (userNameInput.getLength() == 0) {
      errorText.setText("Please enter user name");
       return;
      }
      this.userName = userNameInput.getCharacters().toString();
      
      this.login();
 
    });
  }
  
 
    public static void main(String[] args){
      
      launch(args);
    }

}

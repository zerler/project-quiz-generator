package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
  int questionsLoaded = 0; //holds number of questions loaded
  
	@Override
	public void start(Stage primaryStage) {
		try {
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			createHomepage();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createHomepage() {
	  questionsLoaded = 4; //hardcode number of questions for now
	  Stage stage = new Stage();
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root,400,400);
	  
	  Label quizGenerator = new Label("Quiz Generator"); //label for title
	  quizGenerator.setFont(new Font("Arial", 30));
	  BorderPane.setAlignment(quizGenerator, Pos.CENTER);
	  BorderPane.setMargin(quizGenerator, new Insets(12,12,12,12)); //margin for title
      root.setTop(quizGenerator);
      
      Button addQuestion = new Button("Add Question"); //create addQuestion button
      Button loadQuestions = new Button("Load Questions");
      Button saveQuestions = new Button("Save Questions");
      Label numberOfQuestionsLoaded = new Label("Number of Questions Loaded: ");
      Label actualNumber = new Label(""+questionsLoaded); //label to dynamically change
      addQuestion.setOnAction(e -> addQuestionScreen(actualNumber)); //functionality of button
      loadQuestions.setOnAction(e -> loadSaveScreen());
      saveQuestions.setOnAction(e -> loadSaveScreen());
      
      ListView<CheckBox> listView = new ListView<CheckBox>(); //create list of topics
      ObservableList<CheckBox> topics = FXCollections.observableArrayList();
      topics.add(new CheckBox("hash table"));
      topics.add(new CheckBox("linux"));
      topics.add(new CheckBox("tree"));
      listView.setItems(topics);
      
      HBox numberBox = new HBox(numberOfQuestionsLoaded, actualNumber); //create layouts
      HBox topBox = new HBox(addQuestion, loadQuestions, saveQuestions);
      VBox mainVBox = new VBox(topBox, numberBox, listView);
      numberBox.setAlignment(Pos.CENTER); //center main elements
      topBox.setAlignment(Pos.CENTER);
      mainVBox.setAlignment(Pos.CENTER);
      HBox.setMargin(numberOfQuestionsLoaded, new Insets(5, 0, 5, 0)); //set margin for number label
      HBox.setMargin(actualNumber, new Insets(5, 0, 5, 0));
      root.setCenter(mainVBox); //set center
      
      Button startButton = new Button("START");
      Label desiredQuestions = new Label("Desired Number of Questions: ");
      TextField numQuestions = new TextField();
      HBox bottomBox = new HBox(startButton, desiredQuestions, numQuestions);
      HBox.setMargin(startButton, new Insets(0, 15, 0, 0));
      bottomBox.setAlignment(Pos.CENTER);
      root.setBottom(bottomBox);
      
      stage.setScene(scene);
      stage.setTitle("Quiz Generator");
      stage.show();
	}
	
	public void addQuestionScreen(Label count) {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root,400,400);
		createTitle(stage, root);
		
		Label topic = new Label("Topic: ");
		topic.setFont(new Font("Arial", 16));
		TextField topicField = new TextField();
		Label questionText = new Label("Question Text: ");
		questionText.setFont(new Font("Arial", 16));
		TextField questionTextField = new TextField();
		Label choicesLabel = new Label("Choices:");
		choicesLabel.setFont(new Font("Arial", 16));
		Label leaveBlank = new Label("leave choice blank if unneeded");
		leaveBlank.setFont(new Font("Arial", 16));
		
		ListView<TextField> listView = new ListView<TextField>();
		ObservableList<TextField> choices = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++)
			choices.add(new TextField());
		listView.setItems(choices);
		
		GridPane body = new GridPane();
		body.add(topic, 0, 0);
		body.add(topicField, 1, 0);
		body.add(questionText, 0, 1);
		body.add(questionTextField, 1, 1);
		body.add(choicesLabel, 0, 2);
		body.add(leaveBlank, 1, 2);
		GridPane.setMargin(topic, new Insets(20, 0, 0, 0));
		GridPane.setMargin(topicField, new Insets(20, 0, 0, 0));
		
		VBox main = new VBox(body, listView);
		root.setCenter(main);
		
	    VBox left = new VBox(new Label("A"), new Label("B"), new Label("C"), new Label("D"), 
	        new Label("E"));
	    Label current;
	    for (int i = 0; i < 5; i++) {
	      current = (Label)left.getChildren().get(i);
	      current.setFont(new Font("Arial", 24));
	      if (i > 0)
	        VBox.setMargin(left.getChildren().get(i), new Insets(5, 0, 0, 0));
	    }
	    
	    VBox.setMargin(left.getChildren().get(0), new Insets(90, 0, 0, 0));
	    root.setLeft(left);
		
		GridPane bottom = new GridPane();
		Button add = new Button("Add This Question");
		add.setOnAction(e -> {
		  questionsLoaded++;
		  count.setText(""+questionsLoaded);
		  stage.hide();
		});
		bottom.add(new Label("Correct Answer Letter:"), 0, 0);
		bottom.add(new TextField(), 1, 0);
		bottom.add(add, 2, 0);
		GridPane.setHalignment(add, HPos.RIGHT);
		root.setBottom(bottom);
		
		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		stage.show();
	}
	
	public void loadSaveScreen() {
	  Stage stage = new Stage();
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root,400,400);
      createTitle(stage, root);
      
      Label ifLoad = new Label("Load File Path:");
      Label ifSave = new Label("Save File Path:");
      TextField loadField = new TextField();
      TextField saveField = new TextField();
      Button loadButton = new Button("Load");
      Button saveButton = new Button("Save");
      
      GridPane main = new GridPane();
      main.add(ifLoad, 0, 0);
      main.add(loadField, 1, 0);
      main.add(loadButton, 2, 0);
      main.add(ifSave, 0, 1);
      main.add(saveField, 1, 1);
      main.add(saveButton, 2, 1);
      GridPane.setMargin(ifLoad, new Insets(20, 0, 0, 0));
      GridPane.setMargin(loadField, new Insets(20, 0, 0, 0));
      GridPane.setMargin(loadButton, new Insets(20, 0, 0, 0));
      root.setCenter(main);
      
      stage.setScene(scene);
      stage.setTitle("Quiz Generator");
      stage.show();
	}
	
	public void createTitle(Stage stage, BorderPane root) {
		Label quizGenerator = new Label("Quiz Generator");
		quizGenerator.setFont(new Font("Arial", 30));
		BorderPane.setAlignment(quizGenerator, Pos.CENTER);
		BorderPane.setMargin(quizGenerator, new Insets(12,12,12,12));
		
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> stage.hide());
		backButton.setFont(new Font("Arial", 20));
		
		GridPane titleGrid = new GridPane();
		titleGrid.add(backButton, 0, 0);
		titleGrid.add(quizGenerator, 1, 0);
		GridPane.setMargin(quizGenerator, new Insets(0, 0, 0, 20));
		root.setTop(titleGrid);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

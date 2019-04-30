package application;
	
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
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
  Teacher teacher = new Teacher();
  
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
      Label actualNumber = new Label(""+teacher.unsortedQuestions.size()); //label to dynamically change
      addQuestion.setOnAction(e -> {  //functionality of button
        addQuestionScreen();
        stage.hide();
      });
      loadQuestions.setOnAction(e -> {
        loadSaveScreen();
        stage.hide();
      });
      saveQuestions.setOnAction(e -> {
        loadSaveScreen();
        stage.hide();
      });
      
      ListView<CheckBox> listView = new ListView<CheckBox>(); //create list of topics
      ObservableList<CheckBox> topics = FXCollections.observableArrayList();
      for (String topic : teacher.sortedQuestions.keySet())
        topics.add(new CheckBox(topic));

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
	
	public void addQuestionScreen() {
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
		Label imgPath = new Label("Image Path: ");
		imgPath.setFont(new Font("Arial", 16));
		TextField imgPathTextField = new TextField();
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
		body.add(imgPath, 0, 2);
		body.add(imgPathTextField, 1, 2);
		body.add(choicesLabel, 0, 3);
        body.add(leaveBlank, 1, 3);
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
	    
	    VBox.setMargin(left.getChildren().get(0), new Insets(120, 0, 0, 0));
	    root.setLeft(left);
		
		GridPane bottom = new GridPane();
		Button add = new Button("Add This Question");
		TextField correctAnswer = new TextField();
		bottom.add(new Label("Full Correct Answer:"), 0, 0);
		bottom.add(correctAnswer, 1, 0);
		bottom.add(add, 2, 0);
		GridPane.setHalignment(add, HPos.RIGHT);
		root.setBottom(bottom);
		
		add.setOnAction(e -> {
          questionsLoaded++;
          ArrayList<String> stringChoices = new ArrayList<>();
          for (TextField field : choices) {
            if (!field.getText().equals(""))
              stringChoices.add(field.getText());
          }
          
          teacher.addQuestion(questionTextField.getText(), stringChoices, correctAnswer.getText(),
              topicField.getText(), imgPathTextField.getText());
          stage.hide();
          createHomepage();
        });
		
		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		stage.show();
	}
	
	public void loadSaveScreen() {
	  Stage stage = new Stage();
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root,350,150);
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
      
      loadButton.setOnAction(e -> {
        teacher.loadAdditionalQuestions(loadField.getText());
        stage.hide();
        createHomepage();
      });
      
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
		backButton.setOnAction(e -> {
		  stage.hide();
		  createHomepage();
		});
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

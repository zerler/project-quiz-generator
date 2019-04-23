package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			createHomepage(root, scene);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Quiz Generator");
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createHomepage(BorderPane root, Scene scene) {
	  Label quizGenerator = new Label("Quiz Generator");
	  quizGenerator.setFont(new Font("Arial", 30));
	  BorderPane.setAlignment(quizGenerator, Pos.CENTER);
	  BorderPane.setMargin(quizGenerator, new Insets(12,12,12,12)); // optional
      root.setTop(quizGenerator);
      
      Button addQuestion = new Button("Add Question");
      Button loadQuestions = new Button("Load Questions");
      Button saveQuestions = new Button("Save Questions");
      Label numberOfQuestionsLoaded = new Label("Number of Questions Loaded: ");
      
      ListView<CheckBox> listView = new ListView<CheckBox>();
      ObservableList<CheckBox> topics = FXCollections.observableArrayList();
      
      topics.add(new CheckBox("hash table"));
      topics.add(new CheckBox("linux"));
      topics.add(new CheckBox("tree"));
      listView.setItems(topics);
      
      HBox topBox = new HBox(addQuestion, loadQuestions, saveQuestions);
      topBox.setAlignment(Pos.CENTER);
      VBox mainVBox = new VBox(topBox, numberOfQuestionsLoaded, listView);
      mainVBox.setAlignment(Pos.CENTER);
      root.setCenter(mainVBox);
      
      Button startButton = new Button("Start");
      Label desiredQuestions = new Label("Desired Number of Questions: ");
      TextField numQuestions = new TextField();
      HBox bottomBox = new HBox(startButton, desiredQuestions, numQuestions);
      bottomBox.setAlignment(Pos.CENTER);
      root.setBottom(bottomBox);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

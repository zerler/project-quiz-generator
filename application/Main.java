package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			createHomepage(root, scene);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createHomepage(BorderPane root, Scene scene) {
	  Label quizGenerator = new Label("Quiz Generator");
      root.setTop(quizGenerator);
      
      Button addQuestion = new Button("Add Question");
      Button loadQuestions = new Button("Load Questions");
      Button saveQuestions = new Button("Save Questions");
      
      Label numberOfQuestions = new Label("Number of Questions: ");
      
      HBox topBox = new HBox(addQuestion, loadQuestions, saveQuestions);
      VBox mainVBox = new VBox(topBox, numberOfQuestions);
      
      root.setCenter(mainVBox);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Main driver class to make the JavaFX gui operate
 * 
 * @author All Members
 *
 */
public class Main extends Application {
	int questionsLoaded = 0; // holds number of questions loaded
	Teacher teacher = new Teacher(); // teacher which makes quizzes

	/**
	 * Method gets called on startup of the GUI. Calls the create home page method.
	 * 
	 * @param primaryStage - the first stage created
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			createHomepage(); // attempt to create the homepage GUI
		} catch (Exception e) {
			/* do nothing */}
	}

	/**
	 * Method generates a homepage screen for the quiz generator. Show options to
	 * save/load questions as well as an option to add a new question. Allows the
	 * user to enter a number of desired questions for their quiz, and a start
	 * button. Also shows a list of topics which the user can select to be included
	 * in their quiz.
	 */
	private void createHomepage() {
		Stage stage = new Stage(); // creates the essential parts of the GUI
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);

		Label quizGenerator = new Label("Quiz Generator"); // label for title
		quizGenerator.setFont(new Font("Arial", 30)); // modify font of title
		BorderPane.setAlignment(quizGenerator, Pos.CENTER); // center title
		BorderPane.setMargin(quizGenerator, new Insets(12, 12, 12, 12)); // margin for title
		root.setTop(quizGenerator);

		Button addQuestion = new Button("Add Question"); // create necessary buttons
		Button loadQuestions = new Button("Load Questions");
		Button saveQuestions = new Button("Save Questions");
		Label numberOfQuestionsLoaded = new Label("Number of Questions Loaded: ");
		Label actualNumber = new Label("" + teacher.unsortedQuestions.size()); // label to dynamically change
		addQuestion.setOnAction(e -> { // functionality of buttons
			addQuestionScreen(); // go to the addQuestion screen
			stage.hide(); // close this GUI screen
		});
		loadQuestions.setOnAction(e -> {
			loadSaveScreen();
			stage.hide();
		});
		saveQuestions.setOnAction(e -> {
			loadSaveScreen();
			stage.hide();
		});

		ListView<CheckBox> listView = new ListView<CheckBox>(); // create list of topics
		ObservableList<CheckBox> topics = FXCollections.observableArrayList();
		for (String topic : teacher.sortedQuestions.keySet()) // add all topics from teacher
			topics.add(new CheckBox(topic));

		listView.setItems(topics); // set the listView as these topics

		HBox numberBox = new HBox(numberOfQuestionsLoaded, actualNumber); // create layouts
		HBox topBox = new HBox(addQuestion, loadQuestions, saveQuestions);
		VBox mainVBox = new VBox(topBox, numberBox, listView);
		numberBox.setAlignment(Pos.CENTER); // center main elements
		topBox.setAlignment(Pos.CENTER);
		mainVBox.setAlignment(Pos.CENTER);
		HBox.setMargin(numberOfQuestionsLoaded, new Insets(5, 0, 5, 0)); // set margin for number label
		HBox.setMargin(actualNumber, new Insets(5, 0, 5, 0));
		root.setCenter(mainVBox); // set center

		Label desiredQuestions = new Label("Desired Number of Questions: "); // GUI elements for bottom
		TextField numQuestions = new TextField();
		Button startButton = new Button("START"); // create start button
		HBox bottomBox = new HBox(startButton, desiredQuestions, numQuestions);
		startButton.setOnAction(e -> { // add functionality to start button
			ArrayList<String> topicsForQuiz = new ArrayList<String>(); // get topics as strings for quiz
			for (int i = 0; i < topics.size(); i++) {
				if (topics.get(i).isSelected()) // only get selected topics
					topicsForQuiz.add(topics.get(i).getText());
			}
			int numberOfQuestions;
			Quiz quiz = null;
			try {
				numberOfQuestions = Integer.parseInt(numQuestions.getText());
				quiz = this.teacher.makeQuiz(topicsForQuiz, numberOfQuestions); // make new quiz
				try {
					stage.hide();
					this.answerQuestionScreen(quiz, 0); // go to answer question GUI
				} catch (FileNotFoundException e1) {
					/* do nothing */}
			} catch (Exception e1) {
				/* do nothing */}

		});
		HBox.setMargin(startButton, new Insets(0, 15, 0, 0)); // add some spacing
		bottomBox.setAlignment(Pos.CENTER); // center these elements
		root.setBottom(bottomBox);

		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		stage.show();
	}

	/**
	 * Generates the add question screen, with text fields for all of the required
	 * information for a new question. After inputting new information, attempts to
	 * add the question to the quiz generator.
	 */
	public void addQuestionScreen() {
		Stage stage = new Stage(); // create essential GUI elements
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		createTitle(stage, root); // set same title bar

		Label topic = new Label("Topic: "); // create GUI elements for this screen
		TextField topicField = new TextField();
		Label questionText = new Label("Question Text: ");
		TextField questionTextField = new TextField();
		Label imgPath = new Label("Image Path: ");
		TextField imgPathTextField = new TextField();
		Label choicesLabel = new Label("Choices:");
		Label leaveBlank = new Label("leave choice blank if unneeded");
		topic.setFont(new Font("Arial", 16)); // setting fonts of labels
		questionText.setFont(new Font("Arial", 16));
		imgPath.setFont(new Font("Arial", 16));
		choicesLabel.setFont(new Font("Arial", 16));
		leaveBlank.setFont(new Font("Arial", 16));

		ListView<TextField> listView = new ListView<TextField>(); // creating a list of textfields
		ObservableList<TextField> choices = FXCollections.observableArrayList();
		for (int i = 0; i < 5; i++) // show 5 blank text fields for choices
			choices.add(new TextField());
		listView.setItems(choices);

		GridPane body = new GridPane(); // create GridPane to organize all elements
		body.add(topic, 0, 0);
		body.add(topicField, 1, 0);
		body.add(questionText, 0, 1);
		body.add(questionTextField, 1, 1);
		body.add(imgPath, 0, 2);
		body.add(imgPathTextField, 1, 2);
		body.add(choicesLabel, 0, 3);
		body.add(leaveBlank, 1, 3);
		GridPane.setMargin(topic, new Insets(20, 0, 0, 0)); // add spacing
		GridPane.setMargin(topicField, new Insets(20, 0, 0, 0));

		VBox main = new VBox(body, listView); // further layout organization with VBox
		root.setCenter(main);

		VBox left = new VBox(new Label("A"), new Label("B"), new Label("C"), new Label("D"), new Label("E")); // label
																												// different
																												// selections
		Label current;
		for (int i = 0; i < 5; i++) { // set all letters to the same large font
			current = (Label) left.getChildren().get(i);
			current.setFont(new Font("Arial", 24));
			if (i > 0)
				VBox.setMargin(left.getChildren().get(i), new Insets(5, 0, 0, 0)); // space for letters
		}

		VBox.setMargin(left.getChildren().get(0), new Insets(120, 0, 0, 0)); // space above "A"
		root.setLeft(left);

		GridPane bottom = new GridPane(); // grid pane to organize elements at bottom
		Button add = new Button("Add This Question");
		TextField correctAnswer = new TextField();
		bottom.add(new Label("Full Correct Answer:"), 0, 0);
		bottom.add(correctAnswer, 1, 0);
		bottom.add(add, 2, 0);
		GridPane.setHalignment(add, HPos.RIGHT);
		root.setBottom(bottom);

		add.setOnAction(e -> { // add functionality to "add question" button
			questionsLoaded++; // keep track of how many questions are loaded
			ArrayList<String> stringChoices = new ArrayList<>(); // make an array of all choices
			for (TextField field : choices) {
				if (!field.getText().equals(""))
					stringChoices.add(field.getText());
			}
			
			// adds a default image if none are specified
			if(imgPathTextField.getText().equalsIgnoreCase("")) {
			  imgPathTextField.setText("quizbueno.jpg");
			}

			// attempt to create a question with this information
			teacher.addQuestion(questionTextField.getText(), stringChoices, correctAnswer.getText(),
					topicField.getText(), imgPathTextField.getText());
			stage.hide(); // close this window
			createHomepage(); // go back home
		});

		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		stage.show();
	}

	/**
	 * GUI screen to enter path for JSON files to load or save questions.
	 */
	public void loadSaveScreen() {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 350, 150);
		createTitle(stage, root); // add same title as other screens

		Label ifLoad = new Label("Load File Path:"); // create GUI elements for this screen
		Label ifSave = new Label("Save File Path:");
		TextField loadField = new TextField();
		TextField saveField = new TextField();
		Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");

		GridPane main = new GridPane(); // create grid pane to organize elements
		main.add(ifLoad, 0, 0);
		main.add(loadField, 1, 0);
		main.add(loadButton, 2, 0);
		main.add(ifSave, 0, 1);
		main.add(saveField, 1, 1);
		main.add(saveButton, 2, 1);
		GridPane.setMargin(ifLoad, new Insets(20, 0, 0, 0)); // add some spacing
		GridPane.setMargin(loadField, new Insets(20, 0, 0, 0));
		GridPane.setMargin(loadButton, new Insets(20, 0, 0, 0));
		root.setCenter(main);

		loadButton.setOnAction(e -> { // add functionality to the buttons
			teacher.loadAdditionalQuestions(loadField.getText()); // load questions
			stage.hide(); // close this window
			createHomepage(); // go back to home screen
		});
		saveButton.setOnAction(e -> {
			teacher.saveQuestions(saveField.getText()); // save questions
			stage.hide(); // close this window
			createHomepage(); // go back to home screen
		});

		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		stage.show();
	}

	/**
	 * Small method created to keep the title bar uniform throughout the GUI
	 * screens.
	 * 
	 * @param stage - the stage to be modified
	 * @param root  - the root layout to be modified
	 */
	private void createTitle(Stage stage, BorderPane root) {
		Label quizGenerator = new Label("Quiz Generator"); // big quiz generator title
		quizGenerator.setFont(new Font("Arial", 30)); // set the font for title
		BorderPane.setAlignment(quizGenerator, Pos.CENTER); // center title
		BorderPane.setMargin(quizGenerator, new Insets(12, 12, 12, 12)); // add spacing

		Button backButton = new Button("Back"); // back button in top left
		backButton.setOnAction(e -> { // add functionality to button
			stage.hide(); // close this window
			createHomepage(); // go home
		});
		backButton.setFont(new Font("Arial", 20)); // sent font for button

		GridPane titleGrid = new GridPane(); // grid pane to organize the title bar
		titleGrid.add(backButton, 0, 0);
		titleGrid.add(quizGenerator, 1, 0);
		GridPane.setMargin(quizGenerator, new Insets(0, 0, 0, 20)); // add spacing
		root.setTop(titleGrid);
	}

	public void createResultScreen(Quiz quiz) {
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		createTitle(stage, root);

//		ArrayList<Question> topic = new ArrayList<Question>();
//		Quiz quiz = new Quiz(topic);

		double score = Math.floor(quiz.calculateScore());
		Label result = new Label("Result");
		Label correct = new Label("Correct: " + quiz.getNumberAnswersCorrect());
		Label incorrect = new Label("Incorrect: " + quiz.answersIncorrect);
		Label numOfQuestion = new Label("Number of questions: " + quiz.getNumQuestions());
		Label percentCorrect = new Label("Grade Percent: " + score);
		Button retry = new Button("Try Again");
		result.setFont(new Font("Arial", 20));
		correct.setFont(new Font("Arial", 15));
		incorrect.setFont(new Font("Arial", 15));
		numOfQuestion.setFont(new Font("Arial", 15));
		percentCorrect.setFont(new Font("Arial", 15));
		retry.setFont(new Font("Arial", 20));

		GridPane.setMargin(result, new Insets(0, 0, 30, 0));
		GridPane.setMargin(correct, new Insets(0, 0, 5, 0));
		GridPane.setMargin(incorrect, new Insets(0, 0, 5, 0));
		GridPane.setMargin(numOfQuestion, new Insets(0, 0, 5, 0));
		GridPane.setMargin(percentCorrect, new Insets(0, 0, 5, 0));

		retry.setOnAction(e -> {
			stage.hide();
			createHomepage();
		});

		GridPane resultScreen = new GridPane();
		resultScreen.add(result, 0, 0);
		resultScreen.add(correct, 0, 1);
		resultScreen.add(incorrect, 0, 2);
		resultScreen.add(numOfQuestion, 0, 3);
		resultScreen.add(percentCorrect, 0, 4);
		resultScreen.add(retry, 0, 5);
		root.setTop(resultScreen);

		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This is the GUI for answer a question
	 * 
	 * @param quiz  the quiz object
	 * @param index the index of question
	 * @throws FileNotFoundException
	 */
	public void answerQuestionScreen(Quiz quiz, int index) throws FileNotFoundException {

		Stage stage = new Stage();
		if (index == quiz.questions.size()) {
			stage.hide();
			this.createResultScreen(quiz);
			return;
		}
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 600, 400);
		createTitle(stage, root);
		Question question;
		String questionText;
		ArrayList<String> choices;
		VBox left;
		VBox right;
		Button submit_and_next;
		FileInputStream inputstream;
		Image image = null;
		ToggleGroup group;
		ArrayList<RadioButton> choicesLabel;
		Label questionSet;
		// this array indicates whether question is submitted
		ArrayList<Boolean> flagArray = new ArrayList<Boolean>();
		question = quiz.questions.get(index);
		questionText = question.getQuestion();
		choices = question.getChoices();
		left = new VBox();
		right = new VBox();
		submit_and_next = new Button("Submit");
		image = null;
		group = new ToggleGroup();
		if (question.imageFile != null) { // if this questino has an image
			try {
				inputstream = new FileInputStream(question.imageFile);
				image = new Image(inputstream);
			} catch (FileNotFoundException e) {
			}
		}
		ImageView imageView = new ImageView(image);
		// resize the image if size is too big
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);
		// add image to right part
		right.getChildren().add(imageView);
		// create and add question text to left part
		questionSet = new Label(questionText);
		left.getChildren().add(questionSet);
		// use radio button to add choices
		choicesLabel = new ArrayList<RadioButton>();
		int numChoices = choices.size();
		for (int i = 0; i < numChoices; i++) {
			choicesLabel.add(new RadioButton(choices.get(i)));
			choicesLabel.get(i).setToggleGroup(group);
			left.getChildren().add(choicesLabel.get(i));
		}
		left.getChildren().add(submit_and_next);
		root.setLeft(left);
		root.setRight(right);
		stage.setScene(scene);
		stage.setTitle("Quiz Generator");
		submit_and_next.setOnAction(e -> {
			if (flagArray.size() != 0) {
				stage.hide();
				try { // if the question is answered, go to next question
					answerQuestionScreen(quiz, index + 1);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				Label isCorrect = null;
				RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
				if (selectedRadioButton.equals(null)) { // no choice is selected
					return;
				} else {
					// check if the choice is correct
					for (int i = 0; i < numChoices; i++) {
						if (choicesLabel.get(i).equals(selectedRadioButton)) {
							if (choicesLabel.get(i).getText().equals(question.getAnswer())) {
								// create correct label
								isCorrect = new Label("Correct!");
								question.isCorrect = true;
								break;
							} else {
								// create incorrect label
								isCorrect = new Label("Incorrect!");
								question.isCorrect = false;
								break;
							}
						}
					}
					// add a label to show if answer is correct
					left.getChildren().add(isCorrect);
					submit_and_next.setText("Next Question"); // change button's name
					flagArray.add(true); // update flag array, next time it will go to next question
				}
			}
		});
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

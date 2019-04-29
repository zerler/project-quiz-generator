package application;

import java.util.ArrayList;

public class Question {
  private String questionText;
  private ArrayList<String> choices;
  private String correctAnswer; //example "A", "B" ...
  private String topic; //example "linux", "AVL trees"
  private boolean isCorrect; //holds whether user answered question correctly
  private String imageFile; //holds path for optional image in question OR null
  
  public Question(String questionText, ArrayList<String> choices, String answer, String topic,
      String filePath) {
    this.questionText = questionText;
    this.choices = choices;
    this.correctAnswer = answer;
    this.topic = topic;
    if (filePath.length() > 0)
      this.imageFile = filePath;
    else
      this.imageFile = null;
  }
}

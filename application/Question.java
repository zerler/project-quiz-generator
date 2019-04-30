package application;

import java.util.ArrayList;
import java.util.Arrays;

public class Question {
  private String questionText;
  private ArrayList<String> choices;
  private String correctAnswer;
  private String topic; //example "linux", "AVL trees"
  public boolean isCorrect; //holds whether user answered question correctly
  public String imageFile; //holds path for optional image in question OR null
  
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
  
  public void setQuestion(String newQuestion) {
    this.questionText = newQuestion;
  }
  
  public String getQuestion() {
    return this.questionText;
  }
  
  public void addChoice(String newChoice) {
    if (!newChoice.equals(""))
      choices.add(newChoice);
  }
  
  public ArrayList<String> getChoices() {
    return this.choices;
  }
  
  public void setAnswer(String answer) {
    answer = answer.toLowerCase();
    this.correctAnswer = answer;
  }
  
  public String getAnswer() {
    return this.correctAnswer;
  }
  
  public void setTopic(String topic) {
    this.topic = topic;
  }
  
  public String getTopic() {
    return this.topic;
  }
  
  public void setImage(String newPath) {
    this.imageFile = newPath;
  }
}

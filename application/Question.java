package application;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Question class to hold all of the necessary information for each quiz question.
 * @author Ben Zerler
 */
public class Question {
  private String questionText; //actual text for the question
  private ArrayList<String> choices; //possible answers to the question
  private String correctAnswer; //full correct answer as a string
  private String topic; //example "linux", "AVL trees"
  public boolean isCorrect; //holds whether user answered question correctly
  public String imageFile; //holds path for optional image in question OR null
  
  /**
   * Only constructor for a Question
   * @param questionText - text for the actual question
   * @param choices - all of the possible answers to the question
   * @param answer - full correct answer as a string
   * @param topic - the topic of the question
   * @param filePath - possible path for a picture
   */
  public Question(String questionText, ArrayList<String> choices, String answer, String topic,
      String filePath) {
    this.questionText = questionText; //set all fields as the passed parameters
    this.choices = choices;
    this.correctAnswer = answer;
    this.topic = topic;
    if (filePath.length() > 0) //make sure filePath isn't blank
      this.imageFile = filePath;
    else
      this.imageFile = null;
  }
  
  /**
   * Setter for the question text.
   * @param newQuestion - the desired question text
   */
  public void setQuestion(String newQuestion) {
    this.questionText = newQuestion;
  }
  
  /**
   * Getter for the question text.
   * @return question text
   */
  public String getQuestion() {
    return this.questionText;
  }
  
  /**
   * Add another choice to this question
   * @param newChoice - new possible answer
   */
  public void addChoice(String newChoice) {
    if (!newChoice.equals("")) //make sure new Choice isn't blank
      choices.add(newChoice);
  }
  
  /**
   * Return ArrayList of all choices as strings
   */
  public ArrayList<String> getChoices() {
    return this.choices;
  }
  
  /**
   * Change the correct answer of a question.
   * @param answer - new correct answer
   */
  public void setAnswer(String answer) {
    this.correctAnswer = answer;
  }
  
  /**
   * Get the correct answer of a question.
   * @return - correct answer
   */
  public String getAnswer() {
    return this.correctAnswer;
  }
  
  /**
   * Set the topic of a question as something else.
   * @param topic - new topic
   */
  public void setTopic(String topic) {
    this.topic = topic;
  }
  
  /**
   * Get the current topic of a question.
   * @return - current topic
   */
  public String getTopic() {
    return this.topic;
  }
  
  /**
   * Set the image of this question as some new picture.
   * @param newPath - path that leads to a different picture
   */
  public void setImage(String newPath) {
    this.imageFile = newPath;
  }
}

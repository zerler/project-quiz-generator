package application;

import java.util.ArrayList;

/**
 * Quiz class to store questions and score for each quiz.
 * 
 */
public class Quiz {
  ArrayList<Question> questions; //all questions to display for this quiz
  int answersCorrect; //# of questions answered correctly
  int answersIncorrect; //# of questions answered incorrectly
  double score; //calculated score
  
  /**
   * Constructor of a quiz, initialize all fields
   * @param questions - the questions to put on the quiz
   */
  public Quiz (ArrayList<Question> questions) {
    answersCorrect = 0;
    answersIncorrect = 0;
    score = 0.0;
    this.questions = questions;
  }
  
  /**
   * Calculate score to put on the result GUI screen.
   * @return - a percent of questions answered correctly
   */
  public double calculateScore() {
    for (Question question : questions) {
      if (question.isCorrect)
        answersCorrect++;
      else
        answersIncorrect++;
    }
    score = (double)answersCorrect/(answersCorrect+answersIncorrect);
    score *= 100;
    /*System.out.println("correct: "+answersCorrect+" incorrect: "+answersIncorrect+" result: "+
    score);*/
    return score;
  }
  
  /**
   * Returns number of questions answered correctly
   * @return answersCorrect
   */
  public int getNumberAnswersCorrect() {
	  return answersCorrect;
  }
  
  /**
   * Returns the score on this quiz.
   * @return score
   */
  public double getScore() {
      this.calculateScore();
	  return score;
  }
  
  /**
   * Get the number of questions on this quiz.
   * @return number of questions
   */
  public int getNumQuestions() {
	  return questions.size();
  }
  
  /**
   * Return an arraylist of all of the questions on the quiz.
   * @return all questions
   */
  public ArrayList<Question> getQuestions(){
	  return questions;
  }
}

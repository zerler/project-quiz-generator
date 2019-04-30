package application;

import java.util.ArrayList;

public class Quiz {
  ArrayList<Question> questions;
  int answersCorrect;
  int answersIncorrect;
  double score;
  
  public Quiz (ArrayList<Question> questions) {
    answersCorrect = 0;
    answersIncorrect = 0;
    score = 0.0;
    
    this.questions = questions;
    
  }
  
  public double calculateScore() {
    for (Question question : questions) {
      if (question.isCorrect)
        answersCorrect++;
      else
        answersIncorrect++;
    }
    score = (double)answersCorrect/(answersCorrect+answersIncorrect);
    score *= 100;
    return score;
  }
  
  
  
}

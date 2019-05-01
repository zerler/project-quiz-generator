package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.Random;

/**
 * Teacher class contains all of the imported and created questions, can load questions, save
 * questions, and create a new quiz.
 * @author All Members
 *
 */
public class Teacher {
  protected Map<String, ArrayList<Question>> sortedQuestions; //sorted questions into topics
  protected ArrayList<Question> unsortedQuestions; //all questions, unsorted
  
  /**
   * Default constructor for a Teacher, creates blank map and array list for questions.
   */
  public Teacher() {
    sortedQuestions = new TreeMap<String, ArrayList<Question>>();
    unsortedQuestions = new ArrayList<Question>();
  }
  
  /**
   * Reads a JSON File at the passed filePath, adds to the sorted and unsorted lists.
   * @param filePath - location of the JSON file to import from
   */
  public void loadAdditionalQuestions(String filePath) {
    try {
      Object obj = new JSONParser().parse(new FileReader(filePath)); //create JSON parser
      JSONObject jo = (JSONObject) obj; //convert to JSON object
      JSONArray allQuestions = (JSONArray) jo.get("questionArray"); //get all question objects
      
      for (Object question : allQuestions) { //iterate through all questions
        JSONObject JSONQuestion = (JSONObject)question; //convert each question to a JSON Object
        
        String topic = (String) JSONQuestion.get("topic"); //get all information from question
        String questionText = (String) JSONQuestion.get("questionText");
        String image = (String) JSONQuestion.get("image");
        
        JSONArray choices = (JSONArray) JSONQuestion.get("choiceArray"); //get all choices
        String correctAnswer = "";
        ArrayList<String> stringChoices = new ArrayList<String>();
        for (Object choice : choices) { //iterate through all choices
          JSONObject JSONChoice = (JSONObject)choice; //convert each choice to a JSON Object
          if(JSONChoice.get("isCorrect").equals("T")) //checks if it's the right answer
            correctAnswer = (String) JSONChoice.get("choice");
          stringChoices.add((String)JSONChoice.get("choice")); //add each choice to string arraylist
        }
        if (correctAnswer.equals("")) //if no answers marked correct, cancel
          return;
        
        //create question, add to all questions
        unsortedQuestions.add(new Question(questionText, stringChoices, correctAnswer, topic, image));
        sortQuestions();
      }
    } catch (Exception e) { return; }
  }
  
  /**
   * Iterate through all unsorted questions, add them to the sorted map if they aren't present.
   */
  private void sortQuestions() {
    for(Question question : unsortedQuestions) { //iterates through all questions imported
      
      if(!sortedQuestions.containsKey(question.getTopic())) { //if it's a new topic
        ArrayList<Question> newTopic = new ArrayList<>(Arrays.asList(question));
        sortedQuestions.put(question.getTopic(), newTopic); //add new topic
      } else { //if the topic already exists
        if (!sortedQuestions.get(question.getTopic()).contains(question)) //if it isnt a duplicate
          sortedQuestions.get(question.getTopic()).add(question); //add it under the correct topic
      }
      
    }
  }
  
  /**
   * Add a new question to this teacher
   * @param questionText - text of the new question
   * @param choices - all possible answers to this question
   * @param answer - the full correct answer in string form
   * @param topic - the topic for this question
   * @param filePath - the possible file path of a picture
   */
  public void addQuestion(String questionText, ArrayList<String> choices, String answer, String topic,
      String filePath) {
    if (choices.size() < 2 || choices.size() > 5) //checks that there are 2-5 choices
      return;
    //aborts if any of the essential values aren't set
    if (answer == null || answer.equals("") || topic.equals("") || questionText.equals(""))
      return;
    //create new Question and add it to list of questions
    unsortedQuestions.add(new Question(questionText, choices, answer, topic, filePath));
    //sort questions into topics
    sortQuestions();
  }
  
  /**
   * Save all questions loaded to a different JSON file.
   * @param fileName - new JSON file to be created.
   */
  @SuppressWarnings("unchecked")
  public void saveQuestions(String fileName) {
    JSONObject outer = new JSONObject(); //creating JSONObject
    JSONArray questionArray = new JSONArray(); //holds all question objects
    JSONObject questionObject; //holds all question information
    JSONArray choiceArray;
    JSONObject choiceObject;
    
    for (Question question : unsortedQuestions) { //cycle through all questions
      questionObject = new JSONObject();
      questionObject.put("meta-data", "unused"); //meta data is always unused
      questionObject.put("questionText", question.getQuestion()); //set question text
      questionObject.put("topic", question.getTopic()); //set topic
      questionObject.put("image", question.imageFile); //set image file path
      
      choiceArray = new JSONArray(); //JSON array of choices
      for (String choice : question.getChoices()) { //cycle through all choices
        choiceObject = new JSONObject(); //make an object for each choice
        if (choice.equals(question.getAnswer())) //mark if it's the correct answer
          choiceObject.put("isCorrect", "T");
        else
          choiceObject.put("isCorrect", "F");
        choiceObject.put("choice", choice); //add the actual choice itself
        choiceArray.add(choiceObject); //add this object to the array
      }
      
      questionObject.put("choiceArray", choiceArray); //add choiceArray to the question object
      questionArray.add(questionObject); //add question object to the array of questions
    }
    outer.put("questionArray", questionArray); //add array of questions to outer object
    
    // writing JSON to filePath 
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(fileName);
    } catch (FileNotFoundException e) {/* do nothing */} 
    pw.write(outer.toJSONString()); 
    
    pw.flush(); 
    pw.close(); 
  }
  
  /**
   * Create a quiz based on certain topics and a number of desired questions.
   * @param topics - selected topics to create quiz from
   * @param numQuestions - number of questions to put on quiz
   * @return the quiz just made
   */
  public Quiz makeQuiz(ArrayList<String> topics, int numQuestions) {
	  ArrayList<Question> questionSelection = new ArrayList<Question>(); //holds all applicable Qs
	  ArrayList<Question> questionsForQuiz = new ArrayList<Question>(); //holds selected Qs
	  Random rand = new Random(); //random number generator
	  
	  for(String topic : topics)
	    questionSelection.addAll(sortedQuestions.get(topic)); //add questions of selected topics
	  
	  for (int i = 0; i < numQuestions; i++) { //randomly select questions from applicable
	    if (questionSelection.size() == 0) break;
	    int randomInt = rand.nextInt(questionSelection.size());
	    questionsForQuiz.add(questionSelection.get(randomInt));
	    questionSelection.remove(randomInt);
	  }
	  
	  Quiz result = new Quiz(questionsForQuiz); //build quiz from selected questions
	  return result;
  }
}

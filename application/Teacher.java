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

public class Teacher {
  protected Map<String, ArrayList<Question>> sortedQuestions;
  protected ArrayList<Question> unsortedQuestions;
  
  public Teacher() {
    sortedQuestions = new TreeMap<String, ArrayList<Question>>();
    unsortedQuestions = new ArrayList<Question>();
  }
  
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
  
  private void sortQuestions() {
    for(Question question : unsortedQuestions) { //iterates through all questions imported
      
      if(!sortedQuestions.containsKey(question.getTopic())) { //if it's a new topic
        ArrayList<Question> newTopic = new ArrayList<>(Arrays.asList(question));
        sortedQuestions.put(question.getTopic(), newTopic); //add new topic
      } else { //if the topic already exists
        sortedQuestions.get(question.getTopic()).add(question); //add it under the correct topic
      }
      
    }
  }
  
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
  
  public Quiz makeQuiz(String[] topics, int numQuestions) {
	  ArrayList<Question> questions = new ArrayList<Question>();
	  ArrayList<Question> allQuestions = new ArrayList<Question>();
	  Set<String> topicKeys = sortedQuestions.keySet();
	  //find and store all questions of selected topics
	  for (String topic : topics) {
		  for (String topicKey : topicKeys) {
			  if (topic.compareTo(topicKey) == 0){
				  for (Question question : sortedQuestions.get(topicKey)) {
					  allQuestions.add(question);
				  }
			  }
		  }
	  }
	  //randomly choose desired number of questions from all questions of selected topic

	  Random rand = new Random();
	  
	  for (int i = 0; i < numQuestions; i++) {
	        int currentIndex = rand.nextInt(allQuestions.size());
	        Question currentQuestion = allQuestions.get(currentIndex);
	        allQuestions.remove(currentIndex);
	        questions.add(currentQuestion);
	  }
	  
	  Quiz newQuiz = new Quiz(questions);
	  return newQuiz;
  }
}

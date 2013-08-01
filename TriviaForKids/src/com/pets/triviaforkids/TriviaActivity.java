package com.pets.triviaforkids;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.triviaforkids.R;

public class TriviaActivity extends Activity {
    
    private final Random rand = new Random();
    
    private ViewFlipper switcher;
    
    /* listener for main menu button */
    private OnClickListener mainMenuListener;
    
    /* Instance variables for the welcome screen layout. */
    private ImageButton startButton;
	
    /* Instance variables for the trivia screen layout. */
    private QuestionSetContainer questionSet;
    private TextView numberQuestionsAnswered;
	private TextView tfQuestion;
	private Button trueButton;
	private Button falseButton;
	private TFQuestion[] questionBank;
	private boolean[] hasBeenAnswered;
	private int questionsAnswered;
	private int questionBankIndex;
	private int answeredRight;
	
	/* Instance variables for the congratulatory screen layout. */
	private TextView scoreDisplay;
	private Button mainMenuButton;
	
	/*
	 * Checks whether the user input is correct or incorrect.
	 * Also marks the question as answered, displays the number 
	 * of questions answered thus far, and displays a toast that
	 * informs the user of the answer status.
	 */
	private void checkAnswer(boolean userPressedTrue) {
	    int messageResId;
	    
	    if (userPressedTrue == questionBank[questionBankIndex].isTrue()) {
	        messageResId = R.string.correct_toast;
	        markNumQuestionsRight();
	    }
	    else {
	        messageResId = R.string.incorrect_toast;
	    }
	    
	    markAsAnswered();
	    displayNumQuestionsAnswered();
	    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}
	
	/* Displays the next question. */
	private void displayNextQuestion() {
	    /* Set text size. */
	    tfQuestion.setTextSize(30);
	    tfQuestion.setGravity(Gravity.CENTER_HORIZONTAL);
	    
	    /*
	     * If all questions have been answered, pressing the next button shows the
	     * congratulatory screen.  Otherwise, if one question is left, pressing
	     * the next button displays only that last question.  If previous two
	     * conditions are not met, then an unanswered question is chosen at random,
	     * where no question is displayed two times in a row.
	     */
        if (questionsAnswered == questionBank.length) {
            switcher.showNext();
            scoreDisplay.setTextSize(30);
            scoreDisplay.setText("Score: " + answeredRight + "/" + questionBank.length);
            return;
        }
        else if (questionsAnswered == questionBank.length - 1) {
            for (int i = 0; i < questionBank.length; i++) {
                if (!hasBeenAnswered[i]) {
                    questionBankIndex = i;
                    break;
                }
            }
            
            tfQuestion.setText(questionBank[questionBankIndex].getTFQuestion());
            return;
        }

        int compareIndex = questionBankIndex;
        questionBankIndex = rand.nextInt(questionBank.length);
        
        while (hasBeenAnswered[questionBankIndex] || compareIndex == questionBankIndex)
            questionBankIndex = rand.nextInt(questionBank.length);
        
        tfQuestion.setText(questionBank[questionBankIndex].getTFQuestion());
	}
	
	private void markAsAnswered() {
	    if (!hasBeenAnswered[questionBankIndex]) {
	        hasBeenAnswered[questionBankIndex] = true;
	        questionsAnswered++;
	    }
	}
	
	private void markNumQuestionsRight() {
	    if (hasBeenAnswered[questionBankIndex] == false)
            answeredRight++;
	}
	
	private void displayNumQuestionsAnswered() {
	    numberQuestionsAnswered.setGravity(Gravity.CENTER_HORIZONTAL);
	    numberQuestionsAnswered.setTextColor(Color.BLUE);
	    numberQuestionsAnswered.setTextSize(35);
	    numberQuestionsAnswered.setText("Number of questions answered: " + questionsAnswered + "/" + questionBank.length);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trivia);
		
		/* Taking care of everything related to QuestionSetContainer */
		questionSet = new QuestionSetContainer(this);
		questionSet.initializeQuestions();
		questionBank = questionSet.getRandomQuestionSet();
		
		/* Switches between views. */
		switcher = (ViewFlipper)findViewById(R.id.profile_switcher);
		
		/* Implementation for mainMenuListener */
		mainMenuListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                switcher.setDisplayedChild(0);
                questionsAnswered = 0;
                answeredRight = 0;
                questionBank = questionSet.getRandomQuestionSet();
                hasBeenAnswered = new boolean[questionBank.length];
                displayNumQuestionsAnswered();
                questionBankIndex = rand.nextInt(questionBank.length);
                tfQuestion.setText(questionBank[questionBankIndex].getTFQuestion());
            }  
		};
		
		/* Controller portion for welcome screen. */
		startButton = (ImageButton)findViewById(R.id.start_button);
		startButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                switcher.showNext();
            }
        });
		
		
		/* Controller portion for trivia screen. */
		
		/* Keeps track of questions already displayed. */
		hasBeenAnswered = new boolean[questionBank.length];
		
		questionsAnswered = 0; // Number of questions answered.
		answeredRight = 0; // Number of questions answered correctly on first try.
		
		/* Initialize text view that displays number of questions answered. */
		numberQuestionsAnswered = (TextView)findViewById(R.id.number_questions_answered);
		displayNumQuestionsAnswered();
		
		/* Display first, random question */
		tfQuestion = (TextView)findViewById(R.id.tf_question);
		displayNextQuestion();
		
		/* Initialize buttons and set on click events. */
		trueButton = (Button)findViewById(R.id.true_button);
		trueButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                displayNextQuestion();
            }
        });
		
		falseButton = (Button)findViewById(R.id.false_button);
		falseButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                displayNextQuestion();
            }
        });
		
		/* Controller for trivia and congratulatory screen. */
		scoreDisplay = (TextView)findViewById(R.id.score_display);
		
		mainMenuButton = (Button)findViewById(R.id.main_menu);
		mainMenuButton.setOnClickListener(mainMenuListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trivia, menu);
		return true;
	}
	
}

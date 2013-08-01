package com.pets.triviaforkids;

import java.util.Random;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;

import com.example.triviaforkids.R;

public class QuestionSetContainer {
    
    private Context context;
    private final Random rand = new Random();
    private SparseArray<TFQuestion[]> questionSet;
    private boolean[] setUsed;
    private int numSetsUsed;
    
    public QuestionSetContainer(Context context) {
        this.context = context;
        questionSet = new SparseArray<TFQuestion[]>(10);
        numSetsUsed = 0;
    }
    
    public void initializeQuestions() {
        TFQuestion[] questionBank = new TFQuestion[] { new TFQuestion(R.string.sky_color_q, false),
                                                       new TFQuestion(R.string.bird_leg_q, true),
                                                       new TFQuestion(R.string.monday_tuesday_q, false),
                                                       new TFQuestion(R.string.elephant_skin_q, true),
                                                       new TFQuestion(R.string.elmo_color_q, true),
                                                       new TFQuestion(R.string.cookie_monster_color, false),
                                                       new TFQuestion(R.string.oscar_color, true),
                                                       new TFQuestion(R.string.pigs_oink, true),
                                                       new TFQuestion(R.string.cats_woof, false),
                                                       new TFQuestion(R.string.christmas_december, true) };
        questionSet.append(0, questionBank);
        
        questionBank = new TFQuestion[] { new TFQuestion(R.string.x_w, false),
                                          new TFQuestion(R.string.seven_eight, false),
                                          new TFQuestion(R.string.bird_chirp, true),
                                          new TFQuestion(R.string.sheep_baah, true),
                                          new TFQuestion(R.string.car_wheels, false),
                                          new TFQuestion(R.string.bike_wheels, true),
                                          new TFQuestion(R.string.one_plus_one, true),
                                          new TFQuestion(R.string.circle_round, true),
                                          new TFQuestion(R.string.firetruck_yellow, false),
                                          new TFQuestion(R.string.five_toes, true) };
        questionSet.append(1, questionBank);
        
        setUsed = new boolean[questionSet.size()];
    }
    
    public TFQuestion[] getRandomQuestionSet() {
        if (numSetsUsed == questionSet.size()) {
            setUsed = new boolean[questionSet.size()];
            numSetsUsed = 0;
            
            Toast.makeText(context, R.string.all_sets_used, Toast.LENGTH_LONG).show();
        }
        else if (numSetsUsed == questionSet.size() - 1) {
            for (int i = 0; i < setUsed.length; i++)
                if (!setUsed[i]) {
                    setUsed[i] = true;
                    numSetsUsed++;
                    return questionSet.get(i);
                }
        }
        
        int index = rand.nextInt(questionSet.size());
        
        while (setUsed[index])
            index = rand.nextInt(questionSet.size());
        
        setUsed[index] = true;
        numSetsUsed++;
        return questionSet.get(index);
    }
    
    public int size() {
        return questionSet.size();
    }
}

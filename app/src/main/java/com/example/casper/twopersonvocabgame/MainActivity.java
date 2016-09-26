package com.example.casper.twopersonvocabgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The code used for this app is adapted from the Court Counter app by Udacity on Github.
 * <p>
 * This score keeper is intended to be used in a vocabulary game between two persons.
 * In this game both competitors are given a jumbled group of letters.
 * The competitor creates a word with as many letters as possible from the jumbled letters given.
 * Then the number of letters in the word is divided by the number of jumbled letters given.
 * Then, the resulting value is added to the competitors score only if the word exists in the dictionary.
 * If the word does not exist in the dictionary, the resulting value is subtracted from the competitors score.
 */
public class MainActivity extends AppCompatActivity {
    //Tracks the number of letters in the jumbled group presented to the competitors
    int numOfJumbledLetters = 3;

    // Tracks the score for Person A
    int scorePersonA = 0;

    // Tracks the score for Person B
    int scorePersonB = 0;

    ///two spinners
    private Spinner spinnerA;
    private Spinner spinnerB;


    //word length for each person
    int wordLengthA = 0;
    int wordLengthB = 0;


    // switch related
    Switch switchButtonA;
    Switch switchButtonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.word_lengths_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerA = (Spinner) findViewById(R.id.spinner_person_a_word_length);

        // Apply the adapter to the spinnerA
        spinnerA.setAdapter(adapter);
        spinnerA.setOnItemSelectedListener(new CustomOnItemSelectedListenerA());

        spinnerB = (Spinner) findViewById(R.id.spinner_person_b_word_length);
        // Apply the adapter to spinnerB
        spinnerB.setAdapter(adapter);
        spinnerB.setOnItemSelectedListener(new CustomOnItemSelectedListenerB());


        //switch related
        switchButtonA = (Switch) findViewById(R.id.switch_a_button);
        switchButtonA.setChecked(true);
        switchButtonA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

            }
        });


        switchButtonB = (Switch) findViewById(R.id.switch_b_button);
        switchButtonB.setChecked(true);
        switchButtonB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimpSlifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (numOfJumbledLetters == 12) {
            //Show an error message
            Toast.makeText(this, "You can't present the competitor with more than 12 letters!", Toast.LENGTH_SHORT).show();
            //exit this method
            return;
        }
        numOfJumbledLetters = numOfJumbledLetters + 1;
        displayNumOfJumbledLetters(numOfJumbledLetters);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (numOfJumbledLetters == 3) {
            //Show an error message
            Toast.makeText(this, "You can't present the competitor with fewer than 3 letters!", Toast.LENGTH_SHORT).show();
            //exit this method
            return;
        }
        numOfJumbledLetters = numOfJumbledLetters - 1;
        displayNumOfJumbledLetters(numOfJumbledLetters);
    }

    /**
     * This method displays the number of letters in the jumbled group presented to the competitors.
     */
    private void displayNumOfJumbledLetters(int numOfJumbledLetters) {
        TextView numOfJumbledLettersTextView = (TextView) findViewById(
                R.id.num_of_jumbled_letters_text_view);
        numOfJumbledLettersTextView.setText("" + numOfJumbledLetters);
    }


    /**
     * This will create a drop down menu of numbers to pick from to input the length of the assembled word by the competitor
     */


    public class CustomOnItemSelectedListenerA implements AdapterView.OnItemSelectedListener {
        String wordLengthAString = (String) (spinnerA.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (wordLengthAString.equals(String.valueOf(spinnerA.getSelectedItem()))) {
                // ToDo when first item is selected
                wordLengthA = Integer.parseInt(wordLengthAString);
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }

    }

    public class CustomOnItemSelectedListenerB implements AdapterView.OnItemSelectedListener {
        String wordLengthBString = String.valueOf(spinnerB.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            if (wordLengthBString.equals(String.valueOf(spinnerB.getSelectedItem()))) {
                // ToDo when first item is selected
                wordLengthB = Integer.parseInt(wordLengthBString);
            } else {
                Toast.makeText(parent.getContext(),
                        "You have selected : " + parent.getItemAtPosition(pos).toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {
        }
    }


    /**
     * Increase the score for Person A by 1 point.
     */
    public void addOneForPersonA(View v) {
        scorePersonA = scorePersonA + 1;
        displayForPersonA(scorePersonA);
    }


    /**
     * Increase the score for Person B by 1 point.
     */
    public void addOneForPersonB(View v) {
        scorePersonB = scorePersonB + 1;
        displayForPersonB(scorePersonB);
    }


    /**
     * updates the score for both persons back to 0.
     */
    public void updateScore(View v) {
        //calculate for person A
        if (switchButtonA.isChecked()) {
            scorePersonA = (int) (scorePersonA + (((double) wordLengthA / (double) numOfJumbledLetters) * 10));
        } else {
            scorePersonA = (int) (scorePersonA - (((double) wordLengthA / (double) numOfJumbledLetters) * 10));
        }
        //calculate for person B
        if (switchButtonB.isChecked()) {
            scorePersonB = (int) (scorePersonB + (((double) wordLengthB / (double) numOfJumbledLetters) * 10));
        } else {
            scorePersonB = (int) (scorePersonB - (((double) wordLengthB / (double) numOfJumbledLetters) * 10));
        }

        displayForPersonA(scorePersonA);
        displayForPersonB(scorePersonB);
    }


    /**
     * Resets the score for both persons back to 0.
     */
    public void resetScore(View v) {
        scorePersonA = 0;
        scorePersonB = 0;
        displayForPersonA(scorePersonA);
        displayForPersonB(scorePersonB);
    }

    /**
     * Displays the given score for Person A.
     */
    public void displayForPersonA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.person_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the given score for Person B.
     */
    public void displayForPersonB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.person_b_score);
        scoreView.setText(String.valueOf(score));
    }
}

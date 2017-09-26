package com.example.diceout;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Field to hold the roll result text
    TextView rollResult;

    // field to hold the score
    int score;

    //Field to hold random number generator
    Random rand;

    //Fields to hold the dice value
    int die1;
    int die2;
    int die3;
    int die4;

    // Field to hold the score text
    TextView scoreText;

    // Arraylist to hold all dice values
    ArrayList<Integer> dice;

    //Arraylist to hold all 3 dice images
    ArrayList<ImageView> diceImageViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //this is a lambda expression that is new in Java 8
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
                final Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rollDice(view);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };

                anim1.setAnimationListener(animationListener);

                for(ImageView item: diceImageViews) {
                    item.startAnimation(anim1);
                }
            }

        });
        //set initial score
        score = 0;

        //Assign TextViews
        rollResult = (TextView) findViewById(R.id.rollResult);
        scoreText = (TextView) findViewById(R.id.scoreText);

        //Initiate the random number generator
        rand = new Random();

        //Create Arraylsit for the dice value
        dice = new ArrayList<Integer>();

        //Assign dice ImageViews
        ImageView die1image = (ImageView) findViewById(R.id.die1Image);
        ImageView die2image = (ImageView) findViewById(R.id.die2Image);
        ImageView die3image = (ImageView) findViewById(R.id.die3Image);
        ImageView die4image = (ImageView) findViewById(R.id.die4Image);


        //Assign the dice ImageViews into an ArrayList
        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1image);
        diceImageViews.add(die2image);
        diceImageViews.add(die3image);
        diceImageViews.add(die4image);


        //create greeting
        // Toast class??? check it out
        // needs a context, then a string message, then an int duration (length_short is predefined)
        //Toast.makeText(getApplicationContext(), "Welcome to DiceOut!", Toast.LENGTH_SHORT ).show();
    }

    public void rollDice(View v){

        //Roll dice
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;
        die4 = rand.nextInt(6) + 1;


        // Set dice vales into an Arraylist. Clear previous values
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);
        dice.add(die4);


        for(int dieOfSet = 0; dieOfSet <dice.size() ; dieOfSet++){
            String imageName = "die_" + dice.get(dieOfSet) + ".png";
            try {
                //get a stream of data, aka the right png dice image
                InputStream stream = getAssets().open(imageName);
                //make a drawable object from the png dice image
                Drawable d = Drawable.createFromStream(stream, null);
                //from the proper diceImageview, seet the drawable image to d
                diceImageViews.get(dieOfSet).setImageDrawable(d);
            }  catch(IOException e){
                e.printStackTrace();
            }
        }



        //Building message with the result
        String msg;

        //4 choose 3 is 4
        if (    (die1 == die2 && die2 == die3) ||
                (die1 == die2 && die2 == die4) ||
                (die1 == die3 && die3 == die4) ||
                (die2 == die3 && die3 == die4)
            ){
            //Triples
            //int scoreDelta = die1 * 100;

            //which to choose
            int tripleDie = die1;
            if(die1 != die2 && die1 != die3){
                tripleDie = die2;
            }
            msg = "You rolled a triple " + tripleDie + "! You score " + 100 + " points!";
            score += 100;

        //4 choose for 2 is 6
        } else if (die1 == die2 || die1 == die3 || die1 == die4 ||
                    die2 == die3 || die2 == die4 ||
                    die3 == die4){
            msg = "You rolled a doubles for 50 points!";
            score += 50;
        } else {
            msg = "You didn't score this roll. Try again bud!";
        }
        //Update the app to display the result message
        rollResult.setText(msg);

        //Update scoreText
        String updatedScoreText = "Score: " + score;
        scoreText.setText(updatedScoreText);

    }
    /*  #1
        On the xml, theres a method called when the button is clicked. That method is rollDice.
        Here in the java class, we made a Textview Object rollResult and then assigned it to a
        view objected found from the ID "rollResult". This is view is a textview object, but the
        method returns a view so you need to cast it. Same goes for the roll Button. Now we have
        two connected Textview and Button Objects.

        #2
        Widgets are user interface elements. So technically, a Texvtiew is a widget sicne the user
        interacts with it
    */

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

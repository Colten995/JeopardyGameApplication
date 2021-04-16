package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PointsDialogFragment.PointsDialogListener, DailyDoubleDialogFragment.DailyDoubleDialogListener {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button highscores;
    private Button settings;

    private Button buttonPoints1;
    private Button buttonPoints2;
    private Button buttonPoints3;
    private Button buttonPoints4;
    private Button buttonPoints5;
    private Button buttonNewGame;
    private Button buttonDailyDouble;
    private Button buttonDoubleJeopardy;
    private Button buttonBackToFirstRound;
    private Button buttonFinalJeopardy;
    private Button logOut;
    private TextView textViewScore;
    private TextView textViewRoundTitle;
    private TextView textViewScoreLabel;

    private Timer timerForScoreAnimation = null;
    private Bundle dailyDoubleDialogBundle = new Bundle();
    private SharedPreferences sharedPref;

    private int pointsToAddOrSubtract;
    private int score;
    private boolean isDoubleJeopardyRound = false;
    private boolean resetGame = false;
    private int scoreAnimationCounter = 0;

    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final String FINAL_JEOPARDY_RESET_GAME_KEY = "finalJeopardyResetGameFlag";
    private final int DEFAULT_SCORE = 0;
    private final int MAXIMUM_POINTS_FIRST_ROUND = 1000;
    private final int MAXIMUM_POINTS_DOUBLE_JEOPARDY = 2000;
    private final String DAILY_DOUBLE_DIALOG_SCORE_KEY = "Score";
    private final String DIALOG_PARENT_VIEW_TAG = "Main Activity";
    private final String SHARED_PREF_KEY_SCORE = "CurrentScore";
    private final String SHARED_PREF_KEY_IS_DOUBLE_JEOPARDY = "isDoubleJeopardy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate our drawer layout ui reference and make a new toggle button for our navigation drawer menu
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //add our toggle to the activity main drawer layout xml
        drawerLayout.addDrawerListener(toggle);
        //sync the state of open or close to our toggle
        toggle.syncState();
        //we now enable our toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Instantiate drawer buttons and set onClickListeners.
        highscores = (Button) findViewById(R.id.btnHighscores);
        //highscores.setOnClickListener(this);
        settings = (Button) findViewById(R.id.btnSettings);
        settings.setOnClickListener(this);
        //then close the drawer menu once an item has been selected
        drawerLayout.closeDrawers();

        highscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HighScoreActivity.class));
            }
        });

        buttonPoints1 = findViewById(R.id.buttonPoints1);
        buttonPoints2 = findViewById(R.id.buttonPoints2);
        buttonPoints3 = findViewById(R.id.buttonPoints3);
        buttonPoints4 = findViewById(R.id.buttonPoints4);
        buttonPoints5 = findViewById(R.id.buttonPoints5);

        logOut = findViewById(R.id.btnLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        buttonNewGame = findViewById(R.id.buttonNewGame);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doResetGame();
            }
        });

        buttonDailyDouble =findViewById(R.id.buttonDailyDouble);

        buttonDailyDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Put the score in the bundle to send to the dialog
                dailyDoubleDialogBundle.putString(DAILY_DOUBLE_DIALOG_SCORE_KEY, String.valueOf(score));

                DialogFragment dailyDoubleDialog = new DailyDoubleDialogFragment();
                dailyDoubleDialog.setArguments(dailyDoubleDialogBundle);
                dailyDoubleDialog.show(getSupportFragmentManager(), DIALOG_PARENT_VIEW_TAG);
            }
        });

        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);

        buttonDoubleJeopardy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDoubleJeopardyRound();
            }
        });
        
        buttonBackToFirstRound = findViewById(R.id.buttonFirstRound);
        
        buttonBackToFirstRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstRound();
            }
        });

        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);

        buttonFinalJeopardy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalJeopardyIntent = new Intent(getApplicationContext(), FinalJeopardyActivity.class);
                finalJeopardyIntent.putExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, score);
                startActivity(finalJeopardyIntent);
            }
        });

        View.OnClickListener pointsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pointsToAddOrSubtract = Integer.parseInt(((Button) v).getText().toString());

                DialogFragment pointsDialog = new PointsDialogFragment();
                pointsDialog.show(getSupportFragmentManager(), DIALOG_PARENT_VIEW_TAG);
            }
        };

        textViewScore = findViewById(R.id.textViewScore);
        textViewRoundTitle = findViewById(R.id.textViewRoundTitle);
        textViewScoreLabel = findViewById(R.id.textViewScoreLabel);

        buttonPoints1.setOnClickListener(pointsListener);
        buttonPoints2.setOnClickListener(pointsListener);
        buttonPoints3.setOnClickListener(pointsListener);
        buttonPoints4.setOnClickListener(pointsListener);
        buttonPoints5.setOnClickListener(pointsListener);

        if(isDoubleJeopardyRound)
        {
            goToDoubleJeopardyRound();
        }

        //If finish game button was clicked reset the game otherwise populate the score with the final jeopardy score from the final jeopardy acitivity
        Intent finalJeopardyIntent = getIntent();
        resetGame = finalJeopardyIntent.getBooleanExtra(FINAL_JEOPARDY_RESET_GAME_KEY, false);
        if (resetGame)
        {
            doResetGame();
        }
        else
        {
            score = finalJeopardyIntent.getIntExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, DEFAULT_SCORE);
            textViewScore.setText(String.valueOf(score));
        }

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


    }

    /*TODO: Make app retain state when resuming using shared prefs
    TODO: Change intents to shared prefs instead to store the score, double_jeopardy state and reset game flag
     */
    @Override
    protected void onPause() {
        Editor ed = sharedPref.edit();

        ed.putInt(SHARED_PREF_KEY_SCORE, score);
        ed.putBoolean(SHARED_PREF_KEY_IS_DOUBLE_JEOPARDY, isDoubleJeopardyRound);
        ed.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        score = sharedPref.getInt(SHARED_PREF_KEY_SCORE, DEFAULT_SCORE);
        isDoubleJeopardyRound = sharedPref.getBoolean(SHARED_PREF_KEY_IS_DOUBLE_JEOPARDY, false);

        textViewScore.setText(String.valueOf(score));
        if(isDoubleJeopardyRound)
        {
            goToDoubleJeopardyRound();
        }
        //TODO: Re-populate current user
    }

    private void doResetGame() {
        score = 0;
        isDoubleJeopardyRound = false;
        textViewScore.setText(String.valueOf(score));
    }

    //by default a menu item is set to false because it has not been touched and so this method is if user selects an item from the menu, it will return true because an item has been selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(highscores)){
            Intent intent = new Intent(this, HighScoreActivity.class);
            startActivity(intent);
        }
        else if(v.equals(settings)){
        }
    }

    @Override
    public void onPointsDialogYesClick(DialogFragment dialog) {
        score += pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
        animateScore(true);
    }

    @Override
    public void onPointsDialogNoClick(DialogFragment dialog) {
        score -= pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
        animateScore(false);
    }


    @Override
    public void onDailyDoubleDialogYesButtonClick(DialogFragment dialog, int wager) {

        if(validateWager(wager))
        {
            score += 2 * wager;
            textViewScore.setText(String.valueOf(score));
        }

    }

    @Override
    public void onDailyDoubleDialogNoButtonClick(DialogFragment dialog, int wager) {
        score -= 2 * wager;
        textViewScore.setText(String.valueOf(score));
    }

    public void goToDoubleJeopardyRound()
    {
        buttonDoubleJeopardy.setVisibility(View.GONE);
        buttonBackToFirstRound.setVisibility(View.VISIBLE);
        isDoubleJeopardyRound = true;

        buttonPoints1.setText(R.string.points_400);
        buttonPoints2.setText(R.string.points_800);
        buttonPoints3.setText(R.string.points_1200);
        buttonPoints4.setText(R.string.points_1600);
        buttonPoints5.setText(R.string.points_2000);

        textViewRoundTitle.setText(R.string.double_jeopardy);
    }
    public void goToFirstRound()
    {
        buttonBackToFirstRound.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        isDoubleJeopardyRound = false;

        buttonPoints1.setText(R.string.points_200);
        buttonPoints2.setText(R.string.points_400);
        buttonPoints3.setText(R.string.points_600);
        buttonPoints4.setText(R.string.points_800);
        buttonPoints5.setText(R.string.points_1000);

        textViewRoundTitle.setText(R.string.first_round_title);
    }
    public boolean validateWager(int wager)
    {
        int maximumWager = calculateDailyDoubleMaxWager();
        boolean isValidWager = true;
        View mainView = findViewById(R.id.mainLayout);

        //Check if wager is entered
        if(wager != 0)
        {
            //check if wager is negative
            if(wager > 0)
            {
                if (score == 0)
                {
                    if (wager > maximumWager)
                    {
                        Snackbar.make(mainView, getString(R.string.wager_greater_than_maximum_amount_allowed) + maximumWager, Snackbar.LENGTH_LONG).show();
                        isValidWager = false;
                    }
                }
                else if (wager > maximumWager)
                {
                    Snackbar.make(mainView, getString(R.string.wager_greater_than_maximum_amount_allowed) + maximumWager, Snackbar.LENGTH_LONG).show();
                    isValidWager = false;
                }
            }
            else
            {
                Snackbar.make(mainView, R.string.wager_not_positive, Snackbar.LENGTH_LONG).show();
                isValidWager = false;
            }
        }
        else
        {
            Snackbar.make(mainView, R.string.please_enter_a_wager, Snackbar.LENGTH_LONG).show();
            isValidWager = false;
        }

        return isValidWager;
    }
    public int calculateDailyDoubleMaxWager()
    {
        int maximumWager = 0;
        if (isDoubleJeopardyRound) {
            //assign maximum wager to 2000 if the score is less than 2000
            maximumWager = (score < MAXIMUM_POINTS_DOUBLE_JEOPARDY) ? 2000 : score;
        } else {
            //assign maximum wager to 1000 if the score is less than 1000
            maximumWager = (score < MAXIMUM_POINTS_FIRST_ROUND) ? 1000 : score;
        }
        return maximumWager;
    }
    public void animateScore(boolean isCorrect)
    {
        textViewScore.setTextColor((isCorrect) ? getColor(R.color.green) : getColor(R.color.red));
        textViewScoreLabel.setTextColor((isCorrect) ? getColor(R.color.green) : getColor(R.color.red));

        //Every half second animate the score text to make it blink
        if(timerForScoreAnimation != null)
        {
            timerForScoreAnimation.cancel();
        }
        timerForScoreAnimation = new Timer(true);
        timerForScoreAnimation.schedule(new TimerTask() {
            @Override
            public void run() {
                if(scoreAnimationCounter < 3)
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewScore.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    textViewScore.animate().alpha(1f).setDuration(250);
                                }
                            });
                            textViewScoreLabel.animate().alpha(0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    textViewScoreLabel.animate().alpha(1f).setDuration(250);
                                }
                            });

                        }
                    });
                    scoreAnimationCounter++;
                }
                else
                {
                    scoreAnimationCounter = 0;
                    timerForScoreAnimation.cancel();
                    timerForScoreAnimation = null;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewScore.setTextColor(getColor(R.color.black));
                            textViewScoreLabel.setTextColor(getColor(R.color.black));
                        }
                    });
                }

            }
        }, 0, 750);
    }
}
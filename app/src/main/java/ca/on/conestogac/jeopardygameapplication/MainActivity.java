package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
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
    private TextView textViewScore;
    private TextView textViewRoundTitle;
    private TextView textViewScoreLabel;

    private Timer timerForScoreAnimation = null;
    private Timer timerForScoreBlinking = null;
    private Bundle dailyDoubleDialogBundle = new Bundle();

    private int pointsToAddOrSubtract;
    private int score;
    private boolean isDoubleJeopardyRound = false;
    private int scoreAnimationCounter = 0;

    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final int MAXIMUM_POINTS_FIRST_ROUND = 1000;
    private final int MAXIMUM_POINTS_DOUBLE_JEOPARDY = 2000;
    private final String DAILY_DOUBLE_DIALOG_SCORE_KEY = "Score";
    private final String DIALOG_PARENT_VIEW_TAG = "Main Activity";
    private final int THREE_SEC_IN_MILLIS = 3000;
    private final int HALF_SEC_IN_MILLIS = 500;
    private final int QUARTER_SEC_IN_MILLIS = 250;

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
        highscores.setOnClickListener(this);
        settings = (Button) findViewById(R.id.btnSettings);
        settings.setOnClickListener(this);
        //then close the drawer menu once an item has been selected
        drawerLayout.closeDrawers();

        buttonPoints1 = findViewById(R.id.buttonPoints1);
        buttonPoints2 = findViewById(R.id.buttonPoints2);
        buttonPoints3 = findViewById(R.id.buttonPoints3);
        buttonPoints4 = findViewById(R.id.buttonPoints4);
        buttonPoints5 = findViewById(R.id.buttonPoints5);

        buttonNewGame = findViewById(R.id.buttonNewGame);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                textViewScore.setText(String.valueOf(score));
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

                    /*switch (v.getId()) {
                        case R.id.buttonPoints1:
                            pointsToAddOrSubtract = 200;
                            break;
                        case R.id.buttonPoints2:
                            pointsToAddOrSubtract = 400;
                            break;
                        case R.id.buttonPoints3:
                            pointsToAddOrSubtract = 600;
                            break;
                        case R.id.buttonPoints4:
                            pointsToAddOrSubtract = 800;
                            break;
                        case R.id.buttonPoints5:
                            pointsToAddOrSubtract = 1000;
                            break;
                        default:
                            break;
                    }*/

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
                //check if wager is greater than maximumWager or wager is greater than score
                /*if (score == 0 && wager > maximumWager) {
                    Snackbar.make(mainView, getString(R.string.wager_greater_than_maximum_amount_allowed) + maximumWager, Snackbar.LENGTH_LONG).show();
                    isValidWager = false;
                }
                else if (wager > maximumWager) {
                    Snackbar.make(mainView, getString(R.string.wager_greater_than_maximum_amount_allowed) + maximumWager, Snackbar.LENGTH_LONG).show();
                    isValidWager = false;
                }*/
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
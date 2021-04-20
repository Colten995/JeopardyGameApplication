package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class FinalJeopardyActivity extends AppCompatActivity {

    private int score;
    private final String SHARED_PREF_KEY_RESET_GAME = "finalJeopardyResetGameFlag";
    private final String SHARED_PREF_KEY_SCORE = "CurrentScore";
    private final int DEFAULT_SCORE = 0;
    private String username;
    private int user_id;
    private int scoreAnimationCounter = 0;

    private TextView textViewScore;
    private TextView textViewCurrentUser;
    private TextView textViewScoreLabel;
    private EditText editTextFinalJeopardyWager;
    private Button buttonFirstRound;
    private Button buttonDoubleJeopardy;
    private Button buttonFinalJeopardy;
    private Button buttonYes;
    private Button buttonNo;
    private Button buttonFinishGame;
    private FloatingActionButton floatingActionButtonStartTimer;
    private SharedPreferences sharedPref;
    private Timer timerForScoreAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_jeopardy);

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);

        textViewScore = findViewById(R.id.textViewScore);
        textViewCurrentUser = findViewById(R.id.textViewCurrentUser);
        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);
        buttonFirstRound = findViewById(R.id.buttonFirstRound);
        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);
        buttonFinishGame = findViewById(R.id.buttonFinishGame);
        floatingActionButtonStartTimer = findViewById(R.id.floatingActionButtonStartTimer);
        editTextFinalJeopardyWager = findViewById(R.id.editTextFinalJeopardyWager);
        textViewScoreLabel = findViewById(R.id.textViewScoreLabel);

        textViewScore.setText(String.valueOf(score));
        buttonFinalJeopardy.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        buttonFirstRound.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        floatingActionButtonStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), FinalJeopardyTimerService.class));
            }
        });

        View.OnClickListener buttonYesNoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFinalJeopardyWager()) {
                    if (v.getId() == R.id.buttonYes)
                    {
                        addWagerToScore();
                        animateScore(true);
                    }
                    else
                    {
                        subtractWagerFromScore();
                        animateScore(false);
                    }
                }
            }
        };

        View.OnClickListener buttonToFirstAndSecondRoundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainActivityIntent);
            }

        };

        buttonFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((JeopardyApplication)getApplication()).addGame(score, user_id, username);

                Editor ed = sharedPref.edit();
                ed.putBoolean(SHARED_PREF_KEY_RESET_GAME, true);
                ed.commit();

                startActivity(mainActivityIntent);
            }
        });

        buttonYes.setOnClickListener(buttonYesNoListener);
        buttonNo.setOnClickListener(buttonYesNoListener);
        buttonDoubleJeopardy.setOnClickListener(buttonToFirstAndSecondRoundListener);
        buttonFirstRound.setOnClickListener(buttonToFirstAndSecondRoundListener);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        username = sharedPref.getString("userName", "");
        user_id = sharedPref.getInt("userId", 0);
        score = sharedPref.getInt(SHARED_PREF_KEY_SCORE, DEFAULT_SCORE);

        textViewCurrentUser.setText(username);
        
    }

    @Override
    protected void onPause() {
        Editor ed = sharedPref.edit();

        ed.putInt(SHARED_PREF_KEY_SCORE, score);
        ed.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        score = sharedPref.getInt(SHARED_PREF_KEY_SCORE, DEFAULT_SCORE);
        username = sharedPref.getString("userName", "");
        user_id = sharedPref.getInt("userId", 0);

        textViewCurrentUser.setText(username);
        textViewScore.setText(String.valueOf(score));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean ret = true;

        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                //if you didn't handle the behavior, like above, just return this
                ret = super.onOptionsItemSelected(item);
                break;
        }
        return ret;
    }

    //Validate the final jeopardy wager to make sure it isn't greater than the score
    public boolean validateFinalJeopardyWager()
    {
        boolean isValidWager;
        if (editTextFinalJeopardyWager.getText().toString().isEmpty()) {
           Snackbar.make(findViewById(R.id.mainFinalJeopardyLayout), getString(R.string.please_enter_a_wager), Snackbar.LENGTH_LONG).show();
           isValidWager = false;
        }
        else
        {
            int wager = Integer.parseInt(editTextFinalJeopardyWager.getText().toString());
            if (wager > score) {
                Snackbar.make(findViewById(R.id.mainFinalJeopardyLayout), getString(R.string.wager_greater_than_maximum_amount_allowed) + score, Snackbar.LENGTH_LONG).show();
                isValidWager = false;
            } else {
                isValidWager = true;
            }
        }

        return isValidWager;
    }

    public void addWagerToScore()
    {
        int finalJeopardyWager = Integer.parseInt(editTextFinalJeopardyWager.getText().toString());
        score += finalJeopardyWager;
        textViewScore.setText(String.valueOf(score));
    }

    public void subtractWagerFromScore()
    {
        int finalJeopardyWager = Integer.parseInt(editTextFinalJeopardyWager.getText().toString());
        score -= finalJeopardyWager;
        textViewScore.setText(String.valueOf(score));
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

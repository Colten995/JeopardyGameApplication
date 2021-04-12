package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class FinalJeopardyActivity extends AppCompatActivity {

    private int score;
    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final String FINAL_JEOPARDY_RESET_GAME_KEY = "finalJeopardyResetGameFlag";
    private final int DEFAULT_SCORE = 0;

    private TextView textViewScore;
    private EditText editTextFinalJeopardyWager;
    private Button buttonFirstRound;
    private Button buttonDoubleJeopardy;
    private Button buttonFinalJeopardy;
    private Button buttonYes;
    private Button buttonNo;
    private Button buttonFinishGame;
    private FloatingActionButton floatingActionButtonStartTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_jeopardy);

        Intent finalJeopardyIntent = getIntent();
        score = finalJeopardyIntent.getIntExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, DEFAULT_SCORE);

        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);

        textViewScore = findViewById(R.id.textViewScore);
        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);
        buttonFirstRound = findViewById(R.id.buttonFirstRound);
        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);
        buttonFinishGame = findViewById(R.id.buttonFinishGame);
        floatingActionButtonStartTimer = findViewById(R.id.floatingActionButtonStartTimer);
        editTextFinalJeopardyWager = findViewById(R.id.editTextFinalJeopardyWager);

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
                if (v.getId() == R.id.buttonYes && validateFinalJeopardyWager())
                {
                    addWagerToScore();
                }
            }
        };

        View.OnClickListener buttonToFirstAndSecondRoundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivityIntent.putExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, score);
                startActivity(mainActivityIntent);
            }

        };

        buttonFinishGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Use Application class to save the score and user name to the database
                mainActivityIntent.putExtra(FINAL_JEOPARDY_RESET_GAME_KEY, true);
                startActivity(mainActivityIntent);
            }
        });

        buttonYes.setOnClickListener(buttonYesNoListener);
        buttonNo.setOnClickListener(buttonYesNoListener);
        buttonDoubleJeopardy.setOnClickListener(buttonToFirstAndSecondRoundListener);
        buttonFirstRound.setOnClickListener(buttonToFirstAndSecondRoundListener);
        
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
}
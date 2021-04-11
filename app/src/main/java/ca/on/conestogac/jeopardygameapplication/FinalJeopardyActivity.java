package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class FinalJeopardyActivity extends AppCompatActivity {

    private int score;
    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final int DEFAULT_SCORE = 0;

    private TextView textViewScore;
    private EditText editTextFinalJeopardyWager;
    private Button buttonFirstRound;
    private Button buttonDoubleJeopardy;
    private Button buttonFinalJeopardy;
    private Button buttonYes;
    private Button buttonNo;
    private FloatingActionButton floatingActionButtonStartTimer;
    private LinearLayout linearLayoutWasAnswerCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_jeopardy);

        Intent finalJeopardyIntent = getIntent();
        score = finalJeopardyIntent.getIntExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, DEFAULT_SCORE);

        textViewScore = findViewById(R.id.textViewScore);
        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);
        buttonFirstRound = findViewById(R.id.buttonFirstRound);
        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);
        floatingActionButtonStartTimer = findViewById(R.id.floatingActionButtonStartTimer);
        linearLayoutWasAnswerCorrect = findViewById(R.id.linearLayoutWasAnswerCorrect);
        editTextFinalJeopardyWager = findViewById(R.id.editTextFinalJeopardyWager);

        textViewScore.setText(String.valueOf(score));
        buttonFinalJeopardy.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        buttonFirstRound.setVisibility(View.VISIBLE);

        floatingActionButtonStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFinalJeopardyWager())
                {
                    startService(new Intent(getApplicationContext(), FinalJeopardyTimerService.class));
                }
                else
                {
                    Snackbar.make(findViewById(R.id.mainFinalJeopardyLayout), getString(R.string.wager_greater_than_maximum_amount_allowed) + score, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        View.OnClickListener buttonYesNoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonYes)
                {
                    addWagerToScore();
                }
                linearLayoutWasAnswerCorrect.setVisibility(View.GONE);
                editTextFinalJeopardyWager.setEnabled(true);
            }
        };

        buttonYes.setOnClickListener(buttonYesNoListener);
        buttonNo.setOnClickListener(buttonYesNoListener);

        View.OnClickListener buttonToFirstAndSecondRoundListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                mainActivityIntent.putExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, score);
                startActivity(mainActivityIntent);
            }

        };

        buttonDoubleJeopardy.setOnClickListener(buttonToFirstAndSecondRoundListener);
        buttonFirstRound.setOnClickListener(buttonToFirstAndSecondRoundListener);
        
    }

    //Validate the final jeopardy wager to make sure it isn't greater than the score
    public boolean validateFinalJeopardyWager()
    {
        boolean isValidWager;
        int wager = Integer.parseInt(editTextFinalJeopardyWager.getText().toString());
        if(wager > score)
        {
            isValidWager = false;
        }
        else
        {
            isValidWager = true;
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
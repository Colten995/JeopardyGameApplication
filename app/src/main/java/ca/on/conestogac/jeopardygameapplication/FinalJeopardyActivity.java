package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class FinalJeopardyActivity extends AppCompatActivity {

    private int score;
    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final int DEFAULT_SCORE = 0;

    private TextView textViewScore;
    private EditText editTextFinalJeopardyWager;
    private Button buttonFirstRound;
    private Button buttonDoubleJeopardy;
    private Button buttonFinalJeopardy;
    private Button buttonStartTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_jeopardy);
        Intent finalJeopardyIntent = getIntent();
        score = finalJeopardyIntent.getIntExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, DEFAULT_SCORE);

        textViewScore = findViewById(R.id.textViewScore);

        editTextFinalJeopardyWager = findViewById(R.id.editTextFinalJeopardyWager);
        textViewScore.setText(String.valueOf(score));

        buttonFirstRound = findViewById(R.id.buttonFirstRound);
        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);
        buttonStartTimer = findViewById(R.id.buttonStartFinalJeopardyTimer);

        buttonFinalJeopardy.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        buttonFirstRound.setVisibility(View.VISIBLE);

        buttonStartTimer.setOnClickListener(new View.OnClickListener() {
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
}
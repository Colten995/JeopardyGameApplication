package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalJeopardyActivity extends AppCompatActivity {

    private int score;
    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";
    private final int DEFAULT_SCORE = 0;

    private TextView textViewScore;
    private Button buttonFirstRound;
    private Button buttonDoubleJeopardy;
    private Button buttonFinalJeopardy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_jeopardy);
        Intent finalJeopardyIntent = getIntent();
        score = finalJeopardyIntent.getIntExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, DEFAULT_SCORE);

        textViewScore = findViewById(R.id.textViewScore);
        textViewScore.setText(String.valueOf(score));

        buttonFirstRound = findViewById(R.id.buttonFirstRound);
        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);

        buttonFinalJeopardy.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        buttonFirstRound.setVisibility(View.VISIBLE);


    }
}
package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonPoints200;
    private Button buttonPoints400;
    private Button buttonPoints600;
    private Button buttonPoints800;
    private Button buttonPoints1000;

    private Button buttonNewGame;
    private Button buttonDailyDouble;

    private TextView textViewScore;
    private Button buttonSecondRound;
    private Button buttonBackToFirstRound;
    private Button buttonFinalJeopardy;

    private int totalScore;
    private final int DEFAULT_SCORE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        View.OnClickListener pointsListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.buttonPoints200:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints400:
                        totalScore += 400;
                        break;
                    case R.id.buttonPoints600:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints800:
                        totalScore += 200;
                        break;
                    case R.id.buttonPoints1000:
                        totalScore += 200;
                        break;
                    default:
                        break;
                }

                textViewScore.setText(String.valueOf(totalScore));
            }
        };

        View.OnClickListener newGameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalScore = DEFAULT_SCORE;
                textViewScore.setText(String.valueOf(totalScore));
            }
        };

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPoints200 =findViewById(R.id.buttonPoints200);
        buttonPoints400 = findViewById(R.id.buttonPoints400);
        buttonPoints600 = findViewById(R.id.buttonPoints600);
        buttonPoints800 = findViewById(R.id.buttonPoints800);
        buttonPoints1000 = findViewById(R.id.buttonPoints1000);

        buttonNewGame = findViewById(R.id.buttonNewGame);
        buttonDailyDouble = findViewById(R.id.buttonDailyDouble);

        textViewScore = findViewById(R.id.textViewScore);
        buttonBackToFirstRound =findViewById(R.id.buttonFirstRound);
        buttonSecondRound = findViewById(R.id.buttonSecondRound);
        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);

        buttonPoints200.setOnClickListener(pointsListener);
        buttonPoints400.setOnClickListener(pointsListener);
        buttonPoints600.setOnClickListener(pointsListener);
        buttonPoints800.setOnClickListener(pointsListener);
        buttonPoints1000.setOnClickListener(pointsListener);

        buttonNewGame.setOnClickListener(newGameListener);
    }
}
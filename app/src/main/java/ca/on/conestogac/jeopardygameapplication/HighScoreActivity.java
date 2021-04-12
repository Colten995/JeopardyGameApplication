package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    TextView textViewHighScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        textViewHighScores = findViewById(R.id.textViewHighScores);
        refreshHighScores();
    }

    private void refreshHighScores() {
        //Display all high scores in the table
        int highScores = ((JeopardyApplication)getApplication()).getHighScores();
        textViewHighScores.setText(String.valueOf(highScores));
    }
}
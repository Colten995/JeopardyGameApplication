package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends AppCompatActivity {

    TextView textViewHighScores;
    Button back;
    ScoreDatabaseHelper scoreDatabaseHelper;
    TextView name;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        back = findViewById(R.id.btnBack);
        name = findViewById(R.id.txtName);
        score = findViewById(R.id.txtScore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        scoreDatabaseHelper = new ScoreDatabaseHelper(this);
//        ShowScores();
    }


    private void ShowScores() {
        Cursor cursor = scoreDatabaseHelper.GetScore();
        HighscoreRecord highscoreRecord;

        String names = "";
        String scores = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    highscoreRecord = new HighscoreRecord();
                    highscoreRecord.setId(cursor.getInt(cursor.getColumnIndex("userId")));
                    highscoreRecord.setUserName(cursor.getString(cursor.getColumnIndex("name")));
                    highscoreRecord.setScore(cursor.getInt(cursor.getColumnIndex("score")));
                    names += highscoreRecord.getUserName() + "\n";
                    scores += highscoreRecord.getScore() + "\n";

                } while (cursor.moveToNext());

                name.setText(names);
                score.setText(scores);
            }
        }

    }
    private void refreshHighScores() {
        //Display all high scores in the table
        int highScores = ((JeopardyApplication)getApplication()).getHighScores();
        textViewHighScores.setText(String.valueOf(highScores));
    }
}
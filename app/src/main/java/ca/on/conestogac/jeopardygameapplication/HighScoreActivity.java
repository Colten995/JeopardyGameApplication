package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HighScoreActivity extends AppCompatActivity {

    TextView textViewHighScores;
    //Button back;
    ScoreDatabaseHelper scoreDatabaseHelper;
    TextView name;
    TextView score;

    TableLayout highScoreTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        //back = findViewById(R.id.btnBack);
        /*name = findViewById(R.id.txtName);
        score = findViewById(R.id.txtScore);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        scoreDatabaseHelper = new ScoreDatabaseHelper(this);
//        ShowScores();*/

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        highScoreTableLayout = findViewById(R.id.tableLayoutHighScores);
        refreshHighScores();
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
        Cursor highScoreCursor = ((JeopardyApplication)getApplication()).getHighScores();
        int numberOfTableRows = highScoreCursor.getCount();

        TextView textViewUsername = new TextView(this);
        TextView textViewScore = new TextView(this);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        /*TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );*/
        textViewUsername.setLayoutParams(textViewParams);
        textViewScore.setLayoutParams(textViewParams);

        highScoreCursor.moveToFirst();
        for(int i = 0; i < numberOfTableRows; i++)
        {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams());
            textViewUsername.setText(highScoreCursor.getString(1));
            textViewScore.setText(String.valueOf(highScoreCursor.getInt(2)));
            tableRow.addView(textViewUsername);
            tableRow.addView(textViewScore);
            highScoreTableLayout.addView(tableRow);
            highScoreCursor.moveToNext();
        }

        highScoreCursor.close();

    }
}
package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import org.w3c.dom.Text;

public class HighScoreActivity extends AppCompatActivity {

    private TextView textViewHighScores;
    //Button back;
    ScoreDatabaseHelper scoreDatabaseHelper;
    TextView name;
    TextView score;
    private Button buttonResetAllScores;

    private TableLayout highScoreTableLayout;
    private LinearLayout linearLayoutHighScores;
    private TextView textViewNoHighScores;
    private SharedPreferences sharedPref;

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

        buttonResetAllScores = findViewById(R.id.buttonResetScores);
        highScoreTableLayout = findViewById(R.id.tableLayoutHighScores);
        linearLayoutHighScores = findViewById(R.id.linearLayoutHighScores);
        textViewNoHighScores = findViewById(R.id.textViewNoHighScores);
        name = findViewById(R.id.textViewUsernameLabel);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPref.getString("userName", "Username");
        name.setText(userName);
        buttonResetAllScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JeopardyApplication)getApplication()).resetTableScores();
                refreshHighScores();
            }
        });

        //refreshHighScores();
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
        TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        /*TableRow.LayoutParams rowParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );*/
        textViewUsername.setLayoutParams(textViewParams);
        textViewScore.setLayoutParams(textViewParams);

        highScoreCursor.moveToFirst();
        //If there are no scores simply add an empty row
        if (highScoreCursor.getCount() == 0)
        {
            highScoreTableLayout.setVisibility(View.GONE);
            textViewNoHighScores.setVisibility(View.VISIBLE);
        }
        else {
            textViewNoHighScores.setVisibility(View.GONE);
            for (int i = 0; i < numberOfTableRows; i++) {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams());
                textViewUsername.setText(highScoreCursor.getString(1));
                textViewScore.setText(String.valueOf(highScoreCursor.getInt(2)));
                tableRow.addView(textViewUsername);
                tableRow.addView(textViewScore);
                tableRow.setBackgroundColor(getColor(R.color.light_grey));
                highScoreTableLayout.addView(tableRow);
                highScoreCursor.moveToNext();
            }
        }

        highScoreCursor.close();

    }
}
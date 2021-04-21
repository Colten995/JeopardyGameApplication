package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.media.RatingCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity {

    private Button buttonResetAllScores;
    private TableLayout highScoreTableLayout;
    private TextView textViewNoHighScores;

    private SharedPreferences sharedPref;

    private final String SHARED_PREF_KEY_DARK_THEME = "darkTheme";
    private boolean setDarkTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        setDarkTheme = sharedPref.getBoolean(SHARED_PREF_KEY_DARK_THEME, false);

        if(setDarkTheme == true)
        {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_high_score);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        buttonResetAllScores = findViewById(R.id.buttonResetScores);
        highScoreTableLayout = findViewById(R.id.tableLayoutHighScores);
        textViewNoHighScores = findViewById(R.id.textViewNoHighScores);

        buttonResetAllScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((JeopardyApplication)getApplication()).resetTableScores();
                refreshHighScores();
            }
        });

        refreshHighScores();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean oldTheme = setDarkTheme;
        setDarkTheme = sharedPref.getBoolean(SHARED_PREF_KEY_DARK_THEME, false);

        //If the theme changes restart the activity
        if(setDarkTheme != oldTheme)
        {
            oldTheme = setDarkTheme;
            finish();
            startActivity(getIntent());
        }
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

    private void refreshHighScores() {
        //Display all high scores in the table
        Cursor highScoreCursor = ((JeopardyApplication)getApplication()).getHighScores();
        int numberOfTableRows = highScoreCursor.getCount();

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
                TextView textViewUsername = new TextView(this);
                TextView textViewScore = new TextView(this);
                TableRow.LayoutParams textViewParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                textViewUsername.setLayoutParams(textViewParams);
                textViewScore.setLayoutParams(textViewParams);
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams());
                textViewUsername.setText(highScoreCursor.getString(1));
                textViewScore.setText(String.valueOf(highScoreCursor.getInt(2)));
                tableRow.addView(textViewUsername);
                tableRow.addView(textViewScore);
                tableRow.setBackgroundColor(getColor(R.color.colorLightBlack));
                highScoreTableLayout.addView(tableRow);
                highScoreCursor.moveToNext();
            }
        }

        highScoreCursor.close();

    }
}
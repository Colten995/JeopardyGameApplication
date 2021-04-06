package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PointsDialogFragment.PointsDialogListener, DailyDoubleDialogFragment.DailyDoubleDialogListener {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button highscores;
    private Button settings;

    private Button buttonPoints1;
    private Button buttonPoints2;
    private Button buttonPoints3;
    private Button buttonPoints4;
    private Button buttonPoints5;
    private Button buttonNewGame;
    private Button buttonDailyDouble;
    private Button buttonDoubleJeopardy;
    private Button buttonBackToFirstRound;
    private Button buttonFinalJeopardy;
    private TextView textViewScore;
    private TextView textViewRoundTitle;

    private int pointsToAddOrSubtract;
    private int score;
    private boolean isDoubleJeopardyRound = false;
    private final String FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY = "finalJeopardyScoreData";

    private final String DIALOG_PARENT_VIEW_TAG = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instantiate our drawer layout ui reference and make a new toggle button for our navigation drawer menu
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //add our toggle to the activity main drawer layout xml
        drawerLayout.addDrawerListener(toggle);
        //sync the state of open or close to our toggle
        toggle.syncState();
        //we now enable our toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Instantiate drawer buttons and set onClickListeners.
        highscores = (Button) findViewById(R.id.btnHighscores);
        highscores.setOnClickListener(this);
        settings = (Button) findViewById(R.id.btnSettings);
        settings.setOnClickListener(this);
        //then close the drawer menu once an item has been selected
        drawerLayout.closeDrawers();

        buttonPoints1 = findViewById(R.id.buttonPoints1);
        buttonPoints2 = findViewById(R.id.buttonPoints2);
        buttonPoints3 = findViewById(R.id.buttonPoints3);
        buttonPoints4 = findViewById(R.id.buttonPoints4);
        buttonPoints5 = findViewById(R.id.buttonPoints5);

        buttonNewGame = findViewById(R.id.buttonNewGame);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                textViewScore.setText(String.valueOf(score));
            }
        });

        buttonDailyDouble =findViewById(R.id.buttonDailyDouble);

        buttonDailyDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dailyDoubleDialog = new DailyDoubleDialogFragment();
                dailyDoubleDialog.show(getSupportFragmentManager(), DIALOG_PARENT_VIEW_TAG);
            }
        });

        buttonDoubleJeopardy = findViewById(R.id.buttonSecondRound);

        buttonDoubleJeopardy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDoubleJeopardyRound();
            }
        });
        
        buttonBackToFirstRound = findViewById(R.id.buttonFirstRound);
        
        buttonBackToFirstRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFirstRound();
            }
        });

        buttonFinalJeopardy = findViewById(R.id.buttonFinalRound);

        buttonFinalJeopardy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalJeopardyIntent = new Intent(getApplicationContext(), FinalJeopardyActivity.class);
                finalJeopardyIntent.putExtra(FINAL_JEOPARDY_INTENT_SCORE_DATA_KEY, score);
                startActivity(finalJeopardyIntent);
            }
        });

        View.OnClickListener pointsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    pointsToAddOrSubtract = Integer.parseInt(((Button) v).getText().toString());

                    /*switch (v.getId()) {
                        case R.id.buttonPoints1:
                            pointsToAddOrSubtract = 200;
                            break;
                        case R.id.buttonPoints2:
                            pointsToAddOrSubtract = 400;
                            break;
                        case R.id.buttonPoints3:
                            pointsToAddOrSubtract = 600;
                            break;
                        case R.id.buttonPoints4:
                            pointsToAddOrSubtract = 800;
                            break;
                        case R.id.buttonPoints5:
                            pointsToAddOrSubtract = 1000;
                            break;
                        default:
                            break;
                    }*/

                DialogFragment pointsDialog = new PointsDialogFragment();
                pointsDialog.show(getSupportFragmentManager(), DIALOG_PARENT_VIEW_TAG);
            }
        };

        textViewScore = findViewById(R.id.textViewScore);
        textViewRoundTitle = findViewById(R.id.textViewRoundTitle);

        buttonPoints1.setOnClickListener(pointsListener);
        buttonPoints2.setOnClickListener(pointsListener);
        buttonPoints3.setOnClickListener(pointsListener);
        buttonPoints4.setOnClickListener(pointsListener);
        buttonPoints5.setOnClickListener(pointsListener);

        if(isDoubleJeopardyRound)
        {
            goToDoubleJeopardyRound();
        }


    }

    //by default a menu item is set to false because it has not been touched and so this method is if user selects an item from the menu, it will return true because an item has been selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(highscores)){
        }
        else if(v.equals(settings)){
        }
    }

    @Override
    public void onPointsDialogYesClick(DialogFragment dialog) {
        score += pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
    }

    @Override
    public void onPointsDialogNoClick(DialogFragment dialog) {
        score -= pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
    }


    @Override
    public void onDailyDoubleDialogYesButtonClick(DialogFragment dialog, int wager) {
        score += 2 * wager;
        textViewScore.setText(String.valueOf(score));
    }

    @Override
    public void onDailyDoubleDialogNoButtonClick(DialogFragment dialog, int wager) {
        score -= 2 * wager;
        textViewScore.setText(String.valueOf(score));
    }

    public void goToDoubleJeopardyRound()
    {
        buttonDoubleJeopardy.setVisibility(View.GONE);
        buttonBackToFirstRound.setVisibility(View.VISIBLE);
        isDoubleJeopardyRound = true;

        buttonPoints1.setText(R.string.points_400);
        buttonPoints2.setText(R.string.points_800);
        buttonPoints3.setText(R.string.points_1200);
        buttonPoints4.setText(R.string.points_1600);
        buttonPoints5.setText(R.string.points_2000);

        textViewRoundTitle.setText(R.string.double_jeopardy);
    }
    public void goToFirstRound()
    {
        buttonBackToFirstRound.setVisibility(View.GONE);
        buttonDoubleJeopardy.setVisibility(View.VISIBLE);
        isDoubleJeopardyRound = false;

        buttonPoints1.setText(R.string.points_200);
        buttonPoints2.setText(R.string.points_400);
        buttonPoints3.setText(R.string.points_600);
        buttonPoints4.setText(R.string.points_800);
        buttonPoints5.setText(R.string.points_1000);

        textViewRoundTitle.setText(R.string.first_round_title);
    }
}
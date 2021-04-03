package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PointsDialogFragment.PointsDialogListener {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Button highscores;
    private Button settings;

    private Button buttonPoints200;
    private Button buttonPoints400;
    private Button buttonPoints600;
    private Button buttonPoints800;
    private Button buttonPoints1000;
    private Button buttonNewGame;
    private TextView textViewScore;

    private int pointsToAddOrSubtract;
    private int score;

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

        buttonPoints200 = findViewById(R.id.buttonPoints200);
        buttonPoints400 = findViewById(R.id.buttonPoints400);
        buttonPoints600 = findViewById(R.id.buttonPoints600);
        buttonPoints800 = findViewById(R.id.buttonPoints800);
        buttonPoints1000 = findViewById(R.id.buttonPoints1000);

        buttonNewGame = findViewById(R.id.buttonNewGame);

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                textViewScore.setText(String.valueOf(score));
            }
        });

        textViewScore = findViewById(R.id.textViewScore);

        View.OnClickListener pointsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.buttonPoints200:
                        pointsToAddOrSubtract = 200;
                        break;
                    case R.id.buttonPoints400:
                        pointsToAddOrSubtract = 400;
                        break;
                    case R.id.buttonPoints600:
                        pointsToAddOrSubtract = 600;
                        break;
                    case R.id.buttonPoints800:
                        pointsToAddOrSubtract = 800;
                        break;
                    case R.id.buttonPoints1000:
                        pointsToAddOrSubtract = 1000;
                        break;
                    default:
                        break;
                }

                DialogFragment pointsDialog = new PointsDialogFragment();
                pointsDialog.show(getSupportFragmentManager(), "MainActivity");
            }
        };

        buttonPoints200.setOnClickListener(pointsListener);
        buttonPoints400.setOnClickListener(pointsListener);
        buttonPoints600.setOnClickListener(pointsListener);
        buttonPoints800.setOnClickListener(pointsListener);
        buttonPoints1000.setOnClickListener(pointsListener);

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
    public void onDialogYesClick(DialogFragment dialog) {
        score += pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
    }

    @Override
    public void onDialogNoClick(DialogFragment dialog) {
        score -= pointsToAddOrSubtract;
        textViewScore.setText(String.valueOf(score));
    }
}
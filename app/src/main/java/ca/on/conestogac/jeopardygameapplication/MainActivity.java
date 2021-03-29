package ca.on.conestogac.jeopardygameapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //Main drawer layout UI widget references.
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

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
        //user defined method is called to open the fragments based on the option chose in our navigation menu
        setupNavDrawer();
        Fragment fragment = new PointsFragment();
        //open a fragment transaction which we will use to replace our main activity layout with the fragment layout
        FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
        //replace our framelayout in activity_main xml with the fragment
        fragTransaction.replace(R.id.frame_layout, fragment);
        fragTransaction.commit();
        //then close the drawer menu once an item has been selected
        drawerLayout.closeDrawers();
    }

    private void setupNavDrawer() {
        //instantiate our navigation menu ui reference
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        //set a navigation listener to know which fragment is selected
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //declare our fragment and put it to null for now until a menu option is selected
                Fragment frag = null;
                //going into our res > menu folder with our menu layout (drawer_menu.xml) i grab the id of the option selected so i know which one is selected
                int itemID = item.getItemId();
                //make an if, else if statement to open the fragment based on option selected
                //this first if statement stores my first fragment points into Fragment frag if it is selected
                if(itemID == R.id.nav_points){
                    frag = new PointsFragment();
                }


                //now we check if a fragment not empty, meaning theres an instance of a fragment
                //so we load that into our activity_main.xml and open it, then close the drawers of our menu
                if(frag != null){
                    //open a fragment transaction which we will use to replace our main activity layout with the fragment layout
                    FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
                    //replace our framelayout in activity_main xml with the fragment
                    fragTransaction.replace(R.id.frame_layout, frag);
                    //commit the transaction
                    fragTransaction.commit();
                    //then close the drawer menu once an item has been selected
                    drawerLayout.closeDrawers();
                    return true;
                }

                //if frag is empty, then return false because there still hasn't been an option selected.
                return false;
            }
        });
    }

    //by default a menu item is set to false because it has not been touched and so this method is if user selects an item from the menu, it will return true because an item has been selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
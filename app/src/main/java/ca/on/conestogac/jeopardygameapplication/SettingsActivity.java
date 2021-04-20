package ca.on.conestogac.jeopardygameapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {
    private static SharedPreferences sharedPref;
    private static boolean stayLogged;
    private static String Stay_Logged_In = "Stay Logged In";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            SwitchPreferenceCompat themeMode = findPreference("Dark Theme");
            themeMode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (themeMode.isChecked()) {
                        Toast.makeText(getContext(), "Light Mode", Toast.LENGTH_SHORT).show();

                        // Checked the switch programmatically
                        themeMode.setChecked(false);
                    } else {
                        Toast.makeText(getContext(), "Dark Mode", Toast.LENGTH_SHORT).show();
                        // Unchecked the switch programmatically
                        themeMode.setChecked(true);
                    }
                    return true;
                }
            });
            SwitchPreferenceCompat stayLoggedIn = findPreference("Stay Logged In");
            stayLoggedIn.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    if (stayLoggedIn.isChecked()) {
                        Toast.makeText(getContext(), "Dont Stay Logged In", Toast.LENGTH_SHORT).show();
                        stayLogged = false;
                        // Checked the switch programmatically
                        stayLoggedIn.setChecked(false);
                    } else {
                        Toast.makeText(getContext(), "Stay Logged In", Toast.LENGTH_SHORT).show();
                        stayLogged = true;
                        // Unchecked the switch programmatically
                        stayLoggedIn.setChecked(true);
                    }
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putBoolean(Stay_Logged_In, stayLogged);
                    editor.commit();
                    return true;
                }
            });
        }
    }

}
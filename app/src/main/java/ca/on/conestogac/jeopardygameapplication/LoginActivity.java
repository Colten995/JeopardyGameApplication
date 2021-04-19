package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    View view;
    boolean isPassword, isUsername;
    private int counter=8;
    UserDatabaseHelper userDatabaseHelper;
    String name;
    int userId;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check for stay Logged in

        txtRegister = findViewById(R.id.txtSignup);
        txtUserName = findViewById(R.id.edUserName2);
        txtPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        userDatabaseHelper = new UserDatabaseHelper(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }
    @Override
    protected void onPause() {
        Editor editor = sharedPref.edit();

        editor.putInt("userId", userId);
        editor.putString("userName", name);
        editor.commit();

        super.onPause();
    }
    // onClick event
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                SetValidation();
                break;
            case R.id.txtSignup:
                startActivity(new Intent(getApplicationContext(), UserRegister.class));

                break;
            default:
                break;
        }
        SetValidation();


    }
    //Username and Password Validation
    public void SetValidation() {
        // check for a valid username
        if (txtUserName.getText().toString().isEmpty()) {
            txtUserName.setError("enter your username");

            Toast.makeText(getApplicationContext(), "Enter your Username", Toast.LENGTH_SHORT).show();
            isUsername = false;
            NumberOfAttempts();
        } else{
            isUsername=true;


        }
        // check for valid password

        if (txtPassword.getText().toString().isEmpty()&& txtPassword.getText().length()<6){
            txtPassword.setError("Enter your Password");
            Toast.makeText(getApplicationContext(), "Enter your password", Toast.LENGTH_SHORT).show();
            isPassword=false;
            NumberOfAttempts();
        }else {
            isPassword=true;
        }

        if (isPassword&& isUsername){
            if (VerifyUser()){
                Toast.makeText(getApplicationContext(), " Login Successful, Welcome "+name, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        }
    }

    // number of attempts
    public void NumberOfAttempts(){
        counter--;

        Toast.makeText(getApplicationContext(), "Number of attempts remaining: " + String.valueOf(counter),
                Toast.LENGTH_SHORT).show();
        if (counter == 0) {

            btnLogin.setEnabled(false);
        }

    }

    private boolean VerifyUser() {
        Cursor cursor = userDatabaseHelper.GetUsers();
        UserAccount userAccount;

        name = txtUserName.getText().toString();
        String password = txtPassword.getText().toString();
        //String names = "";
        //String passwords = "";
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    userAccount = new UserAccount();
                    userAccount.setId(cursor.getInt(cursor.getColumnIndex("userId")));
                    userAccount.setUserName(cursor.getString(cursor.getColumnIndex("name")));
                    userAccount.setUserPassword(cursor.getString(cursor.getColumnIndex("password")));
                    if (userAccount.getUserName().equals(name) && userAccount.getUserPassword().equals(password)){
                        cursor.close();
                        userId = userAccount.getId();
                        return true;
                    }

                } while (cursor.moveToNext());


            }
        }
        cursor.close();
        return false;

    }


}
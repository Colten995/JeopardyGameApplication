package ca.on.conestogac.jeopardygameapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegister = findViewById(R.id.txtSignup);
        txtUserName = findViewById(R.id.edUserName2);
        txtPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
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
            Toast.makeText(getApplicationContext(), " Login Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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



}
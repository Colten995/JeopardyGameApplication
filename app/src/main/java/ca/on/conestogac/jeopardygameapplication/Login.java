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

public class Login extends AppCompatActivity  implements View.OnClickListener{
    private EditText txtUserName, txtPassword;
    private Button btnLogin;
    private TextView txtRegister;
    View view;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:



                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;
            case R.id.txtSignup:
                startActivity(new Intent(getApplicationContext(), UserRegister.class));

                break;
            default:
                break;
        }
    }

}
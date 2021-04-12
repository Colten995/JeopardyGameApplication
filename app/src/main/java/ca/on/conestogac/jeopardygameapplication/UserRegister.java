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

public class UserRegister extends AppCompatActivity implements View.OnClickListener {
     private Button btnRegister;
     private TextView txtLogin;
     private EditText edPassword, edRePassword, edUsername;
      Boolean isPassword, isUsername, isPassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edPassword=findViewById(R.id.edPasswordRegister);
        edUsername=findViewById(R.id.edUserName1);
        edRePassword=findViewById(R.id.edReEnterPassword);
        txtLogin=findViewById(R.id.txtLogin);
        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                SetValidation();
                break;
            case R.id.txtLogin:
                // redirect to login activity
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            default:
                break;
        }
    }
    /* private  void validate(String username, String password){
        if((username=="Admin ")&&(password=="1234")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        else{
            counter--;

            Toast.makeText(getApplicationContext(),"Number of attempts remaining: " + String.valueOf(counter),
                    Toast.LENGTH_SHORT).show();
            if(counter==0){

                btnLogin.setEnabled(false);
            }
        }
    }*/

    // validation

    public void SetValidation(){

        // check for valid password
        if(edPassword.getText().toString().isEmpty()){
            edPassword.setError("Insert Password");
            Toast.makeText(getApplicationContext(),"Please Insert a password", Toast.LENGTH_SHORT).show();
            isPassword=false;
        }
        else if(edPassword.getText().length()<6){
            edPassword.setError("Invalid Password");
            Toast.makeText(getApplicationContext(),"Invalid Password", Toast.LENGTH_SHORT).show();
            isPassword=false;
        }
        else{
            isPassword=true;
        }
        // check for username valid

        if(edUsername.getText().toString().isEmpty()){
            edUsername.setError("Insert username");
            Toast.makeText(getApplicationContext(),"Please Insert a Username", Toast.LENGTH_SHORT).show();
            isUsername=false;
        }
        else{
            isUsername=true;
        }

        //check for a confirmation password

        if(edRePassword.getText().toString().isEmpty()){
            edRePassword.setError("Insert Password confirmation");
            Toast.makeText(getApplicationContext(),"Please Insert a password confirmation", Toast.LENGTH_SHORT).show();
            isPassword1=false;
        }
        else if(edRePassword.getText().length()<6){
            edRePassword.setError("Invalid Password confirmation");
            Toast.makeText(getApplicationContext(),"Invalid Password confirmation", Toast.LENGTH_SHORT).show();
            isPassword1=false;
        }
        else{
            isPassword1=true;
        }

       // check if all fields have inputs
        if(isPassword&&isUsername&& isPassword1){
            Toast.makeText(getApplicationContext()," Register successfully, enjoy your game", Toast.LENGTH_SHORT).show();
        }
    }


}
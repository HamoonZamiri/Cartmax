package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.login);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        editTextPassword = (EditText) findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password needs to be 6 or more characters long");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //redirect to users account
                    //startActivity(new Intent()).... depending on what page i wanna go to

                }
                else{
                    Toast.makeText(LoginActivity.this,
                            "Login Failed, Please check Email/Password",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
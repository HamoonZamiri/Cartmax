package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        View.OnKeyListener, Contract.View {

    private Button register;
    private EditText editTextEmail, editTextPassword;
    private Button login;

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("B07 Final Project");

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        editTextPassword = (EditText) findViewById(R.id.password);
        editTextPassword.setOnKeyListener(this);

        presenter = new LoginPresenter(new LoginModel(), this);

        SharedPreferences preferences = getSharedPreferences("user_info", 0);
        preferences.edit().clear().apply();
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch(v.getId()) {
            case R.id.password:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            presenter.login();
                            return true;
                        default:
                            break;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                presenter.login();
                break;

        }
    }

    @Override
    public String getEmail() {
        editTextEmail = (EditText) findViewById(R.id.emailAddress);
        return editTextEmail.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        editTextPassword = (EditText) findViewById(R.id.password);
        return editTextPassword.getText().toString().trim();
    }

    @Override
    public void handleError(String error, boolean emailError) {
        if(emailError) {
            editTextEmail.setError(error);
            editTextEmail.requestFocus();
        }
        else {
            editTextPassword.setError(error);
            editTextPassword.requestFocus();
        }
    }

    @Override
    public void success(boolean isCustomer) {
        SharedPreferences p = getSharedPreferences("user_info", 0);
        String email = p.getString("email", "");
        String name = p.getString("name", "");
        Intent i;
        if(isCustomer) {
            i = new Intent(LoginActivity.this, CustomerMainActivity.class);
            i.putExtra("userEmail", email);
            i.putExtra("userName", name);
            startActivity(i);
        }
        else {
            i = new Intent(LoginActivity.this, OwnerMainActivity.class);
            i.putExtra("ownerEmail", email);
            i.putExtra("ownerName", name);
            startActivity(i);

        }
    }

    @Override
    public void failure() {
        Toast.makeText(LoginActivity.this,
                "Login Failed, Please check Email/Password",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
    }

}
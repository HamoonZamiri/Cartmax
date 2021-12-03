package com.example.b07_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    public static boolean ownerTrack = false;
    private TextView registerUser;
    private FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.fullName);
        editTextEmail = (EditText) findViewById(R.id.emailAddressReg);
        editTextPassword = (EditText) findViewById(R.id.passwordReg);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.isOwner:
                CheckBox c = (CheckBox)v;
                ownerTrack = c.isChecked();
                break;
            case R.id.registerUser:
                registerUser(ownerTrack);
                break;

        }
    }

    private void registerUser(boolean isOwner) {
        String email = editTextEmail.getText().toString().trim();
        String fullName = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        if(fullName.isEmpty()){
            editTextName.setError("Full name is required");
            editTextName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        //check if email is valid through built in regex
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Password must be longer than 5 characters");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(email, password, fullName);

                            if (!isOwner){
                                FirebaseDatabase.getInstance().getReference("Users/Customers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterUser.this,
                                                    "Customer has been registered successfully",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterUser.this,
                                                    "Failed to register new user",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                            });}
                            else {

                                FirebaseDatabase.getInstance().getReference("Users/Owners")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterUser.this,
                                                    "Owner has been registered successfully",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(RegisterUser.this,
                                                    "Failed to register new user",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });}
                        }
                        else{
                            Toast.makeText(RegisterUser.this,
                                    "Failed to register new user",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onOwnerClicked(View view) {
        // Is the view now checked?
        ownerTrack = ((CheckBox) view).isChecked();
    }
}
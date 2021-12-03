package com.example.b07_final_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginModel implements Contract.Model {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public LoginModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public String emailError(String email) {
        if (email.isEmpty()) {
            return "Email is required";
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Enter a valid email";
        }
        return "";
    }

    @Override
    public String pwError(String password) {
        if (password.isEmpty()) {
            return "Password is required";
        }
        if (password.length() < 6) {
            return "Password needs to be 6 or more characters long";
        }
        return "";
    }

    @Override
    public void custLogin(LoginActivity view, String email, String password) {
        SharedPreferences p = view.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child("Customers");
                    mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                            } else {
                                for (DataSnapshot cust : task.getResult().getChildren()) {
                                    if (cust.child("email").getValue(String.class).equals(email)) {
                                        String email = cust.child("email").getValue(String.class);
                                        String name = cust.child("name").getValue(String.class);
                                        editor.putString("email", email).apply();
                                        editor.putString("name", name).apply();
                                        editor.putBoolean("isCustomer", true).apply();
                                        view.success(true);
                                        return;
                                    }
                                }
                            }
                        }
                    });
                } else {
                    view.failure();
                }
            }
        });
    }

    @Override
    public void storeLogin(LoginActivity view, String email, String password) {
        SharedPreferences p = view.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = p.edit();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child("Owners");
                    mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (!task.isSuccessful()) {
                            } else {
                                for (DataSnapshot owner : task.getResult().getChildren()) {
                                    if (owner.child("email").getValue(String.class).equals(email)) {
                                        StoreOwner o = task.getResult().getValue(StoreOwner.class);
                                        editor.putString("email", o.getEmail()).apply();
                                        editor.putString("name", o.getName()).apply();
                                        editor.putBoolean("isCustomer", false).apply();
                                        view.success(false);
                                        return;
                                    }
                                }
                            }
                        }
                    });
                } else {
                    view.failure();
                }
            }
        });
    }
}

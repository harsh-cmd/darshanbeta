package com.example.darshanbeta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editTextUsername;
    TextInputLayout editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.textInputLayoutPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*FirebaseAuth  mAuth = FirebaseAuth.getInstance();
                String email  = editTextUsername.getText().toString();
                String password = editTextPassword.getEditText().getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(LoginActivity.this,indexInfo.class));
                                    finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("fail", "signInWithEmail:failure");
                                }

                                // ...
                            }
                        });*/

                Intent i=new  Intent(LoginActivity.this,indexInfo.class);
                startActivity(i);
            }
        });

    }

}

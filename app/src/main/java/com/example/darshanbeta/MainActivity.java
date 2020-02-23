package com.example.darshanbeta;

import com.google.android.gms.tasks.OnCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

import android.content.pm.PackageManager;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import androidx.annotation.NonNull;

import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.os.Build;
import android.util.Log;


public class MainActivity extends AppCompatActivity {
    //controller objects
    private TextView textViewLogin;
    private TextView textViewForgotPassword;
    private Button buttonRegister;
    private EditText username;
    private EditText password;
    private EditText Confirmpassword;
    private EditText email;
    private EditText phone;
    private static final int REQUEST_CAMERA = 1;
    private static final String passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$]).{6,20})";
    private FirebaseAuth mAuth;
    private static final String EMAIL_API_KEY = "82b6e2eae503a7460a1e562293bd9ddb";
    private static final String PHONE_API_KEY = "71b558127779c99ffc18ff7be43fb809";
    EmailValidationAsyncTask validationAsyncTask;


    //    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bind controller object with java class
        textViewLogin = findViewById(R.id.textViewLogin);
        buttonRegister = findViewById(R.id.buttonRegistration);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        username = findViewById(R.id.editTextUsername);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhoneNumber);
        password = findViewById(R.id.editTextPassword);
        Confirmpassword = findViewById(R.id.editTextConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        firestore.setFirestoreSettings(settings);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString().trim();
                long Phone = Long.parseLong(phone.getText().toString());
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String ConfirmPassword = Confirmpassword.getText().toString();
                validationAsyncTask = new EmailValidationAsyncTask(v.getContext());
                validationAsyncTask.execute("http://apilayer.net/api/check?access_key=" + EMAIL_API_KEY + "&email=" + Email + "&smtp=1&formate=1");
                NumberValidationAsyncTask asyncTask = new NumberValidationAsyncTask(v.getContext());
                asyncTask.execute("http://apilayer.net/api/validate?access_key=" + PHONE_API_KEY + "&number=" + Phone + "&country_code=IN");
                try {
                    String emailResponce = validationAsyncTask.get();
                    String phoneResponce = asyncTask.get();
                    Log.i("phone responce", phoneResponce);
                    getEmailValidationResponce(emailResponce);
                    getPhoneValidationResponce(phoneResponce);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (validatePassword(Password)) {
                    Log.i("passwordValidate", "true");
                } else {
                    password.setError("Password must contain 1 digit, 1 uppercase character, 1 lower character, 1 special symbol ($@#) ");

                }

                if (Password.equals(ConfirmPassword)) {
                    Map<String, Object> user = new HashMap<String, Object>();
                    user.put("username", Username);
                    user.put("email", Email);
                    user.put("phone", Phone);
                    user.put("password", Password);
                    db.collection("users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(MainActivity.this, "success firebase", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, "fail firebase", Toast.LENGTH_SHORT).show();
                                }
                            });

                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(MainActivity.this, "emaiil success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this, "emaiil error", Toast.LENGTH_SHORT).show();
                                    }
                                    // ...
                                }
                            });
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }


    void getEmailValidationResponce(String emailValid) {
        try {
            JSONObject jsonObject = new JSONObject(emailValid);
            Log.i("json", jsonObject.toString());
            String domain = jsonObject.getString("domain");
            Boolean format_valid = jsonObject.getBoolean("format_valid");
            Boolean smtp_check = jsonObject.getBoolean("smtp_check");
            if (format_valid && smtp_check) {

            } else {
                email.setError("Email is Invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getPhoneValidationResponce(String phone) {
        try {
            JSONObject jsonObject = new JSONObject(phone);
            boolean valid = jsonObject.getBoolean("valid");
            // phone number is still valid if you give number less than 10 digits because
            // it can be assign to special services or else
            if (phone.length() != 10 && !valid) {
                this.phone.setError("Invalid Phone Number");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validatePassword(String passwordString) {
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(passwordString);
        return matcher.matches();
    }
}
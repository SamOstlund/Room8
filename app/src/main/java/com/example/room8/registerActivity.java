package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class registerActivity extends AppCompatActivity {


    private EditText firstName, lastName, email, password, age, minprice, maxprice, zip, bio;
    private Button submitRegisterButton, registerBack;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        age = (EditText) findViewById(R.id.age);
        minprice = (EditText) findViewById(R.id.minprice);
        maxprice = (EditText) findViewById(R.id.maxprice);
        zip = (EditText) findViewById(R.id.zip);
        bio = (EditText) findViewById(R.id.bio);
        submitRegisterButton = (Button) findViewById(R.id.submitRegisterButton);
        registerBack = (Button) findViewById(R.id.registerBack);

        databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("User");


        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
                startActivity(new Intent(registerActivity.this, profileActivity.class));
                /*AddData();
                if (isEmailValid(email.getText().toString().trim()) && isPasswordValid(password.getText().toString().trim())){
                    Register();
                }
                */

            }
        });

        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this, LoginActivity.class));
            }
        });
    }

    private void Register(){

        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final FirebaseUser user = task.getResult().getUser();
                    if (user != null){
                        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    com.google.firebase.database.DatabaseReference databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
                                    databaseReference = databaseReference.child(user.getUid());

                                    UserModel userModel = new UserModel();

                                    userModel.setFirstName(firstName.getText().toString().trim());
                                    userModel.setLastName(lastName.getText().toString().trim());
                                    userModel.setEmail(email.getText().toString().trim());
                                    userModel.setPassword(password.getText().toString().trim());
                                    userModel.setAge(age.getText().toString().trim());
                                    userModel.setMinprice(minprice.getText().toString().trim());
                                    userModel.setMaxprice(maxprice.getText().toString().trim());
                                    userModel.setZip(zip.getText().toString().trim());
                                    userModel.setBio(bio.getText().toString().trim());

                                    databaseReference.setValue(userModel);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }



    public void AddData(){
        String fName = firstName.getText().toString().trim();
        String lName = lastName.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();
        String Age = age.getText().toString().trim();
        String MinPrice = minprice.getText().toString().trim();
        String MaxPrice = maxprice.getText().toString().trim();
        String Zip = zip.getText().toString().trim();
        String Bio = bio.getText().toString().trim();

        SaveData saveData = new SaveData(fName, lName, Email, Pass, Age, MinPrice, MaxPrice, Zip, Bio);

        databaseReference.setValue(saveData);

    }

}

package com.example.room8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class registerActivity extends AppCompatActivity {


    private EditText firstName, lastName, email, password, age, minprice, maxprice, zip, bio;
    private Button submitRegisterButton, registerBack;

    com.google.firebase.database.DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                AddData();

            }
        });

        registerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this, LoginActivity.class));
            }
        });
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

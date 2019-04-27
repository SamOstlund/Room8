package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class registerActivity extends AppCompatActivity {


    private EditText firstName, lastName, email, password, age, minprice, maxprice, zip, bio;
    private Button submitRegisterButton, registerBack;

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user !=null){
                    Intent intent = new Intent(registerActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

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




        submitRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();



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

        final String Email = email.getText().toString();
        final String Password = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(registerActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                }else{
                    String userId = mAuth.getCurrentUser().getUid();
                    com.google.firebase.database.DatabaseReference databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
                    databaseReference = databaseReference.child(userId);

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
                    userModel.setUrl("https://firebasestorage.googleapis.com/v0/b/room8-4357b.appspot.com/o/default.png?alt=media&token=b7864fc3-6e0b-4372-a2ed-5cf50bc8e703");
                    databaseReference.setValue(userModel);





                }
            }
        });
    }


    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }





    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class changeDataActivity extends AppCompatActivity {

    private Button cancelButton, acceptChanges;
    private EditText emailBox, passwordBox, firstNameBox, lastNameBox, zipCodeBox, ageBox, maxBox, minBox, bioBox;
    private String userID;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private DatabaseReference dbref;
    private String firstNameHolder, lastNameHolder, emailHolder, passwordHolder, zipHolder, ageHolder, bioHolder, minHolder, maxHolder, URLHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //setting up the UI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_data);
        setUp();

        //getting firebase approval, making sure there is a logged in user
        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null){
                    Intent intent = new Intent(changeDataActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        userID = mAuth.getCurrentUser().getUid();
        dbref = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

        //this adds all of the values to the holders
        dbref.addValueEventListener(new ValueEventListener() //this checks if there is information for each of the category of the profile and if there is information then it puts that data on the display
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) //sets the age, name, minimum price, and bio boxes to the correct values
                {
                    firstNameHolder = dataSnapshot.child("firstName").getValue().toString();
                    lastNameHolder = dataSnapshot.child("lastName").getValue().toString();
                    emailHolder = dataSnapshot.child("email").getValue().toString();
                    passwordHolder = dataSnapshot.child("password").getValue().toString();
                    zipHolder = dataSnapshot.child("zip").getValue().toString();
                    ageHolder = dataSnapshot.child("age").getValue().toString();
                    maxHolder = dataSnapshot.child("maxprice").getValue().toString();
                    minHolder = dataSnapshot.child("minprice").getValue().toString();
                    bioHolder = dataSnapshot.child("bio").getValue().toString();
                    URLHolder = dataSnapshot.child("profileImageUrl").getValue().toString();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                return;
            }
        });





    }

    public void setUp() //sets up the on click listeners and links the different views to their variables
    {
        cancelButton = findViewById(R.id.cancelChanges);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(changeDataActivity.this, settingsActivity.class));
            }
        });

        acceptChanges = findViewById(R.id.acceptChanges);
        acceptChanges.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                commitChanges();
            }
        });

        emailBox = findViewById(R.id.email);
        passwordBox = findViewById(R.id.password);
        firstNameBox = findViewById(R.id.firstName);
        lastNameBox = findViewById(R.id.lastName);
        zipCodeBox = findViewById(R.id.zip);
        ageBox = findViewById(R.id.age);
        maxBox = findViewById(R.id.maxprice);
        minBox = findViewById(R.id.minprice);
        bioBox = findViewById(R.id.bio);



    }

    public void commitChanges() //this goes through and checks if there is something in the text field, if so then it changes that value to that. If not, then it just keeps it as whatever information was already in the system
    {

        UserModel updatedUser = new UserModel();


        if (!firstNameBox.getText().toString().equals(""))
            updatedUser.setFirstName(firstNameBox.getText().toString().trim());
        else
            updatedUser.setFirstName(firstNameHolder);


        if (!lastNameBox.getText().toString().equals(""))
            updatedUser.setLastName(lastNameBox.getText().toString().trim());
        else
            updatedUser.setLastName(lastNameHolder);


        if (!emailBox.getText().toString().equals(""))
            updatedUser.setEmail(emailBox.getText().toString().trim());
        else
            updatedUser.setEmail(emailHolder);


        if (!passwordBox.getText().toString().equals(""))
            updatedUser.setPassword(passwordBox.getText().toString().trim());
        else
            updatedUser.setPassword(passwordHolder);


        if (!bioBox.getText().toString().equals(""))
            updatedUser.setBio(bioBox.getText().toString().trim());
        else
            updatedUser.setBio(bioHolder);


        if (!zipCodeBox.getText().toString().equals(""))
            updatedUser.setZip(zipCodeBox.getText().toString().trim());
        else
            updatedUser.setZip(zipHolder);


        if (!ageBox.getText().toString().equals(""))
            updatedUser.setAge(ageBox.getText().toString().trim());
        else
            updatedUser.setAge(ageHolder);


        if (!maxBox.getText().toString().equals(""))
            updatedUser.setMaxprice(maxBox.getText().toString().trim());
        else
            updatedUser.setMaxprice(maxHolder);


        if (!minBox.getText().toString().equals(""))
            updatedUser.setMinprice(minBox.getText().toString().trim());
        else
            updatedUser.setMinprice(minHolder);

        updatedUser.setUrl(URLHolder);

        dbref.setValue(updatedUser);

        finish();






    }
}

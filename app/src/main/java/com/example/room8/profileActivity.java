package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class profileActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private FirebaseUser loggedInUser;
    private TextView nameBox, ageBox, bioBox, nameIDBox, ageIDBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();


        //Setting up the navigation bar at the bottom to go to the correct page when clicked. I also highlight the chosen page so the user knows where they are in the app.
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch(menuItem.getItemId())
                {
                    case R.id.navigation_profile:
                        break;

                    case R.id.navigation_home:
                        Intent intent2 = new Intent(profileActivity.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_messaging:
                        Intent intent3 = new Intent(profileActivity.this, messaging.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });

        nameBox = findViewById(R.id.nameBoxProfile);
        ageBox = findViewById(R.id.ageBox);
        bioBox = findViewById(R.id.bioBox);
        nameIDBox = findViewById(R.id.nameIDBox);
        ageIDBox = findViewById(R.id.ageIDBox);

        nameIDBox.setText("Name: ");
        ageIDBox.setText("Age: ");


        mAuth = FirebaseAuth.getInstance();
        loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentID = mAuth.getCurrentUser().getUid();
        databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(currentID);
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) //sets the age, name, and bio boxes to the correct values
                {
                    if (dataSnapshot.child("age").getValue() != null)
                        ageBox.setText(dataSnapshot.child("age").getValue().toString());
                    if (dataSnapshot.child("firstName").getValue() != null && dataSnapshot.child("lastName").getValue() != null)
                        nameBox.setText(dataSnapshot.child("firstName").getValue().toString() + " " + dataSnapshot.child("lastName").getValue().toString());
                    if (dataSnapshot.child("bio").getValue() != null)
                        bioBox.setText(dataSnapshot.child("bio").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });




    }
}

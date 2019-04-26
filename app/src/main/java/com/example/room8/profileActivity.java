package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*Allows user to view profile information*/

public class profileActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser loggedInUser;
    private TextView nameBox, ageBox, bioBox, nameIDBox, ageIDBox, minIDBox, minBox, maxIDBox, maxBox, bioIDBox;
    private ImageView userPIC;

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


        //Connects the variables to the actual items/views on the .xml file
        nameBox = findViewById(R.id.nameBoxProfile);
        ageBox = findViewById(R.id.ageBox);
        bioBox = findViewById(R.id.bioBox);
        nameIDBox = findViewById(R.id.nameIDBox);
        ageIDBox = findViewById(R.id.ageIDBox);
        minIDBox = findViewById(R.id.minIDBox);
        minBox = findViewById(R.id.minBox);
        maxIDBox = findViewById(R.id.maxIDBox);
        maxBox = findViewById(R.id.maxBox);
        bioIDBox = findViewById(R.id.bioIDBox);
        userPIC = (ImageView) findViewById(R.id.profilePic);

        //Sets the text for the textViews that identify which category of information it is
        nameIDBox.setText("Name: ");
        ageIDBox.setText("Age: ");
        minIDBox.setText("Minimum: ");
        maxIDBox.setText("Maximum: ");
        bioIDBox.setText("Biography:");

        //Getting firebase authentication and also getting the current user's unique ID
        mAuth = FirebaseAuth.getInstance();
        loggedInUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentID);
        databaseReference.addValueEventListener(new ValueEventListener() //this checks if there is information for each of the category of the profile and if there is information then it puts that data on the display
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) //sets the age, name, minimum price, and bio boxes to the correct values
                {
                    if (dataSnapshot.child("age").getValue() != null)
                        ageBox.setText(dataSnapshot.child("age").getValue().toString());
                    if (dataSnapshot.child("firstName").getValue() != null && dataSnapshot.child("lastName").getValue() != null) {
                        String holder = dataSnapshot.child("firstName").getValue().toString() + " " + dataSnapshot.child("lastName").getValue().toString();
                        nameBox.setText(holder);
                    }
                    if (dataSnapshot.child("bio").getValue() != null)
                        bioBox.setText(dataSnapshot.child("bio").getValue().toString());
                    if (dataSnapshot.child("minprice").getValue() != null) {
                        String holder = "$" + dataSnapshot.child("minprice").getValue().toString();
                        minBox.setText(holder);
                    }
                    if (dataSnapshot.child("maxprice").getValue() != null) {
                        String holder = "$" + dataSnapshot.child("maxprice").getValue().toString();
                        maxBox.setText(holder);
                    }
                    if(dataSnapshot.child("profileImageUrl")!= null){
                        String ImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                        switch(ImageUrl){
                            default:
                                Glide.with(getApplication()).load(ImageUrl).into(userPIC);
                                break;
                    }

                }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                return;
            }
        });




    }
}

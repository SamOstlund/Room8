package com.example.room8;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;
import java.util.List;

/*Match making process needs to be completed*/
public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Button logout, uploadImage, dislikeButton, likeButton;
    private DatabaseReference databaseReference;
    private ArrayList<user> possibleMatches;
    private userArrayAdapter myUserArrayAdapter;
    private ListView cardFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user == null){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();

        //Setting up the navigation bar at the bottom to go to the correct page when clicked. I also highlight the chosen page so the user knows where they are in the app.
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                /* Switch that changes activity accordingly*/

                switch(menuItem.getItemId())
                {
                    case R.id.navigation_profile:
                        Intent intent = new Intent(MainActivity.this, profileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_home:
                        break;

                    case R.id.navigation_messaging:
                        Intent intent3 = new Intent(MainActivity.this, messaging.class);
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });


       logout =  findViewById(R.id.logoutButton);
       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               logOut();
            }
        });

       uploadImage =  findViewById(R.id.uploadButton);

       uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, uploadActivity.class));
            }
       });



       //MATCHMAKING
        dislikeButton = findViewById(R.id.dislikeButton);
        likeButton = findViewById(R.id.likeButton);
        cardFrame = findViewById(R.id.myListView);


        String currentID = mAuth.getCurrentUser().getUid(); //string will hold the current logged in User ID
        databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users"); //This creates a database reference that will reference our list of users

        possibleMatches = new ArrayList<user>();

        getPotentialMatches();
        myUserArrayAdapter = new userArrayAdapter(this, R.layout.user_item, possibleMatches);

        cardFrame.setAdapter(myUserArrayAdapter);






    }
    private void logOut(){
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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

    private void getPotentialMatches()
    {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                if (dataSnapshot.exists() && !dataSnapshot.child("connections").child("no").hasChild(mAuth.getCurrentUser().getUid()) && !dataSnapshot.child("connections").child("yes").hasChild(mAuth.getCurrentUser().getUid()) && !dataSnapshot.child("matches").hasChild(mAuth.getCurrentUser().getUid()))
                {
                    user obj = new user(dataSnapshot.getKey());
                    possibleMatches.add(obj);
                    myUserArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
            {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

}

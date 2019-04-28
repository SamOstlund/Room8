package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



/*Messaging activity needs to be completed*/


public class messaging extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new chatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        //Setting up the navigation bar at the bottom to go to the correct page when clicked. I also highlight the chosen page so the user knows where they are in the app.
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.navigation_profile:
                        Intent intent = new Intent(messaging.this, profileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.navigation_home:
                        Intent intent2 = new Intent(messaging.this, MainActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.navigation_messaging:
                        break;
                }

                return false;
            }
        });
    }
}

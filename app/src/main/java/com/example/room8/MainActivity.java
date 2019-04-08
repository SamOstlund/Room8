package com.example.room8;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;



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

package com.example.room8;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class messaging extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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

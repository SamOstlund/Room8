package com.example.room8.Matches;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.room8.MainActivity;
import com.example.room8.R;
import com.example.room8.profileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



/*Messaging activity needs to be completed*/


public class messaging extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private  RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager matchesMan;
    private String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                switch (menuItem.getItemId()) {
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



        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        matchesMan = new LinearLayoutManager(messaging.this);
        mRecyclerView.setLayoutManager(matchesMan);
        mAdapter = new MatchesAdapter(getDataMatches(),messaging.this);
        mRecyclerView.setAdapter(mAdapter);
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("getUse",currentUser);
        getUserMatchId();
      //  mAdapter.notifyDataSetChanged();

    }

    private void getUserMatchId() {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference("Users").child(currentUser).child("Connections").child("Matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot match: dataSnapshot.getChildren() ){
                            FetchMatchInformation(match.getKey());
                            Log.d("getUserMatch",match.getKey());
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void FetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String userId = dataSnapshot.getKey();
                    String name ;
                    String profileImageUrl;

                        name = dataSnapshot.child("firstName").getValue().toString();


                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();



                    MatchesObject obj = new MatchesObject(userId, name, profileImageUrl);
                    resultsMatches.add(obj);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private ArrayList<MatchesObject> resultsMatches = new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataMatches() {
        return resultsMatches;
    }


}

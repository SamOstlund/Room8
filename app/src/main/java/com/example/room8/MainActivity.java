package com.example.room8;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;


import java.util.ArrayList;
import java.util.List;

/*Match making process needs to be completed*/
public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Button logout, uploadImage, dislikeButton, likeButton;
    private CardView cardFrame;
    private RecyclerView myRecycleView;
    private SwipeFlingAdapterView myFlingView;



    private List<user> possibleMatches;
    private userArrayAdapter arrayAdapter;

    private String currentUId;

    private DatabaseReference usersDb;


    ListView listView;
    List<user> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    //    databaseReference = FirebaseDatabase.getInstance().getReference().child(mAuth.getUid());
        usersDb = FirebaseDatabase.getInstance().getReference();

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
        compressCreate();

       // usersDb = FirebaseDatabase.getInstance().getReference().child("Users");

        mAuth = FirebaseAuth.getInstance();
        currentUId = mAuth.getUid();



        rowItems = new ArrayList<user>();
        getPotentialMatches();
        arrayAdapter = new userArrayAdapter(this, R.layout.user_item, rowItems );



        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.card);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

              user obj = (user) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("Connections").child("NotLikes").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
               user obj = (user) dataObject;
                String userId = obj.getUserId();
                usersDb.child(userId).child("Connections").child("Likes").child(currentUId).setValue(true);
               // isConnectionMatch(userId);
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Item Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getPotentialMatches(){

            usersDb.addChildEventListener(new ChildEventListener() {
               final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(currentUId);
                    @Override
                    public void onChildAdded (DataSnapshot dataSnapshot, String s){

                        if(!dataSnapshot.child("Connections").child("NotLikes").hasChild(currentUId) && !dataSnapshot.child("Connections").child("Likes").hasChild(currentUId)) {
                            String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                            user item = new user(dataSnapshot.getKey(), dataSnapshot.child("firstName").getValue().toString(), profileImageUrl);
                            rowItems.add(item);
                            arrayAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onChildChanged (DataSnapshot dataSnapshot, String s){
                        if(!dataSnapshot.child("Connections").child("NotLikes").hasChild(currentUId) && !dataSnapshot.child("Connections").child("Likes").hasChild(currentUId)) {
                            String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                            user item = new user(dataSnapshot.getKey(), dataSnapshot.child("firstName").getValue().toString(), profileImageUrl);
                            rowItems.add(item);
                            arrayAdapter.notifyDataSetChanged();

                        }

                    }


                    @Override
                    public void onChildRemoved (DataSnapshot dataSnapshot){

                        if(!dataSnapshot.child("Connections").child("NotLikes").hasChild(currentUId) && !dataSnapshot.child("Connections").child("Likes").hasChild(currentUId)) {
                            String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                            user item = new user(dataSnapshot.getKey(), dataSnapshot.child("firstName").getValue().toString(), profileImageUrl);
                            rowItems.add(item);
                            arrayAdapter.notifyDataSetChanged();
                        }
                }

                    @Override
                    public void onChildMoved (DataSnapshot dataSnapshot, String s){

                        if(!dataSnapshot.child("Connections").child("NotLikes").hasChild(currentUId) && !dataSnapshot.child("Connections").child("Likes").hasChild(currentUId)) {
                            String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();
                            user item = new user(dataSnapshot.getKey(), dataSnapshot.child("firstName").getValue().toString(), profileImageUrl);
                            rowItems.add(item);
                            arrayAdapter.notifyDataSetChanged();
                        }
                }

                    @Override
                    public void onCancelled (DatabaseError databaseError){

                }

            });

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

    private void compressCreate()
{
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


    logout = findViewById(R.id.logoutButton);
    logout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            logOut();
        }
    });

    uploadImage = findViewById(R.id.uploadButton);

    uploadImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, uploadActivity.class));
        }
    });



    //MATCHMAKING
    dislikeButton = findViewById(R.id.dislikeButton);
    likeButton = findViewById(R.id.likeButton);
   // myFlingView = findViewById(R.id.frame);


}
}

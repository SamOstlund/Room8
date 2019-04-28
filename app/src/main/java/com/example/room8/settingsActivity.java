package com.example.room8;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class settingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logoutButton, changeButton, uploadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.LogoutButtonSettings);
        changeButton = findViewById(R.id.InfoChangeButtonSettings);
        uploadButton = findViewById(R.id.UploadButtonSettings);

        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                logOut();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(settingsActivity.this, changeDataActivity.class);
                startActivity(intent);
            }

        });

        uploadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(settingsActivity.this, uploadActivity.class);
                startActivity(intent);
            }
        });


    }

    private void logOut(){
        mAuth.signOut();
        startActivity(new Intent(settingsActivity.this, LoginActivity.class));
    }
}

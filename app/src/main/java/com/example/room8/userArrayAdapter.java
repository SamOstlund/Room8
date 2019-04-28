package com.example.room8;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room8.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class userArrayAdapter extends ArrayAdapter<user>{


    public userArrayAdapter(Context context, int resourceId, List<user> items){
        super(context, resourceId, items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        user card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }


        //setting up the views
        final TextView name = (TextView) convertView.findViewById(R.id.name);
        final TextView minPrice = (TextView) convertView.findViewById(R.id.minPriceBox);
        final TextView maxPrice = (TextView) convertView.findViewById(R.id.maxPriceBox);
        final TextView bio = (TextView) convertView.findViewById(R.id.bioBox);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);


        //putting the information in the views

        String userIDHolder;

        userIDHolder = card_item.getUserId();
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child(userIDHolder);

        dbReference.addValueEventListener(new ValueEventListener() //this checks if there is information for each of the category of the profile and if there is information then it puts that data on the display
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) //sets the age, name, minimum price, and bio boxes to the correct values
                {

                    if (dataSnapshot.child("firstName").getValue() != null && dataSnapshot.child("age").getValue() != null) {
                        String holder = dataSnapshot.child("firstName").getValue().toString() + ", " + dataSnapshot.child("age").getValue().toString();
                        name.setText(holder);
                    }
                    if (dataSnapshot.child("bio").getValue() != null)
                        bio.setText(dataSnapshot.child("bio").getValue().toString());
                    if (dataSnapshot.child("minprice").getValue() != null) {
                        String holder = "Minimum: $" + dataSnapshot.child("minprice").getValue().toString();
                        minPrice.setText(holder);
                    }
                    if (dataSnapshot.child("maxprice").getValue() != null) {
                        String holder = "Maximum: $" + dataSnapshot.child("maxprice").getValue().toString();
                        maxPrice.setText(holder);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                return;
            }
        });




        switch(card_item.getProfileImageUrl()){

            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(card_item.getProfileImageUrl()).into(image);
                break;
        }


        return convertView;

    }
}
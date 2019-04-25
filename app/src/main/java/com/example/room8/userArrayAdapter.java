package com.example.room8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class userArrayAdapter extends ArrayAdapter<user>
{

    public userArrayAdapter(Context context, int resourceId, ArrayList<user> users)
    {
        super(context, resourceId, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        user userObj = getItem(position); //gets the user we are working with

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        }

        //Attaching the views to the correct object
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
        ImageView image = (ImageView) convertView.findViewById(R.id.userImage);

        //setting the correct values to the views
        userName.setText(userObj.getFirstName());
        switch(userObj.getProfileUrl()) //switch statement for setting the picture
        {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(userObj.getProfileUrl()).into(image);
                break;
        }

        return convertView;
    }


}

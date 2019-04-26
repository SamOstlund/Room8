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
import java.util.List;

public class userArrayAdapter extends ArrayAdapter<user>
{

    private Context thisContext;
    private List<user> userList = new ArrayList<>();
    private int resource;

    public userArrayAdapter(Context context, int resourceId, ArrayList<user> usersListGiven)
    {

        super(context, resourceId, usersListGiven);
        thisContext = context;
        userList = usersListGiven;
        resource = resourceId;
    }

    public userArrayAdapter(Context context, ArrayList<user> users)
    {
        super(context, 0, users);
        thisContext = context;
        userList = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
            convertView = LayoutInflater.from(thisContext).inflate(R.layout.user_item, parent, false); //inflate it to fit

        user currentUser = userList.get(position);

        //Attaching the views to the correct object
        TextView userName = (TextView) convertView.findViewById(R.id.userName);
       // ImageView image = (ImageView) convertView.findViewById(R.id.userImage);

        //setting the correct values to the views
        userName.setText(currentUser.getFirstName());
       /* switch(currentUser.getProfileUrl()) //switch statement for setting the picture
        {
            case "default":
                Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
                break;
            default:
                Glide.clear(image);
                Glide.with(convertView.getContext()).load(currentUser.getProfileUrl()).into(image);
                break;
        }*/

        return convertView;
    }


}

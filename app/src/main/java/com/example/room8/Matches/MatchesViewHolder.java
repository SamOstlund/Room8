package com.example.room8.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room8.Chat.ChatActivity;
import com.example.room8.R;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId,matchName;
    public ImageView matchPic;
    public MatchesViewHolder(View itemView){
        super(itemView);
        itemView.setOnClickListener(this);
        mMatchId = (TextView) itemView.findViewById(R.id.Matchid);
        matchName = (TextView) itemView.findViewById(R.id.MatchName);

        matchPic = (ImageView) itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchId",mMatchId.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);
    }
}
